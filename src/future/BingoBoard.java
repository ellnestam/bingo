package future;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BingoBoard {
	private final String board[][];
	private final int BOARD_DIM = 5;
	private final int WIDTH = 5;
	private final int HEIGHT = 5;
	private final int BOARD_SIZE = WIDTH * HEIGHT;

	private final Map<String, Boolean> eventCalledMap;
	private final List<String> events;
	private final List<String> selectedEvents;

	private final String FREE = "FREE SPACE";
	private final int player;
	private boolean win;

	public BingoBoard(final ArrayList<String> eventList, final int numb) {
		board = new String[WIDTH][HEIGHT];
		selectedEvents = new ArrayList<>();
		events = eventList;
		eventCalledMap = new HashMap<>();
		eventCalledMap.put(FREE, true);
		player = numb;
		win = false;
	}

	public void updateEvents(final ArrayList<String> eventList) {
		events.addAll(eventList);
	}

	// Chooses events and adds them to the board.
	public boolean randomizeEvents() {
		if (this.events.size() < BOARD_SIZE - 1) {
			return false;
		}
		while (selectedEvents.size() < BOARD_SIZE - 1) {
			final Random rand = new Random();
			final int index = rand.nextInt(this.events.size());
			final String str = events.get(index);
			selectedEvents.add(str);
			events.remove(str);
		}

		int count = 0;

		for (final String str : selectedEvents) {
			eventCalledMap.put(str, false);
			if (count == BOARD_SIZE / 2) {
				board[count / BOARD_DIM][count % BOARD_DIM] = FREE;
				count++;
			}
			board[count / BOARD_DIM][count % BOARD_DIM] = str;
			count++;
		}
		return true;
	}

	public void printBoard() {
		System.out.printf("Player %d\n", this.player);

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
		for (int j = 0; j < BOARD_DIM; j++) {
			if (eventCalledMap.get(board[i][j]) == true) {
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
		if (eventCalledMap.containsKey(value)) {
			eventCalledMap.put(value, Boolean.TRUE);
		}
	}

	/*
	 * Checks board for a win and returns true if board won and false otherwise.
	 */
	public boolean checkWin() {
		this.win = evalBoard();
		return this.win;
	}

	public boolean hasWinningRow() {
		return this.win;
	}

	public int getPlayer() {
		return player;
	}

	// Checks the board for win. Returns true if a win is found.
	private boolean evalBoard() {
		int i, j, count;

		for (i = 0; i < BOARD_DIM; i++) {
			j = 0;
			count = 0;
			// Checks horizontally for a win.
			while (eventCalledMap.get(board[i][j]) != false) {
				count++;
				j++;
				if (count == BOARD_DIM) {
					return true;
				}
			} // end while

			j = 0;
			count = 0;

			// Checks verically for a win.
			while (eventCalledMap.get(board[j][i]) != false) {
				count++;
				j++;
				if (count == BOARD_DIM) {
					return true;
				}
			}
		}

		i = 0;
		count = 0;

		// Checks the top left to bottom right diagnal for a win.
		while (eventCalledMap.get(board[i][i]) != false) {
			count++;
			i++;
			if (count == BOARD_DIM) {
				return true;
			}
		}

		i = BOARD_DIM - 1;
		j = 0;
		count = 0;
		// Checks the top left to bottom right diagnal for a win.
		while (eventCalledMap.get(board[i][j]) != false) {
			count++;
			i--;
			j++;
			if (count == BOARD_DIM) {
				return true;
			}
		}

		return false;
	}
}