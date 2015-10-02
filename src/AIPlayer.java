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
		//all methods that help this one should add their moves to plan
		//in the end the first move of the plan is returned
		Location l = character.location;
		Location prev = visitedLocations.get(visitedLocations.size()-1);
		visitedLocations.add(l);
		if (adjacent(l, prev)){
			//we came here ourselves
			
		} else {
			//bat dropped us here
		}
		
		if (plan.size()>0){
			// I don't like this because there might be danger
			//might want to still process every cell
			// TODO think!
			return firstOfPlanned(l);
		}
		
		Cell curCell = real_board.getCell(l);
		if (curCell instanceof ExitCell){
			exitLoc = l; //remember for future
		}
		
		if (character.collectedTreasure() && knowExit()){
			//make a path to Exit, make the first step if possible
			
		}
		if (curCell.breezes()){
			processBreeze(l);
		}
		if (curCell.glitters()){
			
		}
		if (curCell.smells()){
			
		}
		if (!curCell.breezes() && !curCell.smells() && !curCell.glitters()){
			ai_board.getBoardObject()[l.getX()][l.getY()] = new EmptyCell(l, ai_board);
		}
		
		return firstOfPlanned(l);
	}
	
	public boolean adjacent(Location l1, Location l2){
		if ((Math.abs((l1.getX() - l2.getX()) + (l1.getY() - l2.getY()))) == 1){
			return true;
		}
		int dist = 0;
		if (l1.getX() == l2.getX()){
			dist = Math.abs(l1.getY() - l2.getY());
			
		}
		else if (l1.getY() == l2.getY()){
			dist = Math.abs(l1.getY() - l2.getY());
		}
		if (dist == 1 || dist == ai_board.getSize())
			return true;
		else return false;
	}
	
	public void processBreeze(Location l){
		//breezes on the current cell
		//take all the cells around this that we visited
		//there will be at least one, the previous one
		//unless we were dropped by the bat
	}
	
	private char firstOfPlanned(Location l){
		char move = moveToChar(l, plan.get(0));
		plan.remove(0);
		return move;
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
