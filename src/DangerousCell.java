/**Class used by the AI to mark a cell where a pit or wumpis might be.*/
public class DangerousCell extends Cell {

	/**Constructor.
	 * @param x - x coordinate of the location
	 * @param y - y coordinate of the location
	 * @param b - reference to the board */
	public DangerousCell(int x, int y, Board b) {
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
	public DangerousCell(Location l, Board b) {
		super(l, b);
	}
}
