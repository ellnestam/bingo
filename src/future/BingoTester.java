package future;

public class BingoTester {

	public static void main(final String[] args) {

		final BingoGame game = new BingoGame(2);

		for (int i = 1; i <= 25; i++) {
			game.addEvent(Integer.toString(i));
		}
		game.startGame();

	}
}
