package future;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BingoBoard {
	private final String board[][];

	private final int WIDTH = 5;
	private final int HEIGHT = 5;

	private final int BOARD_SIZE = WIDTH * HEIGHT;

	private final Map<String, Boolean> calledNumbers;
	private final List<String> events;
	private final List<String> selectedEvents;

	private final String FREE = "FREE SPACE";
	private final int player;
	private boolean win;

	public BingoBoard(final ArrayList<String> eventList, final int numb) {
		board = new String[WIDTH][HEIGHT];
		selectedEvents = new ArrayList<>();
		events = eventList;
		calledNumbers = new HashMap<>();
		calledNumbers.put(FREE, true);
		player = numb;
		win = false;
	}

	public void updateEvents(final ArrayList<String> eventList) {
		events.addAll(eventList);
	}

	// Chooses events and adds them to the board.
	public void randomizeEvents() {
		final Random rand = new Random();

		while (selectedEvents.size() < BOARD_SIZE - 1) {
			final int index = rand.nextInt(events.size());
			final String str = events.get(index);
			selectedEvents.add(str);
			events.remove(str);
		}

		placeNumbers(selectedEvents, calledNumbers);
	}

	private void placeNumbers(final List<String> numbers, final Map<String, Boolean> calledNumbers) {
		int count = 0;

		for (final String bingoNumber : numbers) {
			calledNumbers.put(bingoNumber, false);

			if (count == BOARD_SIZE / 2) {
				board[getX(count)][getY(count)] = FREE;
				count++;
			}

			board[getX(count)][getY(count)] = bingoNumber;
			count++;
		}
	}

	private int getY(final int count) {
		return count % HEIGHT;
	}

	private int getX(final int count) {
		return count / WIDTH;
	}

	public void printBoard() {
		System.out.printf("Player %d\n", player);

		printTopRow();

		for (int i = 0; i < HEIGHT; i++) {
			printRowSeparator();
			printLine(i);
		}

		printRowSeparator();
		printBottomRow();
	}

	private void printBottomRow() {
		System.out.println("_____________________\n\n");
	}

	private void printTopRow() {
		System.out.println("_____________________");
	}

	private void printLine(final int i) {
		for (int j = 0; j < WIDTH; j++) {
			if (calledNumbers.get(board[i][j]) == true) {
				System.out.printf("|%3s", "X");
			} else {
				System.out.printf("|%3s", board[i][j]);
			}
		}

		System.out.println("|");
	}

	private void printRowSeparator() {
		System.out.println("|---|---|---|---|---|");
	}

	public void markNumber(final String value) {
		if (calledNumbers.containsKey(value)) {
			calledNumbers.put(value, Boolean.TRUE);
		}
	}

	public boolean checkWin() {
		win = evalBoard();
		return win;
	}

	public boolean hasWinningRow() {
		return win;
	}

	public int getPlayer() {
		return player;
	}

	private boolean evalBoard() {
		final boolean horizontalWin = checkHorizontally(calledNumbers, board);
		final boolean verticalWin = checkVertically(calledNumbers, board);
		final boolean diagonallyWin = checkDiagonally(calledNumbers, board);
		final boolean diagonallyR = checkDiagonallyTopRight(calledNumbers, board);
		return horizontalWin || verticalWin || diagonallyWin || diagonallyR;
	}

	private boolean checkHorizontally(final Map<String, Boolean> calledNumbers, final String[][] board) {
		boolean result = true;
		for (int x = 0; x < HEIGHT; x++) {
			for (int y = 0; y < WIDTH; y++) {
				final Boolean statusAt = statusAt(calledNumbers, board, x, y);
				result = result && statusAt;
			}

			if (result) {
				return result;
			}
		}

		return false;
	}

	private boolean checkVertically(final Map<String, Boolean> calledNumbers, final String[][] board) {
		boolean result = true;
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				final Boolean statusAt = statusAt(calledNumbers, board, y, x);
				result = result && statusAt;
			}

			if (result) {
				return result;
			}
		}

		return false;
	}

	private boolean checkDiagonally(final Map<String, Boolean> calledNumbers, final String[][] board) {
		boolean result = true;
		for (int i = 0; i < WIDTH; i++) {
			final Boolean statusAt = statusAt(calledNumbers, board, i, i);
			result = result && statusAt;
		}

		return result;
	}

	private boolean checkDiagonallyTopRight(final Map<String, Boolean> calledNumbers, final String[][] board) {
		boolean result = true;
		final int farRight = WIDTH - 1;
		for (int i = 0; i < WIDTH; i++) {
			final Boolean statusAt = statusAt(calledNumbers, board, farRight - i, i);
			result = result && statusAt;
		}

		return result;
	}

	private Boolean statusAt(final Map<String, Boolean> calledNumbers, final String[][] board, final int x,
			final int y) {
		return calledNumbers.get(board[x][y]);
	}
}