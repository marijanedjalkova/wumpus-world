/**
 * Represents all the cells that can possibly be on the game board. Has a
 * location and can feel percepts.
 */
public abstract class Cell {
	/** Where on the board it is */
	protected Location location;
	/** Reference to the board */
	protected Board board;

	/**
	 * Constructor.
	 * 
	 * @param x
	 *            - x coordinate of the location
	 * @param y
	 *            - y coordinate of the location
	 * @param b
	 *            - reference to the board
	 */
	public Cell(int x, int y, Board b) {
		board = b;
		location = new Location(x, y);
	}

	/**
	 * Constructor.
	 * 
	 * @param l
	 *            - the location
	 * @param b
	 *            - reference to the board
	 */
	public Cell(Location l, Board b) {
		board = b;
		location = l;
	}

	/** @return location of the Cell on the board */
	public Location getLocation() {
		return location;
	}

	/** prints details in a proper representative format */
	public void print(AIPlayer player, boolean debug) {
		if (this instanceof UnknownCell) {
			System.out.print("|  ??? ");
			return;
		}
		System.out.print("|");

		if (this instanceof PitCell)
			System.out.print("P");
		else if (board.game.character.getLocation().equalsTo(location))
			System.out.print("A");

		else if (board.game.wumpus.getLocation().equalsTo(location)) {
			if (player.currentLocation.equalsTo(location) || debug)
				System.out.print("W");
			else
				System.out.print(" ");
		} else if (board.game.superBat.getLocation().equalsTo(location)) {
			if (player.visited(location) || debug)
				System.out.print("B");
			else
				System.out.print(" ");
		} else
			System.out.print(" ");

		if (this instanceof TreasureCell)
			System.out.print("T");
		else
			System.out.print(" ");
		if (this instanceof ExitCell)
			System.out.print("E");
		else
			System.out.print(" ");
		if (smells()) {
			if (player.visited(location) || debug)
				System.out.print("@");
			else
				System.out.print(" ");
		} else
			System.out.print(" ");
		if (breezes())
			System.out.print("~");
		else
			System.out.print(" ");
		if (glitters())
			System.out.print("*");
		else
			System.out.print(" ");
	}

	/** @return true if there is wumpus is alive and around */
	public boolean smells() {

		Location wumpusLocation = board.game.wumpus.getLocation();
		if (board.game.wumpus.isAlive()) {
			if (board.getNorth(location).equalsTo(wumpusLocation))
				return true;
			if (board.getSouth(location).equalsTo(wumpusLocation))
				return true;
			if (board.getEast(location).equalsTo(wumpusLocation))
				return true;
			if (board.getWest(location).equalsTo(wumpusLocation))
				return true;
		}
		return false;
	}

	/** @return true if there is treasure around */
	public boolean glitters() {

		if (board.getCell(board.getNorth(location)) instanceof TreasureCell)
			return true;
		if (board.getCell(board.getSouth(location)) instanceof TreasureCell)
			return true;
		if (board.getCell(board.getEast(location)) instanceof TreasureCell)
			return true;
		if (board.getCell(board.getWest(location)) instanceof TreasureCell)
			return true;
		return false;
	}

	/** @return true if there is pit around */
	public boolean breezes() {

		if (board.getCell(board.getNorth(location)) instanceof PitCell)
			return true;
		if (board.getCell(board.getSouth(location)) instanceof PitCell)
			return true;
		if (board.getCell(board.getEast(location)) instanceof PitCell)
			return true;
		if (board.getCell(board.getWest(location)) instanceof PitCell)
			return true;
		return false;
	}

	/** prints information for the human eye */
	public void printPercepts() {
		if (breezes())
			System.out.println("Cell brezzes!");
		if (smells())
			System.out.println("Cell smells!");
		if (glitters())
			System.out.println("Cell glitters!");

	}

}
