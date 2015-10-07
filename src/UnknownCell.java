/**Class used by the AI to mark a cell it knows nothing about.*/
public class UnknownCell extends Cell {

	/**Constructor.
	 * @param x - x coordinate of the location
	 * @param y - y coordinate of the location
	 * @param b - reference to the board */
	public UnknownCell(int x, int y, Board b) {
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
	public UnknownCell(Location l, Board b) {
		super(l, b);
	}
	
	/**@return true if Wumpus is in one of the adjacent cells.
	 * Always returns false because if the cell is unknown
	 * then it means that it was not opened by the Adventurer yet. */
	public boolean smells(){
		return false;
	}
	
	/**@return true if Pit is in one of the adjacent cells.
	 * Always returns false because if the cell is unknown
	 * then it means that it was not opened by the Adventurer yet. */
	public boolean breezes(){
		return false;
	}
	
	/**@return true if Treasure is in one of the adjacent cells.
	 * Always returns false because if the cell is unknown
	 * then it means that it was not opened by the Adventurer yet. */
	public boolean glitters(){
		return false;
	}


}
