import java.util.ArrayList;
import java.util.Random;
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
		visitedLocations.add(l);
		
		if (plan.size()>0){
			//might want to still process every cell, TODO think!
			return firstOfPlanned(l);
		}
		
		Cell curCell = real_board.getCell(l);
		
		if (curCell instanceof ExitCell){
			exitLoc = l; //remember for future
		}
		
		if (curCell instanceof TreasureCell && knowExit()){
			//go back
			//TODO think of a better way to make path
			pathBackToExit();
			return firstOfPlanned(l);
			
		}
		
		
		
		if (!curCell.breezes() && !curCell.smells() && !curCell.glitters()){
			ai_board.getBoardObject()[l.getX()][l.getY()] = new EmptyCell(l, ai_board);
		}
		
		if (curCell.breezes()){
			
		}
		if (curCell.glitters()){
			
		}
		if (curCell.smells()){
			
		}

		return firstOfPlanned(l);
	}
	
	private void pathBackToExit(){
		int count = visitedLocations.size() - 2;
		while (count > 0){
			plan.add(visitedLocations.get(count));
			if (ai_board.getCell(visitedLocations.get(count)) instanceof ExitCell)
				count = 0;
			count--;
		}
	}
	
	public boolean adjacent(Location l1, Location l2){
		int one_dir_dist = 0;
		if (l1.getX() == l2.getX()){
			one_dir_dist = Math.abs(l1.getY() - l2.getY());
		}
		else if (l1.getY() == l2.getY()){
			one_dir_dist = Math.abs(l1.getY() - l2.getY());
		}
		if (one_dir_dist == 1 || one_dir_dist == ai_board.getSize())
			return true;
		return false;
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
	
	private char randomMove(){
		Random rn = new Random();
		int choice = rn.nextInt(3);
		switch (choice){
		case 0:
			return 'n';
		case 1:
			return 's';
		case 2: 
			return 'w';
		case 3:
			return 'e';
		default:
			return 'e';
		}
	}
	
	private Location chooseRandom(ArrayList<Location> list){
		Random rn = new Random();
		int choice = rn.nextInt(list.size());
		return list.get(choice);
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
