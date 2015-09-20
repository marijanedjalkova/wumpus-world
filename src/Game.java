
public class Game {
	private boolean finished;
	private Board gBoard;
	private int stepCount;
	public Adventurer player;
	
	
	public Game(int complexity){
		gBoard = new Board(10, this, complexity);
		System.out.println("Board initialised");
		gBoard.print();
		System.out.println(player.xPos);
		System.out.println(player.yPos);
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
			finished = true;
		}
	}
	
}
