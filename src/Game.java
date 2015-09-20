
public class Game {
	private boolean finished;
	private Board gBoard;
	private int stepCount;
	
	public Game(){
		gBoard = new Board(10);//this should initialise Board, too
		finished = false;
		stepCount = 0;
	}
	
	public boolean isFinished(){
		return finished;
	}
	
	public int getStepCount(){
		return stepCount;
	}
	
}
