package future;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BingoGame {

	private final int playerCount;
	private final List<BingoBoard> boards;

	public BingoGame(final int players) {
		boards = new ArrayList<>();
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
			boards.add(board);
			board.printBoard();
		}

		boolean winnerDetermined = false;

		final Scanner in = new Scanner(System.in);
		while (!winnerDetermined) {
			System.out.println("Enter Event:");
			final String check = in.next();
			for (final BingoBoard board : boards) {
				board.markNumber(check);
				board.printBoard();

				winnerDetermined = board.checkWin() || winnerDetermined;
			}
		}

		printAllWinners();

	}

	private void printAllWinners() {
		boards.stream()
				.filter(b -> b.hasWinningRow())
				.forEach(b -> System.out.printf("Player %s wins!\n\n", b.getPlayer()));
	}

}
