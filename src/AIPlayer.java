import java.util.ArrayList;
import java.util.Random;

/** Represents the ai that solves the puzzle. */
public class AIPlayer {
	/** List of visited locations, one by one, ordered */
	public ArrayList<Location> visitedLocations;

	/** List of Location to go to next. Can be empty if there is no strategy */
	private ArrayList<Location> plan;

	/** List of known pitCells locations */
	private ArrayList<Location> pitCells;

	/** Where on the board the character is right now */
	public Location currentLocation;

	/** Where the exit is, if known. */
	private Location exitLoc; // doesn't always know

	/** Player's version of the board */
	private Board ai_board; // ai's version, will be filled
							// gradually
	/**
	 * List of cells that were visited before, around the current cell
	 */
	private ArrayList<Cell> known_around;

	/** List of cells around the current one, that ai knows nothing about */
	private ArrayList<Cell> unknown_around;

	/** Stores whether the treasure has been collected. */
	private boolean collectedTreasure;

	/**
	 * Constructor.
	 * 
	 * @param b
	 *            - player's version of the board
	 * @param Location
	 *            - first Location of the character
	 * @param Cell
	 *            - the first opened Cell
	 */
	public AIPlayer(Board b, Location cur, Cell curC) {
		visitedLocations = new ArrayList<Location>();
		pitCells = new ArrayList<Location>();
		plan = new ArrayList<Location>(); // for when we have a clear path
		exitLoc = new Location(-1, -1);
		ai_board = b;
		currentLocation = cur;
		visitedLocations.add(cur);
		initialize_ai_board();
		ai_board.getBoardObject()[currentLocation.getX()][currentLocation
				.getY()] = curC;
	}

	/** Fills the board with Unknown cells. */
	private void initialize_ai_board() {
		Cell[][] array = new Cell[ai_board.getSize()][ai_board.getSize()];
		for (int i = 0; i < ai_board.getSize(); i++) {
			for (int j = 0; j < ai_board.getSize(); j++) {
				array[j][i] = new UnknownCell(j, i, ai_board);
			}
		}
		ai_board.setBoardObject(array);

	}

	/** @return true if the Exit Cell has been visited before */
	private boolean knowExit() {
		return (exitLoc.getX() != -1 && exitLoc.getY() != -1);
	}

	/** Scan the board and deduce as much as possible based on known facts */
	private void analyze_board() {
		for (int i = 0; i < ai_board.getSize(); i++) {
			for (int j = 0; j < ai_board.getSize(); j++) {
				Cell current = ai_board.getCell(j, i);
				if (current.breezes())
					checkTriangle(current);
			}
		}
	}

	/**
	 * Checks whether a cell, together with its closest diagonal cells, forms a
	 * triangle of smelly cells, that would allow to easily deduce location of a
	 * pit.
	 * 
	 * @param c
	 *            - the cell that we are working around
	 */
	private void checkTriangle(Cell c) {
		ArrayList<Cell> diags = getDiagonals(c.location);
		int count = 0;
		while (count < diags.size()) {
			if (!diags.get(count).breezes()) {
				diags.remove(count);
				count--;
			}
			count++;
		}
		// now we are only left with breezy diagonals
		// if there are three of them, can't really deduce much
		if (diags.size() == 2) {
			// can probably find the Pit
			// it is more complicated than that,
			// but the chances are pretty high
			Location l1 = diags.get(0).location;
			Location l2 = diags.get(1).location;
			if (l1.getX() == l2.getX() || l1.getY() == l2.getY()) {
				locateWith3Knowns(diags.get(0), diags.get(1));

			}
		} else if (diags.size() == 1) {
			// 2 diagonal cells are breezy
			// 2 possible locations for a pit
			// try and locate it
			locateWith2Knowns(c, diags.get(0));
		}

	}

	/**
	 * @return a command to the game based on the analysis of the current
	 *         situation
	 */
	public char makeMove(Cell curCell) {

		// see what is happening overall
		analyze_board();

		currentLocation = curCell.location;
		visitedLocations.add(currentLocation);
		ai_board.getBoardObject()[currentLocation.getX()][currentLocation
				.getY()] = curCell;

		if (curCell instanceof ExitCell)
			exitLoc = currentLocation; // remember for future

		if (curCell instanceof TreasureCell) {
			if (firstVisit()) {
				collectedTreasure = true;
				plan = new ArrayList<Location>();
			}
			if (knowExit()) {
				// go back
				// TODO think of a better way
				pathBackToExit();
			}
		}

		// if there is anything planned, simply follow it
		if (plan.size() > 0) {
			System.out.println("Following the plan");
			return firstOfPlanned(currentLocation);
		}

		// record neighbours that we have/haven't visited
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
				return moveToChar(currentLocation,
						chooseRandom(unknown_around).location);
			} else {
				// TODO find the closest unknown
				System.out
						.println("Empty cell, visited all around, returning random");
				return moveToChar(currentLocation,
						chooseRandom(known_around).location);
			}
		}

		if (curCell.breezes()) {
			locatePit();
		}

		if (curCell.smells()) {
			locateWumpus();
		}

		if (plan.size() > 0) {
			return firstOfPlanned(currentLocation);
		}
		if (unknown_around.size() > 0) {
			System.out.println("No plan, returning a random unknown location");
			return moveToChar(currentLocation,
					chooseRandom(unknown_around).location);
		} else
			return moveToChar(currentLocation,
					chooseRandom(known_around).location);
	}

	/** @return true if the Cell has never been visited before */
	public boolean firstVisit() {
		for (int i = 0; i < visitedLocations.size() - 1; i++) {
			if (visitedLocations.get(i).equalsTo(currentLocation))
				return false;
		}
		return true;
	}

	/** Attempts to understand where Wumpus is. */
	private void locateWumpus() {
		ArrayList<Cell> diags = getDiagonals(currentLocation);
		ArrayList<Cell> smellyKnowns = new ArrayList<Cell>();
		ArrayList<Cell> notSmellyKnowns = new ArrayList<Cell>();
		ArrayList<Cell> unknowns = new ArrayList<Cell>();
		int count = 0;
		while (count < diags.size()) {
			Cell thisCell = diags.get(count);
			if (!(thisCell instanceof UnknownCell) && thisCell.smells()) {
				smellyKnowns.add(thisCell);
			} else if (thisCell instanceof UnknownCell) {
				unknowns.add(thisCell);
			} else {
				notSmellyKnowns.add(thisCell);
			}
			count++;
		}
		// now we have three lists
		if (smellyKnowns.size() == 2) {
			// current cell and two other form a triangle, can easily deduce
			// where the Wumpus is
			locateWith3Knowns(smellyKnowns.get(0), smellyKnowns.get(1));
			return;
		} else if (smellyKnowns.size() == 1) {
			// 2 possible locations, try to locate it
			locateWith2Knowns(ai_board.getCell(currentLocation),
					smellyKnowns.get(0));
			return;
		} else if (smellyKnowns.size() == 0) {
			// they are all unknown or not smelly
			// can't be all not smelly max 3, then at least 1 unknown, but can't
			// all be unknowns, too
			if (unknowns.size() == 2) {
				locateWith3Knowns(unknowns.get(0), unknowns.get(1));
				return;
			} else if (unknowns.size() == 3) {
				// 3 unknowns and 1 not smelly
				// go back
				plan.add(visitedLocations.get(visitedLocations.size() - 2));
			} else {
				// 1 unknown and 3 not smelly
				locateWith2Knowns(ai_board.getCell(currentLocation),
						unknowns.get(0));
				return;
			}

		}
	}

	/**
	 * Tries to locate the danger when there are 2 possible locations for it
	 * 
	 * @param c1
	 *            - one of the cells with the perceptions
	 * @param c2
	 *            - the other cell with the same perception
	 */
	private void locateWith2Knowns(Cell c1, Cell c2) {
		// finds the danger when perceptions are in 2 diagonal cells
		int x1 = c1.location.getX();
		int x2 = c2.location.getX();
		int y1 = c1.location.getY();
		int y2 = c2.location.getY();
		Location l1 = new Location(x1, y2);
		Location l2 = new Location(x2, y1);
		// TODO replace by something better than assuming both as danger
		if (ai_board.getCell(l1) instanceof UnknownCell
				&& !(ai_board.getCell(l2) instanceof UnknownCell)) {
			ai_board.getBoardObject()[l1.getX()][l1.getY()] = new PitCell(l1,
					ai_board);
		} else if (ai_board.getCell(l2) instanceof UnknownCell
				&& !(ai_board.getCell(l1) instanceof UnknownCell)) {
			ai_board.getBoardObject()[l2.getX()][l2.getY()] = new PitCell(l2,
					ai_board);
		} else {
			// cannot say much, count both as dangerous
			ai_board.getBoardObject()[l1.getX()][l1.getY()] = new DangerousCell(
					l1, ai_board);
			ai_board.getBoardObject()[l2.getX()][l2.getY()] = new DangerousCell(
					l2, ai_board);
		}
	}

	/**
	 * finds the danger when perceptions are in 3 near cells forming a triangle
	 * 
	 * @param c1
	 *            - one of the diagonals of the current cell
	 * @param c2
	 *            - the other diagonal
	 */
	private void locateWith3Knowns(Cell c1, Cell c2) {
		// the danger is right between them
		int wumpusY = (c1.location.getY() + c2.location.getY()) / 2;
		int wumpusX = (c1.location.getX() + c2.location.getX()) / 2;
		ai_board.getBoardObject()[wumpusX][wumpusY] = new DangerousCell(
				wumpusX, wumpusY, ai_board);
		for (int i = 0; i < unknown_around.size(); i++) {
			if (unknown_around.get(i).location.equalsTo(ai_board.getCell(
					wumpusX, wumpusY).location)) {
				unknown_around.remove(i);
				break;
			}
		}
		for (int i = 0; i < known_around.size(); i++) {
			if (known_around.get(i).location.equalsTo(ai_board.getCell(wumpusX,
					wumpusY).location)) {
				known_around.remove(i);
				break;
			}
		}
	}

	/**
	 * @return a list of Cells diagonal to the given location
	 * @param Location
	 *            whose neighbours we are interested in
	 */
	private ArrayList<Cell> getDiagonals(Location l) {
		ArrayList<Cell> result = new ArrayList<Cell>();
		Cell diag1 = ai_board.getCell(ai_board.getNorth(ai_board.getEast(l)));
		Cell diag2 = ai_board.getCell(ai_board.getNorth(ai_board.getWest(l)));
		Cell diag3 = ai_board.getCell(ai_board.getSouth(ai_board.getEast(l)));
		Cell diag4 = ai_board.getCell(ai_board.getSouth(ai_board.getWest(l)));
		result.add(diag1);
		result.add(diag2);
		result.add(diag3);
		result.add(diag4);
		return result;
	}

	/** Tries to locate the Pit around the current location */
	private void locatePit() {
		// check if we know a pit already
		// TODO think what happens if there is more than 1 pit
		for (int i = 0; i < known_around.size(); i++) {
			if (known_around.get(i) instanceof PitCell) {
				return;
			}
		}
		if(unknown_around.size()== 3 ){
			plan.add(visitedLocations.get(visitedLocations.size()-2));
			return;
		}
		if (unknown_around.size() == 2) {
			int count = 0;
			while (count < unknown_around.size()) {
				double danger = inspectUnknown(unknown_around.get(count));
				if (danger == 0) {
					// might as wll go there if it is safe
					plan.add(unknown_around.get(count).location);
					unknown_around.remove(count);
					count--;
				}
				count++;
			}

		}
		if (unknown_around.size() == 1) {
			// know exactly where it is
			Location pitLoc = unknown_around.get(0).location;
			ai_board.getBoardObject()[pitLoc.getX()][pitLoc.getY()] = new PitCell(
					pitLoc, ai_board);
			pitCells.add(pitLoc);
			unknown_around.remove(0);
		} else {
			return;
		}
	}

	/**
	 * Tries to evaluate how dangerous a given cell is
	 * 
	 * @param cell
	 *            - given cell
	 */
	public double inspectUnknown(Cell cell) {
		// 0 means safe, 1 - definitely Pit
		Location l = cell.location;
		Cell north = ai_board.getCell(ai_board.getNorth(l));
		ArrayList<Cell> known = new ArrayList<Cell>();
		if (!(north instanceof UnknownCell)) {
			known.add(north);
		}

		Cell south = ai_board.getCell(ai_board.getSouth(l));
		if (!(south instanceof UnknownCell)) {
			known.add(south);
		}

		Cell east = ai_board.getCell(ai_board.getEast(l));
		if (!(east instanceof UnknownCell)) {
			known.add(east);
		}

		Cell west = ai_board.getCell(ai_board.getWest(l));
		if (!(west instanceof UnknownCell)) {
			known.add(west);
		}
		// known can't be empty cause there is current player cell
		// which is breezy

		for (int i = 0; i < known.size(); i++) {
			if (!(known.get(i).breezes())) {
				// cell is safe
				return 0;
			}
		}
		// TODO finish this
		return 0.5;
	}

	/**
	 * @return true if the location has ever been visited
	 * @param Location
	 *            that interests us
	 */
	public boolean visited(Location l) {
		for (int i = 0; i < visitedLocations.size(); i++) {
			if (visitedLocations.get(i).equalsTo(l))
				return true;
		}
		return false;
	}

	/**
	 * Finds treasure near a glittering location by walking around
	 * 
	 * @param Location
	 *            l - where the glitter is
	 * @param neighbours
	 *            - possible locations of the treasure
	 */
	private void lookupTreasureNear(Location l, ArrayList<Cell> neighbours) {
		int count = 0;
		boolean flag = true;
		while (count < neighbours.size()) {
			// inspect all of the neighbour cells
			if (flag)
				plan.add(neighbours.get(count).location);
			else {
				if (count < neighbours.size() - 1) {
					plan.add(l);
				}
				count++;
			}
			flag = !flag;
		}
	}

	/**
	 * @return a list of neighbours who are either known or unknown, depending on
	 *         the parameter
	 * @param Location
	 *            whose neighbours we are interested in
	 * @param known
	 *            - whether we want the known or unknown ones
	 */
	private ArrayList<Cell> lookAround(Location l, boolean known) {

		ArrayList<Cell> result = new ArrayList<Cell>();

		Cell north = ai_board.getCell(ai_board.getNorth(l));
		if ((north instanceof UnknownCell) != known) {
			if (!(north instanceof PitCell))
				result.add(north);
		}

		Cell south = ai_board.getCell(ai_board.getSouth(l));
		if ((south instanceof UnknownCell) != known) {
			if (!(south instanceof PitCell))
				result.add(south);
		}

		Cell east = ai_board.getCell(ai_board.getEast(l));
		if ((east instanceof UnknownCell) != known) {
			if (!(east instanceof PitCell))
				result.add(east);
		}

		Cell west = ai_board.getCell(ai_board.getWest(l));
		if ((west instanceof UnknownCell) != known) {
			if (!(west instanceof PitCell))
				result.add(west);
		}

		return result;
	}

	/**
	 * Backtracks to the exit via exactly the same route that we came from
	 * there. Adds the route to the plan
	 */
	private void pathBackToExit() {
		int count = visitedLocations.size() - 2;
		while (count > 0) {
			plan.add(visitedLocations.get(count));
			if (ai_board.getCell(visitedLocations.get(count)) instanceof ExitCell)
				count = 0;
			count--;
		}
	}

	/**
	 * @return true if Locations share ad edge
	 * @param l1
	 * @param l2
	 */
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

	/**
	 * @return a command to the game that represents the first step of the plan
	 * @param l
	 *            - where we are stepping from
	 */
	private char firstOfPlanned(Location l) {
		for (int i = 0; i < plan.size(); i++) {
			System.out.println("-" + plan.get(i));
		}
		char move = moveToChar(l, plan.get(0));
		plan.remove(0);
		return move;
	}

	/**
	 * @return a cell chosen randomly from a list Pits and dangerous cells are
	 *         being filtered out if there is nothing else on the list, go back
	 */
	private Cell chooseRandom(ArrayList<Cell> list) {
		if (list.size() == 0)
			return ai_board.getCell(visitedLocations.get(visitedLocations
					.size() - 2));
		Random rn = new Random();
		int choice = rn.nextInt(list.size());
		if (list.get(choice) instanceof PitCell
				|| list.get(choice) instanceof DangerousCell) {
			list.remove(choice);
			return chooseRandom(list);
		}
		return list.get(choice);
	}

	/**
	 * converts a move from one location to another to a gam command
	 * 
	 * @return a game command
	 * @param l1
	 *            - current location
	 * @param l2
	 *            - next location
	 */
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
