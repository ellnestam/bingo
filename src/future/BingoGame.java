package future;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BingoGame {

	private final List<BingoBoard> boards;

	public BingoGame() {
		boards = new ArrayList<>();
	}

	public BingoBoard addPlayer(final String playerName) {
		final List<String> numbers = new ArrayList<String>(numbers());
		final BingoBoard board = new BingoBoard(numbers, playerName);
		boards.add(board);
		board.prepareBoard();
		return board;
	}

	public void run() {
		for (final BingoBoard board : boards) {
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
				winnerDetermined = board.hasWinningRow() || winnerDetermined;
			}
		}

		printAllWinners();
	}

	private void printAllWinners() {
		boards.stream()
				.filter(b -> b.hasWinningRow())
				.forEach(b -> System.out.printf("Player %s wins!\n\n", b.getPlayer()));
	}

	private List<String> numbers() {
		return IntStream
				.range(1, BingoBoard.BOARD_SIZE)
				.mapToObj(i -> i + "")
				.collect(Collectors.toList());
	}
}
