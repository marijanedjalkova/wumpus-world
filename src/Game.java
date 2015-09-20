
public class Game {
	boolean is_won;
	Board gBoard;
	int stepCount;
	
	public Game(){
		gBoard = new Board(10);//this should initialise Board, too
		is_won = false;
		stepCount = 0;
	}
	
}
