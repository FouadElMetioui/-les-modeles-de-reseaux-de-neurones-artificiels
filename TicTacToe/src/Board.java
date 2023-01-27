/**
 * Initializes a new Tic-Tac-Toe board.
 * Contains all methods for operating on board,
 * checking status of board and checking for any potential wins.
 * 
 * @author cyrilyared
 *
 */

public class Board {
	
	private int[] currentBoard = new int[9];
	private int moveNumber;
	private boolean isPlaying;
	
	public Board() {
		resetBoard();
	}
	
	public Board(int[] currentBoard, int moveNumber, boolean isPlaying) {
		for (int i = 0; i < currentBoard.length; i++) {
			this.currentBoard[i] = currentBoard[i];
		}
		
		this.moveNumber = moveNumber;
		this.isPlaying = isPlaying;
	}
	
	/**
	 * Creates a copy of the current board.
	 * 
	 * @param board to be copied
	 * @return copy of a board
	 */
	public Board copyBoard(Board board) {
		return new Board(board.currentBoard, board.moveNumber, board.isPlaying);
	}
	
	/**
	 * Initializes the board with zeros and resets the move number.
	 */
	public void resetBoard() {
		for(int i = 0; i < currentBoard.length; i++) {
			currentBoard[i] = 0;
		}
		
		moveNumber = 0;
		isPlaying = true;
	}
	
	/**
	 * Attempts to place token on the current board based on the player's number.
	 * Returns true if token successfully placed and false if otherwise.
	 * 
	 * @param player current player placing token
	 * @param position where the token is being placed from 1 to 9
	 * @return boolean depending on whether move is successful
	 */
	public boolean setBoardValue(int player, int position) {
		position--;
		if(moveNumber > 9 || currentBoard[position] != 0) {
			return false;
		}
		currentBoard[position] = player;
		moveNumber++;
		checkBoardFilled();
		checkWin();
		return true;
	}
	
	/**
	 * Returns whether current position on board occupied.
	 * 
	 * @param position location on board to check
	 * @return boolean depending on whether location filled
	 */
	public boolean isPosEmpty(int position) {
		position--;
		if(position > 8 || position < 0 || currentBoard[position] != 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if a player has won and returns the player number.
	 * 
	 * @return the player that won or zero if no current winner
	 */
	public int checkWin() {
		
		//Check vertical rows.
		for(int i = 0; i < 3; i++) {
			if(currentBoard[i] == currentBoard[i+3] && currentBoard[i] == currentBoard[i+6] && currentBoard[i] != 0) {
				isPlaying = false;
				return currentBoard[i];
			} 
		}
		
		//Check horizontal rows.
		for(int i = 0; i < 3; i++) {
			if(currentBoard[i*3] == currentBoard[i*3+1] && currentBoard[i*3] == currentBoard[i*3+2] && currentBoard[i*3] != 0) {
				isPlaying = false;
				return currentBoard[i*3];
			} 
		}
		
		//Check diagonals.
		if(currentBoard[0] == currentBoard[4] && currentBoard[0] == currentBoard[8] && currentBoard[0] != 0) {
			isPlaying = false;
			return currentBoard[0];
		}
		
		if(currentBoard[2] == currentBoard[4] && currentBoard[2] == currentBoard[6] && currentBoard[2] != 0) {
			isPlaying = false;
			return currentBoard[2];
		}
		
		return 0;
	}
	
	/**
	 * Returns integer value of the currentBoard at position input.
	 * If out of range of array, returns -1.
	 * 
	 * @param input board position
	 * @return integer based on currentBoard value
	 */
	public int getPositionValue(int input) {
		if(input >= 0 && input < currentBoard.length) {
			return currentBoard[input];
		} else {
			return -1;
		}
	}
	
	/**
	 * Returns integer moveNumber.
	 * 
	 * @return moveNumber
	 */
	public int getMoveNumber() {
		return moveNumber;
	}
	
	/**
	 * Returns boolean based on if the board is filled.
	 * 
	 * @return true if board is filled, false if otherwise
	 */
	public boolean checkBoardFilled() {
		if(moveNumber > 8) {
			isPlaying = false;
			return true;
		}
		return false;
	}
	
	/**
	 * Returns boolean based on value of isPlaying.
	 * 
	 * @return boolean isPlaying
	 */
	public boolean isPlaying() {
		return isPlaying;
	}
	
	/**
	 * Switches the current player.
	 * 
	 * @param currentPlayer
	 * @return new player
	 */
	public int switchPlayer(int currentPlayer) {
		if(currentPlayer == 1) {
			return 2;
		} else {
			return 1;
		}
	}
	
	/**
	 * Prints the current board configuration to the console output.
	 */
	public void printBoard() {
		int value;
		
		for(int i = 0; i < 3; i++) {
			System.out.print("| ");
			
			for (int j = 0; j < 3; j++) {
				value = getPositionValue(i*3+j);
				
				switch(value) {
					case 1:
						System.out.print("X ");
						break;
					case 2:
						System.out.print("O ");
						break;
					default:
						System.out.print("  ");
						break;
				}
			}
			System.out.println("|");
		}
	}
}