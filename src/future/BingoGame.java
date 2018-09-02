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

	// Main driver for the game.
	public void startGame() {
		this.winnerDetermined = false;
		for (int i = 1; i <= this.playerCount; i++) {
			final ArrayList<String> events = (ArrayList<String>) eventList.clone();
			final BingoBoard board = new BingoBoard(events, i);
			board.randomizeEvents();
			this.boardList.add(board);
			board.printBoard();
		}

		final Scanner in = new Scanner(System.in);
		while (this.winnerDetermined == false) {
			System.out.println("Enter Event:");
			final String check = in.next();
			for (final BingoBoard boards : boardList) {
				boards.putMarker(check);
				boards.printBoard();
				if (winnerDetermined == false) {
					winnerDetermined = boards.checkWin();
				} else {
					boards.checkWin();
				}
			}
		}

		this.printWinner();

	}

	// Prints out winning boards. More than one player may win.
	private void printWinner() {
		for (final BingoBoard boards : boardList) {
			if (boards.won()) {
				System.out.printf("Player %d wins!\n\n", boards.getPlayer());
			}
		}
	}

}
