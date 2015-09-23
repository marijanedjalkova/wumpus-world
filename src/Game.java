import java.util.Scanner;

public class Game {
	private boolean finished;
	private Board gBoard;
	private int stepCount;
	public Adventurer player;
	public Wumpus wumpus;
	public SuperBat superBat;
	
	
	public Game(int complexity){
		int size = 10;
		player = new Adventurer();
		wumpus = new Wumpus();
		superBat = new SuperBat();
		gBoard = new Board(size, this, complexity);
		finished = false;
		stepCount = 0;
	}

	
	public int getStepCount(){
		return stepCount;
	}
	
	public boolean process(char move){
		switch(move){
		case 'n':
		case 'e':
		case 's':
		case 'w':
			gBoard.move(player, move);
			break;
		default:
			System.out.println("Not valid, try again");
			return false;
		}
		return true;
	}
	
	private void lost(){
		System.out.println("Sorry, you lost!");
		finished = true;
	}
	
	private boolean won(){
		if (gBoard.getCell(player.getLocation()) instanceof ExitCell){
			if (player.collectedTreasure()){
				System.out.println("Congratulations, you won!");
				finished = true;
				return true;
			}
		}
		return false;
	}
	
	public void checkNewState(){
		Location newLocation = player.getLocation();
		if (newLocation.equalsTo(wumpus.getLocation())){
			lost();
			return;
		}
		if (gBoard.getCell(newLocation) instanceof PitCell){
			lost();
			return;
		}
		
		if (newLocation.equalsTo(superBat.getLocation())){
			superBat.move(player, gBoard);
		}
		if (gBoard.getCell(newLocation) instanceof TreasureCell){
			player.collectTreasure();
			return;
		}
		if (won()){
			return;
		}
		if (gBoard.getCell(newLocation).smells()){
			System.out.println("This cell smells!");
		}
		if (gBoard.getCell(newLocation).glitters()){
			System.out.println("This cell glitters!");
		}
		if (gBoard.getCell(newLocation).breezes()){
			System.out.println("This cell breezes!");
		}
	}
	
	
	public void start(){
		Scanner user_input = new Scanner( System.in );
		char move;		
		while (!finished){
			//take a step
			gBoard.print();
			System.out.println("Your step:");
			move = user_input.next().charAt(0);
			if (!process(move))
				continue;
			stepCount++;
			checkNewState();
		}
		user_input.close();
	}
	
}
