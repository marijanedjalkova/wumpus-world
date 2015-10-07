/** Class representing coordinates of the game board */
public class Location {
	/** x coordinate */
	private int x;
	/** y coordinate */
	private int y;

	/**
	 * Constructor.
	 * 
	 * @param x
	 *            - horizontal coordinate
	 * @param y
	 *            - vertical coordinate
	 */
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/** @return vertical coordinate */
	public int getX() {
		return x;
	}

	/** @return horizontal coordinate */
	public int getY() {
		return y;
	}

	/**
	 * @return true if
	 * @param l
	 *            points at the same place on board as this Location
	 */
	public boolean equalsTo(Location l) {
		return x == l.getX() && y == l.getY();
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
