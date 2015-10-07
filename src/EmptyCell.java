/**
 * Represents a cell with no Treasure, Pits or Exits. Any moving object can be
 * on an EmptyCell, and it won't stop being empty because of that. Empty Cells
 * can have percepts.
 */
public class EmptyCell extends Cell {

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
	public EmptyCell(int x, int y, Board b) {
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
	public EmptyCell(Location l, Board b) {
		super(l, b);
	}

}
