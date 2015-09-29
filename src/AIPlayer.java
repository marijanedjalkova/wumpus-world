import java.util.Scanner;


public class AIPlayer {
	Scanner user_input;
	
	public AIPlayer(){
		user_input = new Scanner( System.in );
	}

	public char makeMove(){
		return user_input.next().charAt(0);
		//TODO
	}
	
	

}
