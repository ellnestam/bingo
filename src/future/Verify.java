package future;

import java.util.Map;

public class Verify {

	public static boolean checkDiagonallyFlipped(final Map<String, Boolean> calledNumbers, final String[][] board) {
		boolean result = true;
		final int farRight = BingoBoard.WIDTH - 1;
		for (int i = 0; i < BingoBoard.WIDTH; i++) {
			result = result && isCalledAt(calledNumbers, board, farRight - i, i);
		}

		return result;
	}

	public static boolean checkDiagonally(final Map<String, Boolean> calledNumbers, final String[][] board) {
		boolean result = true;
		for (int i = 0; i < BingoBoard.WIDTH; i++) {
			result = result && isCalledAt(calledNumbers, board, i, i);
		}

		return result;
	}

	public static boolean checkVertically(final Map<String, Boolean> calledNumbers, final String[][] board) {
		for (int y = 0; y < BingoBoard.HEIGHT; y++) {
			boolean result = true;
			for (int x = 0; x < BingoBoard.WIDTH; x++) {
				result = result && isCalledAt(calledNumbers, board, x, y);
			}

			if (result) {
				return result;
			}
		}

		return false;
	}

	public static boolean checkHorizontally(final Map<String, Boolean> calledNumbers, final String[][] board) {
		for (int y = 0; y < BingoBoard.WIDTH; y++) {
			boolean result = true;
			for (int x = 0; x < BingoBoard.HEIGHT; x++) {
				result = result && isCalledAt(calledNumbers, board, y, x);
			}

			if (result) {
				return result;
			}
		}

		return false;
	}

	static Boolean isCalledAt(final Map<String, Boolean> numbers, final String[][] board, final int x, final int y) {
		final String number = board[x][y];
		return numbers.containsKey(number) && numbers.get(number);
	}

}
