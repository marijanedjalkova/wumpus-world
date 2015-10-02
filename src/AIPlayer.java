import java.util.ArrayList;
import java.util.Scanner;


public class AIPlayer {
	Scanner user_input;
	Game game;
	ArrayList<Location> visitedLocations, plan;
	Location current;
	Adventurer character;
	Location exitLoc; //may not know
	Board ai_board, real_board; // ai's version, will be filled gradually
	
	
	public AIPlayer(Game g){
		game = g;
		character = g.character;
		user_input = new Scanner( System.in );
		visitedLocations = new ArrayList<Location>();
		plan = new ArrayList<Location>(); //for when we have a clear path
		exitLoc = new Location(-1, -1);
		ai_board = new Board(game.getBoard().getSize(), game);
		real_board = game.getBoard();
		initialize_ai_board();
	}
	
	private void initialize_ai_board(){
		Cell[][] array = new Cell[ai_board.getSize()][ai_board.getSize()];
		for (int i = 0; i < ai_board.getSize(); i++){
			for (int j = 0; j < ai_board.getSize(); j++){
				array[j][i] = new UnknownCell(j, i, ai_board);
			}
		}
		ai_board.setBoardObject(array);
	}
	
	
	boolean knowExit(){
		return (exitLoc.getX() != -1 && exitLoc.getY() != -1);
	}
	

	public char makeMove(){
		Location l = character.location;
		visitedLocations.add(l);
		
		if (plan.size()>0){
			char move = moveToChar(l, plan.get(0));
			plan.remove(0);
			return move;
		}
		
		Cell curCell = real_board.getCell(l);
		if (curCell instanceof ExitCell){
			exitLoc = l; //remember for future
		}
		
		if (character.collectedTreasure() && knowExit()){
			//make a path to Exit, make the first step if possible
			
		}
		if (curCell.breezes()){
			
		}
		if (curCell.glitters()){
			
		}
		if (curCell.smells()){
			
		}
		if (!curCell.breezes() && !curCell.smells() && !curCell.glitters()){
			
		}
		return 'n';
	}
	
	private char moveToChar(Location l1, Location l2){
		if (l1.getX() == l2.getY()){
			if (l1.getY() - l2.getY() == 1)
				return 'n';
			else 
				return 's';
		} else {
			if (l1.getX() - l2.getX() == 1)
				return 'w';
			else
				return 'e';
		}
	}
	
	
	
	

}
