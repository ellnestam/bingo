package future;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BingoGame {

	private final List<BingoBoard> boards = new ArrayList<>();
	private final List<String> numbers;

	public BingoGame() {
		numbers = numbersForGame();
	}

	public void addPlayer(final String playerName) {
		boards.add(new BingoBoard(numbers, playerName));
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
				.forEach(b -> System.out.printf("%s wins!\n\n", b.player()));
	}

	private static List<String> numbersForGame() {
		return IntStream
				.range(1, BingoBoard.BOARD_SIZE)
				.mapToObj(i -> i + "")
				.collect(Collectors.toList());
	}
}
