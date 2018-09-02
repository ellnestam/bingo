package future;

/**
 *
 * @author Dom
 */
public class BingoTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {

            BingoGame game = new BingoGame(4);

            for(int i=1; i<=25; i++)
                game.addEvent(Integer.toString(i));
            game.startGame();

    }//end main
}//end class