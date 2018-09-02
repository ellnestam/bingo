package future;

import java.util.ArrayList;
import java.util.Scanner;

public class BingoGame {

	private final ArrayList<String> eventList;
	private final int playerCount;
	private boolean winnerDetermined;
	private final ArrayList<BingoBoard> boardList;

	BingoGame(final int players) {
		this.eventList = new ArrayList<>();
		this.playerCount = players;
		this.winnerDetermined = false;
		boardList = new ArrayList<>();
	}

	public void addEvent(final String event) {
		this.eventList.add(event);
	}

	public void startGame() {
		for (int i = 1; i <= this.playerCount; i++) {
			createAndPrintPlayerBoards(i);
		}

		winnerDetermined = false;

		final Scanner in = new Scanner(System.in);
		while (winnerNotDetermined()) {
			System.out.println("Enter Event:");
			final String check = in.next();
			for (final BingoBoard board : boardList) {
				board.markNumber(check);
				board.printBoard();
				if (winnerNotDetermined()) {
					winnerDetermined = board.checkWin();
				} else {
					board.checkWin();
				}
			}
		}

		this.printAllWinners();

	}

	private void createAndPrintPlayerBoards(final int i) {
		final ArrayList<String> events = (ArrayList<String>) eventList.clone();
		final BingoBoard board = new BingoBoard(events, i);
		board.randomizeEvents();
		boardList.add(board);
		board.printBoard();
	}

	private boolean winnerNotDetermined() {
		return !winnerDetermined;
	}

	private void printAllWinners() {
		for (final BingoBoard board : boardList) {
			if (board.hasWinningRow()) {
				System.out.printf("Player %d wins!\n\n", board.getPlayer());
			}
		}
	}

}
