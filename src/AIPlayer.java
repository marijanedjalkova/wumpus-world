import java.util.ArrayList;
import java.util.Scanner;


public class AIPlayer {
	Scanner user_input;
	Game game;
	ArrayList<Location> visitedLocations;
	Location current;
	Adventurer character;
	Location exitLoc; //may not know
	Board ai_board;
	
	public AIPlayer(Game g){
		game = g;
		character = g.character;
		user_input = new Scanner( System.in );
		visitedLocations = new ArrayList<Location>();
		exitLoc = new Location(-1, -1);
		ai_board = new Board(game.getBoard().getSize(), game);
		initialize_board();
	}
	
	private void initialize_board(){
		Cell[][] array = new Cell[ai_board.getSize()][ai_board.getSize()];
		for (int i = 0; i < ai_board.getSize(); i++){
			for (int j = 0; j < ai_board.getSize(); j++){
				array[j][i] = new EmptyCell(j, i, ai_board);
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
		Cell curCell = game.getBoard().getCell(l);
		if (curCell instanceof ExitCell){
			exitLoc = l; //remember for future
			
		}
		if (character.collectedTreasure() && knowExit()){
			//make a path to Exit, make the first step if possible
			return firstStepToExit(l, exitLoc);
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
	
	private char firstStepToExit(Location current, Location exit){
		return 'e';
	}
	
	

}
