/**
 * Represents all the moving objects in the game. By default, there are three of
 * them - Adventurer, Wumpus and the SuperBat. They can move on the board.
 */
public abstract class MovingObject {
	/** Where on the board the object currently is */
	protected Location location;
	/** Reference to the game */
	protected Game game;

	/**
	 * Constructor.
	 * 
	 * @param x
	 *            - x coordinate of the location
	 * @param y
	 *            - y coordinate of the location
	 * @param g
	 *            - reference to the game itself
	 */
	public MovingObject(int x, int y, Game g) {
		location = new Location(x, y);
		game = g;
	}

	/**
	 * Constructor.
	 * 
	 * @param g
	 *            - reference to the game itself
	 */
	public MovingObject(Game g) {
		this(0, 0, g);
	}

	/**
	 * Changes location to
	 * 
	 * @param l
	 *            - new Location
	 */
	public void setLocation(Location l) {
		location = l;
	}

	/** @return current location */
	public Location getLocation() {
		return location;
	}

}
