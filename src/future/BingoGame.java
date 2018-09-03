package future;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BingoGame {

	private final List<String> eventList;
	private final int playerCount;
	private final boolean winnerDetermined;
	private final List<BingoBoard> boardList;

	public BingoGame(final int players) {
		eventList = new ArrayList<>();
		playerCount = players;
		winnerDetermined = false;
		boardList = new ArrayList<>();
	}

	private void addEvent(final String event) {
		eventList.add(event);
	}

	public void addEvents() {
		for (int i = 1; i <= BingoBoard.BOARD_SIZE; i++) {
			addEvent(Integer.toString(i));
		}
	}

	public void startGame() {
		for (int i = 1; i <= playerCount; i++) {
			createAndPrintPlayerBoards(i);
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

	private void createAndPrintPlayerBoards(final int i) {
		final ArrayList<String> events = new ArrayList<String>(eventList);
		final BingoBoard board = new BingoBoard(events, i);
		board.randomizeEvents();
		boardList.add(board);
		board.printBoard();
	}

	private void printAllWinners() {
		for (final BingoBoard board : boardList) {
			if (board.hasWinningRow()) {
				System.out.printf("Player %d wins!\n\n", board.getPlayer());
			}
		}
	}

}
