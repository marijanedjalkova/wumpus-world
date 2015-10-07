/**
 * Represents a moving object of the game controlled by the player. Adventurer
 * can walk around, collect gold by stepping on the cell with the treasure,
 * shoot the Wumpus.
 */
public class Adventurer extends MovingObject {
	/** stores whether the gold has been collected */
	private boolean collected;

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
	public Adventurer(int x, int y, Game g) {
		super(x, y, g);
		collected = false;
		game = g;
	}

	/**
	 * Constructor.
	 * 
	 * @param g
	 *            - reference to the game itself
	 */
	public Adventurer(Game g) {
		super(g);
		collected = false;
		game = g;
	}

	/** Records that the gold is now collected */
	public void collectTreasure() {
		collected = true;
	}

	/** @return true if the gold has been collected */
	public boolean collectedTreasure() {
		return collected;
	}

	/**
	 * Shoots into the specified location. Wumpus either dies or moves.
	 * 
	 * @param l
	 *            - direction of shooting
	 */
	public void shoot(Location l) {
		if (game.wumpus.getLocation().equalsTo(l)) {
			game.wumpus.die();
		} else {
			game.wumpus.moveRandomly();
		}
	}

}
