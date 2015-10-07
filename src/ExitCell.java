/** Represents a cell that the Adventurer must come to 
 * after having collected gold to win. */
public class ExitCell extends Cell {

	/**Constructor.
	 * @param x - x coordinate of the location
	 * @param y - y coordinate of the location
	 * @param b - reference to the board */
	public ExitCell(int x, int y, Board b) {
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
	public ExitCell(Location l, Board b) {
		super(l, b);
	}
	

}
