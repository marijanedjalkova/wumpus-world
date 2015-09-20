
public class Game {
	private boolean finished;
	private Board gBoard;
	private int stepCount;
	
	public Game(){
		gBoard = new Board(10);
		finished = false;
		stepCount = 0;
	}
	
	public boolean isFinished(){
		return finished;
	}
	
	public int getStepCount(){
		return stepCount;
	}
	
	public void start(){
		while (!finished){
			//take a step
			//move the object
			//process new data
			//check that is not lost
		}
	}
	
}
