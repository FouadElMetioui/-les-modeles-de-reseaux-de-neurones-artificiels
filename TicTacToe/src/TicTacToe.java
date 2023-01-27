import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Initializes game and controls input and output.
 * 
 * @author cyrilyared
 *
 */
public class TicTacToe {
	
	public static void main(String[] args) {
		Scanner userInput;
		userInput = new Scanner(System.in);
		
		while(userOptions(userInput)) {
			int playerSetting = playerSetting(userInput);
			playGame(userInput, playerSetting);
		}
	}
	
	/**
	 * Prints user options to initialize a new game and obtaining training data for neural network.
	 * Reads user input.
	 * 
	 * @param userInput scanner that reads console input stream
	 * @return boolean based on user input
	 */
	private static boolean userOptions(Scanner userInput) {
		int newGameFormatted = -1;
		do{
			System.out.println("Would you like to play a new game (y/n)?");
			newGameFormatted = yesNoOption(userInput.nextLine());
		}while(newGameFormatted == -1);
			
		if(newGameFormatted == 0) {
			int trainNeuralNetwork = -1;
			do{
				System.out.println("Would you like to get training data for the neural network (y/n)?");
				trainNeuralNetwork = yesNoOption(userInput.nextLine());
			}while(trainNeuralNetwork == -1);
			
			if(trainNeuralNetwork == 1) {
				getTrainingDataSettings(userInput);
			}
			return false;
		}
		return true;
	}
	
	/**
	 * Prints player settings and reads user input.
	 * 
	 * @param userInput scanner that reads console input stream
	 * @return playerSetting selected
	 */
	private static int playerSetting(Scanner userInput) {
		int playerSetting = -1;
		do{
			System.out.println("Who are the players?");
			System.out.println("1 for Human vs. Human, 2 for Human vs. Random, 3 for Random vs. Random");
			System.out.println("4 for Human vs. Minimax, 5 for Minimax vs. Random, 6 for Minimax vs. Minimax");
			playerSetting = numberFormat(userInput.nextLine(), 1, 6);
		}while(playerSetting == -1);
		return playerSetting;
	}
	
	/**
	 * Creates an object of the class Board.
	 * Sets the new board values.
	 * Prints the current state and switches users.
	 * Checks for any potential wins or draws.
	 * 
	 * @param userInput scanner that reads console input stream
	 * @param playerSetting setting selected for user type
	 */
	private static void playGame(Scanner userInput, int playerSetting) {
		Board currentBoard = new Board();
		Random randomGen = new Random();
		int currentPlayer = randomNumberInRange(1, 2, randomGen);
		int winner;
		
		while(currentBoard.isPlaying()) {
			System.out.println("Player " + String.valueOf(currentPlayer) + " move.");
			int move = playMove(currentPlayer, currentBoard, playerSetting, userInput);
			currentBoard.setBoardValue(currentPlayer, move);
			currentBoard.printBoard();
			currentPlayer = currentBoard.switchPlayer(currentPlayer);
		}
		winner = currentBoard.checkWin();
		
		if(winner == 0) {
			System.out.println("No player has won."); 
		} else {
			System.out.println("Player " +  String.valueOf(winner) + " has won.");
		}	
	}
	
	/**
	 * Selects a move for player currentPlayer on the board currentBoard.
	 * 
	 * @param currentPlayer player selecting move
	 * @param currentBoard instance of class Board
	 * @param playerSetting setting selected for user type
	 * @param userInput scanner that reads console input stream
	 * @return move (value between 1 and 9)
	 */
	private static int playMove(int currentPlayer, Board currentBoard, int playerSetting, Scanner userInput) {
		RandomPlayer randomPlayer = new RandomPlayer();
		int move = 0;
		
		switch(playerSetting) {
			case 1:
				move = playerMove(userInput, currentBoard, currentPlayer);
				break;
			case 2:
				if(currentPlayer == 1) {
					move = playerMove(userInput, currentBoard, currentPlayer);
				} else {
					move = randomPlayer.getMove(currentBoard);
				}
				break;
			case 3:
				move = randomPlayer.getMove(currentBoard);
				break;
			case 4:
				if(currentPlayer == 1) {
					move = playerMove(userInput, currentBoard, currentPlayer);
				} else {
					Minimax minimaxPlayer = new Minimax(currentPlayer);
					move = minimaxPlayer.getMove(currentBoard);
				}
				break;
			case 5:
				if(currentPlayer == 1) {
					Minimax minimaxPlayer = new Minimax(currentPlayer);
					move = minimaxPlayer.getMove(currentBoard);
				} else {
					move = randomPlayer.getMove(currentBoard);
				}
				break;
			case 6:
				Minimax minimaxPlayer = new Minimax(currentPlayer);
				move = minimaxPlayer.getMove(currentBoard);
				break;
			default:
				break;
		}
		return move;
	}
	
	/**
	 * Reads user input to select move.
	 * Verifies move available.
	 * 
	 * @param userInput scanner that reads console input stream
	 * @param currentBoard instance of class Board
	 * @param currentPlayer player selecting move
	 * @return selected move
	 */
	private static int playerMove(Scanner userInput, Board currentBoard, int currentPlayer) {
		System.out.println("Player " + String.valueOf(currentPlayer) + " Move (1-9): ");
		int move = numberFormat(userInput.nextLine(), 1, 9);
		
		while(move == -1 || currentBoard.isPosEmpty(move) == false) {
			System.out.println("Enter a value between 1 and 9 with an empty slot.");
			move = numberFormat(userInput.nextLine(), 1, 9);
		}
		return move;
	}
	
	/**
	 * Obtains settings for the training data.
	 * 
	 * @param userInput scanner that reads console input stream
	 */
	private static void getTrainingDataSettings(Scanner userInput) {
		int numberOfTrainingExamples = -1;
		do{
			System.out.println("How many training examples would you like?");
			numberOfTrainingExamples = numberFormat(userInput.nextLine(), 1, Integer.MAX_VALUE);
		}while(numberOfTrainingExamples == -1);
		
		String filename;
		do{
			System.out.println("Please enter the filename.");
			filename = userInput.nextLine();
		}while(filename == null || filename == "");
		
		getTrainingData(numberOfTrainingExamples, filename, userInput);
	}
	
	/**
	 * Simulates game and prints training data into file specified by filename.
	 * 
	 * @param m number of training examples
	 * @param filename
	 * @param userInput scanner that reads console input stream
	 */
	private static void getTrainingData(int m, String filename, Scanner userInput) {		
		try(FileWriter fw = new FileWriter(filename, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter output = new PrintWriter(bw))
			{
			int iter = 0;
			int playerSetting = playerSetting(userInput);
			Random randomGen = new Random();
			Board currentBoard = new Board();
			while(iter < m) {
				int currentPlayer = randomNumberInRange(1, 2, randomGen);
			
				while(currentBoard.isPlaying() && iter < m) {
					int move = playMove(currentPlayer, currentBoard, playerSetting, userInput);
					if(currentPlayer == 1) {
						printTrainingData(currentBoard, move, output);
						iter++;
					}
					currentBoard.setBoardValue(currentPlayer, move);
					currentPlayer = currentBoard.switchPlayer(currentPlayer);
				}
				currentBoard.resetBoard();
			}
		}catch(IOException e){
			System.out.println("I/O exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Prints training data on a new line of the specified file.
	 * 
	 * @param currentBoard state of instance of Board before applying move
	 * @param move selected for player 1
	 * @param output instance of PrintWriter
	 */
	private static void printTrainingData(Board currentBoard, int move, PrintWriter output) {
		String line = "";
		for(int i = 0; i < 9; i++) {
			int value = currentBoard.getPositionValue(i);
			if(value == 2) {
				line += -1;
			} else {
				line += String.valueOf(value);
			}
			line += ", ";
		}
		line += String.valueOf(move);
		output.println(line);
	}
	
	/**
	 * Returns integer from user input string.
	 * 
	 * @param input string input from the user
	 * @param min minimum integer accepted
	 * @param max maximum integer accepted
	 * @return integer formatted result
	 */
	private static int numberFormat(String input, int min, int max) {
		int result;
		try {
			result = Integer.parseInt(input);
		} catch(NumberFormatException e) {
			return -1;
		}
		
		if(result >= min && result <= max) {
			return result;
		}
		return -1;
	}
	
	/**
	 * Returns 1 if yes selected, 0 if no selected or -1 if formatting error.
	 * 
	 * @param input string input from user
	 * @return integer result
	 */
	private static int yesNoOption(String input) {
		if(input.toUpperCase().equals("Y")) {
			return 1;
		} else if(input.toUpperCase().equals("N")) {
			return 0;
		} else {
			return -1;
		}
	}
	
	/**
	 * Returns a random integer in a specified range.
	 * 
	 * @param min minimum of range
	 * @param max maximum of range
	 * @param randomGen instance of class random
	 * @return random integer in range
	 */
	public static int randomNumberInRange(int min, int max, Random randomGen) {
		return randomGen.nextInt(max - min + 1) + min;
	}
}