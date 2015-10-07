import java.util.Random;

/**
 * Class represents a moving Super Bat. If encountered, moves the Adventurer
 * onto a random Cell.
 */
public class SuperBat extends MovingObject {

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
	public SuperBat(int x, int y, Game g) {
		super(x, y, g);

	}

	/**
	 * Constructor.
	 * 
	 * @param g
	 *            - reference to the game itself
	 */
	public SuperBat(Game g) {
		super(g);
	}

	/**
	 * Moves the player on the board
	 * 
	 * @param player
	 *            - who to move
	 * @param b
	 *            - board of the action
	 */
	public void move(Adventurer player, Board b) {
		System.out
				.println("You were picked up by a superbat and moved to a random location!");
		Random rn = new Random();
		int size = b.getSize();
		int x, y;
		do {
			//choose a random location until we end on an empty one
			x = rn.nextInt(size);
			y = rn.nextInt(size);
		} while (!(b.getCell(x, y) instanceof EmptyCell));
		player.setLocation(new Location(x, y));
	}

}
