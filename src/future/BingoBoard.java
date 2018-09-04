package future;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BingoBoard {
	private final String board[][];

	private final static int WIDTH = 5;
	private final static int HEIGHT = 5;

	public final static int BOARD_SIZE = WIDTH * HEIGHT;

	private final Map<String, Boolean> calledNumbers;
	private final List<String> events;

	private final String FREE = "FREE SPACE";
	private final String player;

	public BingoBoard(final List<String> eventList, final String playerName) {
		board = new String[WIDTH][HEIGHT];
		events = eventList;
		calledNumbers = new HashMap<>();
		player = playerName;
	}

	public void updateEvents(final ArrayList<String> eventList) {
		events.addAll(eventList);
	}

	public void prepareBoard() {
		final List<String> numbers = randomizeNumbers();
		prepareCalledNumbers(numbers, calledNumbers);
		placeNumbersOnBoard(numbers, calledNumbers);
	}

	private List<String> randomizeNumbers() {
		final Random rand = new Random();
		final List<String> numbers = new ArrayList<>();

		while (numbers.size() < BOARD_SIZE - 1) {
			final int index = rand.nextInt(events.size());
			final String randomNumber = events.get(index);
			numbers.add(randomNumber);
			events.remove(randomNumber);
		}
		return numbers;
	}

	private void prepareCalledNumbers(final List<String> selectedEvents, final Map<String, Boolean> calledNumbers) {
		calledNumbers.put(FREE, true);
	}

	private void placeNumbersOnBoard(final List<String> numbers, final Map<String, Boolean> calledNumbers) {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (centerOfBoard(x, y)) {
					board[x][y] = FREE;
					y++;
				}

				final String number = numbers.remove(0);
				board[x][y] = number;
			}
		}
	}

	private boolean centerOfBoard(final int x, final int y) {
		return x == 2 && y == 2;
	}

	public void printBoard() {
		System.out.println("Player: " + player);

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

	private void printLine(final int x) {
		for (int y = 0; y < WIDTH; y++) {
			if (numberIsCalledAt(calledNumbers, board, x, y)) {
				System.out.printf("|%3s", "X");
			} else {
				System.out.printf("|%3s", board[x][y]);
			}
		}

		System.out.println("|");
	}

	private void printRowSeparator() {
		System.out.println("|---|---|---|---|---|");
	}

	public void printSimple() {
		printBoard(board);
	}

	private void printBoard(final String[][] board) {
		for (int y = 0; y < 5; y++) {
			System.out.print("|");
			for (int x = 0; x < 5; x++) {
				System.out.print(board[y][x] + "|");
			}
			System.out.println("");
			System.out.println("-------------");
		}
	}

	public void markNumber(final String value) {
		calledNumbers.put(value, Boolean.TRUE);
	}

	public boolean hasWinningRow() {
		return evalBoard();
	}

	public String getPlayer() {
		return player;
	}

	private boolean evalBoard() {
		final boolean horizontalWin = checkHorizontally(calledNumbers, board);
		final boolean verticalWin = checkVertically(calledNumbers, board);
		final boolean diagonallyWin = checkDiagonally(calledNumbers, board);
		final boolean diagonallyR = checkDiagonallyTopRight(calledNumbers, board);
		return horizontalWin || verticalWin || diagonallyWin || diagonallyR;
	}

	public boolean checkHorizontally(final Map<String, Boolean> calledNumbers, final String[][] board) {
		for (int y = 0; y < WIDTH; y++) {
			boolean result = true;
			for (int x = 0; x < HEIGHT; x++) {
				final Boolean statusAt = numberIsCalledAt(calledNumbers, board, y, x);
				result = result && statusAt;
			}

			if (result) {
				return result;
			}
		}

		return false;
	}

	public boolean checkVertically(final Map<String, Boolean> calledNumbers, final String[][] board) {
		for (int y = 0; y < HEIGHT; y++) {
			boolean result = true;
			for (int x = 0; x < WIDTH; x++) {
				final Boolean statusAt = numberIsCalledAt(calledNumbers, board, x, y);
				result = result && statusAt;
			}

			if (result) {
				return result;
			}
		}

		return false;
	}

	public boolean checkDiagonally(final Map<String, Boolean> calledNumbers, final String[][] board) {
		boolean result = true;
		for (int i = 0; i < WIDTH; i++) {
			final Boolean statusAt = numberIsCalledAt(calledNumbers, board, i, i);
			result = result && statusAt;
		}

		return result;
	}

	public boolean checkDiagonallyTopRight(final Map<String, Boolean> calledNumbers, final String[][] board) {
		boolean result = true;
		final int farRight = WIDTH - 1;
		for (int i = 0; i < WIDTH; i++) {
			final Boolean statusAt = numberIsCalledAt(calledNumbers, board, farRight - i, i);
			result = result && statusAt;
		}

		return result;
	}

	private Boolean numberIsCalledAt(final Map<String, Boolean> calledNumbers, final String[][] board, final int x,
			final int y) {
		final String number = board[x][y];
		return calledNumbers.containsKey(number) && calledNumbers.get(number);
	}
}