package future;

import java.util.Map;

public class Verify {

	static Boolean numberIsCalledAt(final Map<String, Boolean> calledNumbers, final String[][] board, final int x,
			final int y) {
		final String number = board[x][y];
		return calledNumbers.containsKey(number) && calledNumbers.get(number);
	}

	public static boolean checkDiagonallyTopRight(final Map<String, Boolean> calledNumbers, final String[][] board) {
		boolean result = true;
		final int farRight = BingoBoard.WIDTH - 1;
		for (int i = 0; i < BingoBoard.WIDTH; i++) {
			result = result && numberIsCalledAt(calledNumbers, board, farRight - i, i);
		}
	
		return result;
	}

	public static boolean checkDiagonally(final Map<String, Boolean> calledNumbers, final String[][] board) {
		boolean result = true;
		for (int i = 0; i < BingoBoard.WIDTH; i++) {
			result = result && numberIsCalledAt(calledNumbers, board, i, i);
		}
	
		return result;
	}

	public static boolean checkVertically(final Map<String, Boolean> calledNumbers, final String[][] board) {
		for (int y = 0; y < BingoBoard.HEIGHT; y++) {
			boolean result = true;
			for (int x = 0; x < BingoBoard.WIDTH; x++) {
				result = result && numberIsCalledAt(calledNumbers, board, x, y);
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
				result = result && numberIsCalledAt(calledNumbers, board, y, x);
			}
	
			if (result) {
				return result;
			}
		}
	
		return false;
	}

}
