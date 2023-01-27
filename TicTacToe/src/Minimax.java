/**
 * Plays moves based on minimax algorithm.
 * 
 * @author cyrilyared
 *
 */
public class Minimax {
	
	private int playerNum;
	
	public Minimax(int playerNum) {
		this.playerNum = playerNum;
	}
	
	/**
	 * Returns move based on minimax calculations.
	 * 
	 * @param currentBoard
	 * @return playable move with max value
	 */
	public int getMove(Board currentBoard) {
		int[] possibleMovesScore = new int[9];
		
		for(int pos = 0; pos < 9; pos++) {
			if(currentBoard.isPosEmpty(pos + 1)) {
				Board boardCopy = currentBoard.copyBoard(currentBoard);
				boardCopy.setBoardValue(playerNum, pos + 1);
				possibleMovesScore[pos] = evaluateMove(boardCopy, boardCopy.switchPlayer(playerNum), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
			}
		}
		return (checkMaxPlayable(currentBoard, possibleMovesScore) + 1);
	}
	
	/**
	 * Simulates all moves using minimax with alpha-beta pruning and assigns values to each position.
	 * 
	 * @param currentBoard
	 * @param player player number being evaluated
	 * @param depth adjustment for future values
	 * @param alpha for alpha-beta pruning
	 * @param beta for alpha-beta pruning
	 * @return integer value for strength of move
	 */
	private int evaluateMove(Board currentBoard, int player, int depth, int alpha, int beta) {
		if(!currentBoard.isPlaying()) {
			if(currentBoard.checkWin() == playerNum) {
				return 10 - depth;
			} else if(currentBoard.checkWin() == currentBoard.switchPlayer(playerNum)) {
				return -10 + depth;
			} else {
				return 0;
			}
		}
		
		int bestValueMax = Integer.MIN_VALUE;
		int bestValueMin = Integer.MAX_VALUE;
	
		for(int pos = 0; pos < 9; pos++) {
			if(currentBoard.isPosEmpty(pos + 1)) {
				Board boardCopy = currentBoard.copyBoard(currentBoard);
				boardCopy.setBoardValue(player, pos + 1);
				
				if(player == playerNum) {
					bestValueMax = findMax(bestValueMax, evaluateMove(boardCopy, boardCopy.switchPlayer(player), depth + 1, alpha, beta));
					alpha = findMax(alpha, bestValueMax);
					if(beta <= alpha) {
						break;
					}
				} else {
					bestValueMin = findMin(bestValueMin, evaluateMove(boardCopy, boardCopy.switchPlayer(player), depth + 1, alpha, beta));
					beta = findMin(beta, bestValueMin);
					if(beta <= alpha) {
						break;
					}
				}
			}
		}
		
		if(player == playerNum) {
			return bestValueMax;
		} else {
			return bestValueMin;
		}
	}
	
	/**
	 * Returns a playable move with the maximum value.
	 * 
	 * @param currentBoard
	 * @param possibleMovesScore array with values
	 * @return maxPosPlayable position of maximum value playable
	 */
	private int checkMaxPlayable(Board currentBoard, int[] possibleMovesScore) {
		int maxPosPlayable = 0;
		int maxValue = 0;
		boolean valueFound = false;
		
		for(int pos = 0; pos < possibleMovesScore.length; pos++) {
			if(currentBoard.isPosEmpty(pos + 1)) {
				if(valueFound) {
					if(possibleMovesScore[pos] > maxValue) {
						maxValue = possibleMovesScore[pos];
						maxPosPlayable = pos;
					}
				} else {
					maxValue = possibleMovesScore[pos];
					maxPosPlayable = pos;
					valueFound = true;
				}
			}
		}
		return maxPosPlayable;
	}
	
	/**
	 * Returns maximum of two integers.
	 * 
	 * @param val1
	 * @param val2
	 * @return maximum
	 */
	private int findMax(int val1, int val2) {
		if(val1 > val2) {
			return val1;
		} else {
			return val2;
		}
	}
	
	/**
	 * Returns minimum of two integers.
	 * 
	 * @param val1
	 * @param val2
	 * @return minimum
	 */
	private int findMin(int val1, int val2) {
		if(val1 < val2) {
			return val1;
		} else {
			return val2;
		}
	}
}
