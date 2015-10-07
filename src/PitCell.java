/** Represents a deadly for the Adventurer cell. */
public class PitCell extends Cell {

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
	public PitCell(int x, int y, Board b) {
		super(x, y, b);
	}

	/**
	 * Constructor.
	 * 
	 * @param l
	 *            - location of the cell
	 * @param b
	 *            - reference to the board
	 */
	public PitCell(Location l, Board b) {
		super(l, b);
	}
}
