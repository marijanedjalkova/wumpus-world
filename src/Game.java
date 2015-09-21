import java.util.Scanner;

public class Game {
	private boolean finished;
	private Board gBoard;
	private int stepCount;
	public Adventurer player;
	
	
	public Game(int complexity){
		gBoard = new Board(10, this, complexity);
		System.out.println("Board initialised");
		gBoard.print();
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
	
	public void start(){
		Scanner user_input = new Scanner( System.in );
		char move;		
		while (!finished){
			//take a step
			System.out.println("Your step:");
			move = user_input.next().charAt(0);
			if (!process(move))
				continue;
			stepCount++;
		}
		user_input.close();
	}
	
}
