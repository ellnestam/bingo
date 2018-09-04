package future;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BingoGame {

	private final int playerCount;
	private final List<BingoBoard> boardList;

	public BingoGame(final int players) {
		boardList = new ArrayList<>();
		playerCount = players;
	}

	private List<String> addEvents() {
		final List<String> eventList = new ArrayList<>();

		for (int i = 1; i <= BingoBoard.BOARD_SIZE; i++) {
			eventList.add(Integer.toString(i));
		}

		return eventList;
	}

	public void run() {
		final List<String> eventList = addEvents();

		for (int i = 1; i <= playerCount; i++) {
			final ArrayList<String> events = new ArrayList<String>(eventList);
			final BingoBoard board = new BingoBoard(events, i);
			board.prepareBoard();
			boardList.add(board);
			board.printBoard();
		}

		boolean winnerDetermined = false;

		final Scanner in = new Scanner(System.in);
		while (!winnerDetermined) {
			System.out.println("Enter Event:");
			final String check = in.next();
			for (final BingoBoard board : boardList) {
				board.markNumber(check);
				board.printBoard();

				winnerDetermined = board.checkWin() || winnerDetermined;
			}
		}

		printAllWinners();

	}

	private void printAllWinners() {
		for (final BingoBoard board : boardList) {
			if (board.hasWinningRow()) {
				System.out.printf("Player %d wins!\n\n", board.getPlayer());
			}
		}
	}

}
