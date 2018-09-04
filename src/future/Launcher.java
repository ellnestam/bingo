package future;

public class Launcher {

	public static void main(final String[] args) {
		final BingoGame game = new BingoGame();
		game.addPlayer("Player 1");
		game.addPlayer("Player 2");
		game.run();
	}
}
