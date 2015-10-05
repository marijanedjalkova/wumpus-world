import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class AIPlayer {
	public ArrayList<Location> visitedLocations;
	private ArrayList<Location> plan, pitCells;
	public Location currentLocation;

	private Location exitLoc; // may not know
	private Board ai_board; // ai's version, will be filled
										// gradually
	private ArrayList<Cell> known_around, unknown_around;
	private boolean collectedTreasure;

	public AIPlayer(Board b, Location cur, Cell curC) {
		visitedLocations = new ArrayList<Location>();
		pitCells = new ArrayList<Location>();
		plan = new ArrayList<Location>(); // for when we have a clear path
		exitLoc = new Location(-1, -1);
		ai_board = b;
		currentLocation = cur;
		visitedLocations.add(cur);
		initialize_ai_board();
		ai_board.getBoardObject()[currentLocation.getX()][currentLocation.getY()] = curC;
	}

	private void initialize_ai_board() {
		Cell[][] array = new Cell[ai_board.getSize()][ai_board.getSize()];
		for (int i = 0; i < ai_board.getSize(); i++) {
			for (int j = 0; j < ai_board.getSize(); j++) {
				array[j][i] = new UnknownCell(j, i, ai_board);
			}
		}
		ai_board.setBoardObject(array);
	}

	boolean knowExit() {
		return (exitLoc.getX() != -1 && exitLoc.getY() != -1);
	}

	public char makeMove(Cell curCell) {
		// all methods that help this one should add their moves to plan
		// in the end the first move of the plan is returned
		currentLocation = curCell.location;
		visitedLocations.add(currentLocation);
		ai_board.getBoardObject()[currentLocation.getX()][currentLocation.getY()] = curCell;

		if (curCell instanceof ExitCell)
			exitLoc = currentLocation; // remember for future

		if (curCell instanceof TreasureCell) {
			if (firstVisit()){
				collectedTreasure = true;
				plan = new ArrayList<Location>();
			}
			if (knowExit()){
			// go back
			// TODO think of a better way
				pathBackToExit();
			}
			
		}

		if (plan.size() > 0) {
			System.out.println("Following the plan");
			return firstOfPlanned(currentLocation);
		}

		known_around = lookAround(currentLocation, true);
		unknown_around = lookAround(currentLocation, false);


		if (curCell.glitters() && !collectedTreasure) {
			// glitter is in one of the surrounding squares
			lookupTreasureNear(currentLocation, unknown_around);
			return firstOfPlanned(currentLocation);
		}

		if (!curCell.breezes() && !curCell.smells()) {
			// choose a random one from the cells we haven't been to yet
			// if we have been to all of them, choose the one closest to the
			// unknown region
			if (unknown_around.size() > 0) {
				System.out.println("Empty cell, returning a random unknown");
				return moveToChar(currentLocation, chooseRandom(unknown_around).location);
			} else {
				// TODO find the closest unknown
				System.out.println("Empty cell, visited all around, returning random");
				return moveToChar(currentLocation, chooseRandom(known_around).location);
			}
		}

		if (curCell.breezes()) {
			locatePit();
		}

		if (curCell.smells()) {
			locateWumpus();
		}
		
		if (plan.size() > 0){
			return firstOfPlanned(currentLocation);
		}
		if (unknown_around.size() > 0){
			System.out.println("No plan, returning a random unknown location");
			return moveToChar(currentLocation, chooseRandom(unknown_around).location);
		}
		else
			return moveToChar(currentLocation, chooseRandom(known_around).location);
	}
	
	public boolean firstVisit(){
		for (int i = 0; i < visitedLocations.size() - 1; i++){
			if (visitedLocations.get(i).equalsTo(currentLocation))
				return false;
		}
		return true;
	}
	
	private void locateWumpus(){
		//TODO
 
	}

	private void locatePit() {
		if (unknown_around.size() == 1) {
			Location pitLoc = unknown_around.get(0).location;
			ai_board.getBoardObject()[pitLoc.getX()][pitLoc.getY()] = new PitCell(pitLoc, ai_board);
			pitCells.add(pitLoc);
			unknown_around.remove(0);
		} else if (unknown_around.size() == 4) {
			return;
		} else if (unknown_around.size() == 2) {
			
		} else if (unknown_around.size() == 3) {
			
		}
		
	}
	
	public boolean visited(Location l){
		for (int i = 0; i < visitedLocations.size(); i++){
			if (visitedLocations.get(i).equalsTo(l))
				return true;
		}
		return false;
	}

	private void lookupTreasureNear(Location l, ArrayList<Cell> neighbours) {
		System.out.println("Looking up treasure");
		int count = 0;
		boolean flag = true;
		while (count < neighbours.size()) {
			// inspect all of the neighbour cells
			if (flag){
				System.out.println("Adding " + neighbours.get(count).location);
				plan.add(neighbours.get(count).location);
			}
			else {
				if (count < neighbours.size() - 1){
					System.out.println("Adding " + l);
					plan.add(l);
				}
				count++;
			}
			flag = !flag;
		}
	}

	private ArrayList<Cell> lookAround(Location l, boolean known) {
	
		ArrayList<Cell> result = new ArrayList<Cell>();
		
		Cell north = ai_board.getCell(ai_board.getNorth(l));
		if ((north instanceof UnknownCell) != known){
			if (!(north instanceof PitCell))
				result.add(north);
		} 
		
		Cell south = ai_board.getCell(ai_board.getSouth(l));
		if ((south instanceof UnknownCell) != known){
			if (!(south instanceof PitCell))
				result.add(south);
		} 
		
		Cell east = ai_board.getCell(ai_board.getEast(l));
		if ((east instanceof UnknownCell) != known){
			if (!(east instanceof PitCell))
				result.add(east);
		} 
		
		Cell west = ai_board.getCell(ai_board.getWest(l));
		if ((west instanceof UnknownCell) != known){
			if (!(west instanceof PitCell))
				result.add(west);
		} 
		
		return result;
	}

	private void pathBackToExit() {
		int count = visitedLocations.size() - 2;
		while (count > 0) {
			plan.add(visitedLocations.get(count));
			if (ai_board.getCell(visitedLocations.get(count)) instanceof ExitCell)
				count = 0;
			count--;
		}
	}

	public boolean adjacent(Location l1, Location l2) {
		int one_dir_dist = 0;
		if (l1.getX() == l2.getX()) {
			one_dir_dist = Math.abs(l1.getY() - l2.getY());
		} else if (l1.getY() == l2.getY()) {
			one_dir_dist = Math.abs(l1.getY() - l2.getY());
		}
		if (one_dir_dist == 1 || one_dir_dist == ai_board.getSize())
			return true;
		return false;
	}

	private char firstOfPlanned(Location l) {
		for (int i = 0; i < plan.size(); i++){
			System.out.println("-" + plan.get(i));
		}
		char move = moveToChar(l, plan.get(0));
		plan.remove(0);
		return move;
	}


	private Cell chooseRandom(ArrayList<Cell> list) {
		
		Random rn = new Random();
		int choice = rn.nextInt(list.size());
		return list.get(choice);
	}

	private char moveToChar(Location l1, Location l2) {
		System.out.println("Move to char from " + l1 + " to " + l2);
		if (l1.getX() == l2.getX()) {
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
