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
		presentBoards();

		boolean winnerDetermined = false;

		final Scanner userInput = new Scanner(System.in);
		while (!winnerDetermined) {
			askUserForInput("Enter drawn number:");
			final String number = userInput.next();

			winnerDetermined = doTurn(winnerDetermined, number);
		}

		printAllWinners();
	}

	private void askUserForInput(final String text) {
		System.out.println(text);
	}

	private boolean doTurn(boolean winnerDetermined, final String number) {
		for (final BingoBoard board : boards) {
			board.markNumber(number);
			board.printBoard();
			winnerDetermined = board.hasWinningRow() || winnerDetermined;
		}
		return winnerDetermined;
	}

	private void presentBoards() {
		for (final BingoBoard board : boards) {
			board.printBoard();
		}
	}

	private void printAllWinners() {
		boards.stream()
				.filter(b -> b.hasWinningRow())
				.forEach(b -> System.out.printf("%s wins!\n\n", b.player()));
	}

	private static List<String> numbersForGame() {
		return IntStream
				.range(1, BingoBoard.BOARD_SIZE)
				.mapToObj(number -> number + "")
				.collect(Collectors.toList());
	}
}
