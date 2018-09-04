package future;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BingoGame {

	private final int playerCount;
	private final List<BingoBoard> boards;

	public BingoGame(final int numberOfPlayers) {
		boards = new ArrayList<>();
		playerCount = numberOfPlayers;
	}

	private List<String> numbers() {
		return IntStream
				.range(1, BingoBoard.BOARD_SIZE)
				.mapToObj(i -> i + "")
				.collect(Collectors.toList());
	}

	public void run() {
		final List<String> eventList = numbers();

		for (int i = 1; i <= playerCount; i++) {
			final ArrayList<String> numbers = new ArrayList<String>(eventList);
			final BingoBoard board = new BingoBoard(numbers, i);
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
