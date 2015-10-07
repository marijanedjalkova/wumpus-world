/** Class representing Cell with the static Treasure. */
public class TreasureCell extends Cell {

	/**Constructor.
	 * @param x - x coordinate of the location
	 * @param y - y coordinate of the location
	 * @param b - reference to the board */
	public TreasureCell(int x, int y, Board b) {
		super(x, y, b);
	}
	
	/**Constructor.
	 * @param l - location of the cell
	 * @param b - reference to the board */
	public TreasureCell(Location l, Board b) {
		super(l, b);
	}
}
