
import java.util.Arrays;
import java.util.List;

public class Game {
	private boolean finished;
	private Board gBoard;
	private int stepCount;
	public Adventurer character;
	public Wumpus wumpus;
	public SuperBat superBat;
	private List<Character> dictionary;
	private AIPlayer player;
	
	
	
	public Game(int complexity){
		int size = 10;
		character = new Adventurer(-1, -1, this);
		
		wumpus = new Wumpus(-1, -1, this);
		superBat = new SuperBat(-1, -1, this);
		gBoard = new Board(size, this);
		gBoard.initialize(complexity);
		finished = false;
		stepCount = 0;
		dictionary = Arrays.asList('n', 's', 'w', 'e', 'u', 'd', 'l', 'r');
		Board ai_board = new Board(size, this);
		player = new AIPlayer(ai_board);
	}
	
	public Board getBoard(){
		return gBoard;
	}

	
	public int getStepCount(){
		return stepCount;
	}
	
	public boolean process(char move){
		if (dictionary.contains(move)){
			gBoard.move(character, move);
			return true;
		}
		return false;
	}
	
	private void lost(){
		System.out.println("Sorry, you lost!");
		finished = true;
	}
	
	private boolean won(){
		if (gBoard.getCell(character.getLocation()) instanceof ExitCell){
			if (character.collectedTreasure()){
				System.out.println("Congratulations, you won!");
				finished = true;
				return true;
			}
		}
		return false;
	}
	
	public void checkNewState(){
		Location newLocation = character.getLocation();
		if (wumpus.isAlive() && newLocation.equalsTo(wumpus.getLocation())){
			lost();
			return;
		}
		if (gBoard.getCell(newLocation) instanceof PitCell){
			lost();
			return;
		}
		
		if (newLocation.equalsTo(superBat.getLocation())){
			superBat.move(character, gBoard);
		}
		if (gBoard.getCell(newLocation) instanceof TreasureCell){
			character.collectTreasure();
			return;
		}
		if (won()){
			return;
		}
		gBoard.getCell(newLocation).printPercepts();

	}
	
	
	public void start(){
		char move;	
		Cell currentCell = gBoard.getCell(character.location);
		System.out.println("Game started:");
		while (!finished){
			//take a step
			gBoard.print();
			System.out.println("Your step:");
			currentCell = gBoard.getCell(character.location);
			move = player.makeMove(currentCell);
			if (!process(move)){
				System.out.println("Not valid, try again:");
				continue;
			}
			stepCount++;
			checkNewState();
		}
	}
	
}
