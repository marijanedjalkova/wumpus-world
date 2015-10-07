import java.util.Arrays;
import java.util.List;

/** Represents the game "Wumpus world". Controls the boards, players, actions. */
public class Game {
	/** true if the game has ended */
	private boolean finished;
	/** the main board with all of the information */
	private Board gBoard;
	/** the player version of the board */
	private Board ai_board;
	/** simple player step counter */
	private int stepCount;
	/** Moving object of the game controlled by the player */
	public Adventurer character;
	/** Moving object of the game, enemy of the player */
	public Wumpus wumpus;
	/**
	 * Moving object of the game that moves the player to a random location
	 */
	public SuperBat superBat;
	/** List of allowed commands in the game */
	private List<Character> dictionary;
	/** Player itself */
	private AIPlayer player;

	/**
	 * Constructor.
	 * 
	 * @param complexity
	 *            - how complicated on a scale from 1 to 10 the game is. Mainly
	 *            affects the number of pits.
	 */
	public Game(int complexity) {
		int size = 10;
		character = new Adventurer(-1, -1, this);

		wumpus = new Wumpus(-1, -1, this);
		superBat = new SuperBat(-1, -1, this);
		gBoard = new Board(size, this);
		gBoard.initialize(complexity);
		finished = false;
		stepCount = 0;
		dictionary = Arrays.asList('n', 's', 'w', 'e', 'u', 'd', 'l', 'r');
		ai_board = new Board(size, this);
		player = new AIPlayer(ai_board, character.location,
				gBoard.getCell(character.location));
	}

	/** @return game board with all of the information */
	public Board getBoard() {
		return gBoard;
	}

	/** @return number of steps that the player has made by now */
	public int getStepCount() {
		return stepCount;
	}

	/**
	 * Checks if the move is valid and moves the character.
	 * 
	 * @return false if the move is invalid
	 */
	public boolean process(char move) {
		if (dictionary.contains(move)) {
			gBoard.move(character, move);
			return true;
		}
		return false;
	}

	/** Finishes the game */
	private void lost() {
		System.out.println("Sorry, you lost!");
		finished = true;
	}

	/**
	 * Checks the winning conditions.
	 * 
	 * @return true if the player won, false otherwise
	 */
	private boolean won() {
		if (gBoard.getCell(character.getLocation()) instanceof ExitCell) {
			if (character.collectedTreasure()) {
				System.out.println("Congratulations, you won!");
				finished = true;
				return true;
			}
		}
		return false;
	}

	/** Checks the new state of the game after every step. */
	public void checkNewState() {
		Location newLocation = character.getLocation();
		if (wumpus.isAlive() && newLocation.equalsTo(wumpus.getLocation())) {
			lost();
			return;
		}
		if (gBoard.getCell(newLocation) instanceof PitCell) {
			lost();
			return;
		}

		if (newLocation.equalsTo(superBat.getLocation())) {
			superBat.move(character, gBoard);
		}
		if (gBoard.getCell(newLocation) instanceof TreasureCell) {
			character.collectTreasure();
			return;
		}
		if (won()) {
			return;
		}

	}

	/**
	 * Main game loop is here. Prints the board and the new cell's details, then
	 * takes a move, then processes it and checks the new state
	 */
	public void start() {
		gBoard.print(player, true, gBoard.getCell(character.location));
		char move;
		Cell currentCell = gBoard.getCell(character.location);
		System.out
				.println("Game started: move using NSWE and shoot using UDLR");
		while (!finished) {
			currentCell = gBoard.getCell(character.location);
			ai_board.print(player, false, currentCell);
			currentCell.printPercepts();
			System.out.println("Your step:");
			move = player.makeMove(currentCell);
			System.out.println(move);
			if (!process(move)) {
				System.out.println("Not valid, try again:");
				continue;
			}
			stepCount++;
			checkNewState();
		}
	}

}
