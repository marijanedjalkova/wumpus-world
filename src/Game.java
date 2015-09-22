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
	
	public void checkNewState(){
		Location newLocation = player.getLocation();
		if (newLocation.equalsTo(wumpus.getLocation())){
			player.die();
			finished = true;
			return;
		}
		if (newLocation.equalsTo(superBat.getLocation())){
			superBat.move();
		}
		if (gBoard.getCell(newLocation) instanceof TreasureCell){
			win();
			return;
		}
		if (gBoard.getCell(newLocation) instanceof PitCell){
			player.die();
			finished = true;
			return;
		}
		if (gBoard.getCell(newLocation) instanceof ExitCell){
			gBoard.exit.process();
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
	
	public void win(){
		System.out.println("Confratulations, you won!");
		finished = true;
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
		}
		user_input.close();
	}
	
}
