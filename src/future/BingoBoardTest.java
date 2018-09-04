package future;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class BingoBoardTest {

	final Map<String, Boolean> calledNumbers = new HashMap<>();
	final String[][] board = new String[5][5];

	@Before
	public void setUp() {
		addRowTo(board, 0, "1", "3", "5", "7", "9");
		addRowTo(board, 1, "2", "4", "6", "8", "10");
		addRowTo(board, 2, "11", "13", "15", "17", "19");
		addRowTo(board, 3, "12", "14", "16", "18", "20");
		addRowTo(board, 4, "21", "22", "23", "24", "25");
	}

	@Test
	public void horizontalWin() {
		calledNumbers.put("1", true);
		calledNumbers.put("3", true);
		calledNumbers.put("5", true);
		calledNumbers.put("7", true);
		calledNumbers.put("9", true);

		assertTrue(Verify.checkHorizontally(calledNumbers, board));
		assertFalse(Verify.checkVertically(calledNumbers, board));
	}

	@Test
	public void horizontalWinRow2() {
		calledNumbers.put("2", true);
		calledNumbers.put("4", true);
		calledNumbers.put("6", true);
		calledNumbers.put("8", true);
		calledNumbers.put("10", true);

		assertTrue(Verify.checkHorizontally(calledNumbers, board));
		assertFalse(Verify.checkVertically(calledNumbers, board));
	}

	@Test
	public void verticalWin() {
		calledNumbers.put("9", true);
		calledNumbers.put("10", true);
		calledNumbers.put("19", true);
		calledNumbers.put("20", true);
		calledNumbers.put("25", true);

		assertTrue(Verify.checkVertically(calledNumbers, board));
		assertFalse(Verify.checkHorizontally(calledNumbers, board));
	}

	@Test
	public void DiagonalWin() {
		calledNumbers.put("1", true);
		calledNumbers.put("4", true);
		calledNumbers.put("15", true);
		calledNumbers.put("18", true);
		calledNumbers.put("25", true);

		assertTrue(Verify.checkDiagonally(calledNumbers, board));
		assertFalse(Verify.checkHorizontally(calledNumbers, board));
	}

	@Test
	public void DiagonalWinFlipped() {
		calledNumbers.put("21", true);
		calledNumbers.put("14", true);
		calledNumbers.put("15", true);
		calledNumbers.put("8", true);
		calledNumbers.put("9", true);

		assertTrue(Verify.checkDiagonallyFlipped(calledNumbers, board));
		assertFalse(Verify.checkHorizontally(calledNumbers, board));
	}

	void printBoard(final String[][] board) {
		for (int y = 0; y < 5; y++) {
			System.out.print("|");
			for (int x = 0; x < 5; x++) {
				System.out.print(board[y][x] + "|");
			}
			System.out.println("");
			System.out.println("-------------");
		}
	}

	private void addRowTo(final String[][] board, final int rowNumber, final String... values) {
		int x = 0;
		for (final String value : values) {
			board[rowNumber][x] = value;
			x++;
		}
	}
}
