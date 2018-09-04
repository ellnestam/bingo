package future;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BingoBoard {

	final static int WIDTH = 5;
	final static int HEIGHT = 5;
	private final String board[][] = new String[WIDTH][HEIGHT];

	public final static int BOARD_SIZE = WIDTH * HEIGHT;

	private final Map<String, Boolean> calledNumbers = new HashMap<>();

	private final String FREE = "FREE SPACE";
	private final String playerName;

	public BingoBoard(final List<String> numbersForBoard, final String playerName) {
		this.playerName = playerName;
		placeNumbersOnBoard(shuffle(numbersForBoard));
	}

	public void printBoard() {
		System.out.println(playerName);
		printTopRow();
		printBoardRows();
		printBottomRow();
	}

	public boolean hasWinningRow() {
		final boolean horizontalWin = Verify.checkHorizontally(calledNumbers, board);
		final boolean verticalWin = Verify.checkVertically(calledNumbers, board);
		final boolean diagonallyWin = Verify.checkDiagonally(calledNumbers, board);
		final boolean diagonallyR = Verify.checkDiagonallyTopRight(calledNumbers, board);
		return horizontalWin || verticalWin || diagonallyWin || diagonallyR;
	}

	public String player() {
		return playerName;
	}

	public void markNumber(final String value) {
		calledNumbers.put(value, Boolean.TRUE);
	}

	private List<String> shuffle(final List<String> boardNumbers) {
		final List<String> shuffeledNumbers = new ArrayList<>(boardNumbers);
		Collections.shuffle(shuffeledNumbers, new Random());
		return shuffeledNumbers;
	}

	private void markCenterCalled() {
		calledNumbers.put(FREE, true);
	}

	private void placeNumbersOnBoard(final List<String> numbers) {
		markCenterCalled();

		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (centerOfBoard(x, y)) {
					board[x][y] = FREE;
					y++;
				}

				board[x][y] = numbers.remove(0);
			}
		}
	}

	private boolean centerOfBoard(final int x, final int y) {
		return x == 2 && y == 2;
	}

	private void printBoardRows() {
		for (int y = 0; y < HEIGHT; y++) {
			printRowSeparator();
			printNumberOrXifCalled(y);
		}
		printRowSeparator();
	}

	private void printTopRow() {
		System.out.println("");
	}

	private void printBottomRow() {
		System.out.println("\n");
	}

	private void printNumberOrXifCalled(final int x) {
		for (int y = 0; y < WIDTH; y++) {
			final String s = Verify.numberIsCalledAt(calledNumbers, board, x, y) ? "X" : board[x][y];
			System.out.printf("|%3s", s);
		}

		System.out.println("|");
	}

	private void printRowSeparator() {
		System.out.println("|---|---|---|---|---|");
	}
}