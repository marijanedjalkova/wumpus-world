import java.util.Scanner;


public class AIPlayer {
	Scanner user_input;
	Game game;
	
	public AIPlayer(Game g){
		game = g;
		user_input = new Scanner( System.in );
	}

	public char makeMove(){
		return user_input.next().charAt(0);
		//TODO
	}
	
	

}
