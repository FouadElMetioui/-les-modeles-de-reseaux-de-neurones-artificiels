import java.util.Random;

/**
 * Plays random moves in Tic-Tac-Toe.
 * 
 * @author cyrilyared
 *
 */
public class RandomPlayer {
	
	Random randomGen;
	private int selectedMove;

	public RandomPlayer() {
		randomGen = new Random();
		selectedMove = -1;
	}
	
	/**
	 * Returns random move based on current board.
	 * 
	 * @param board 
	 * @return selectedMove
	 */
	public int getMove(Board board) {
		while(!board.isPosEmpty(selectedMove)) {
			selectedMove = TicTacToe.randomNumberInRange(1, 9, randomGen);
		}
		return selectedMove;
	}	
}