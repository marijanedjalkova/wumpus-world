import java.util.Random;

/**Represents a deadly monster that moves around if disturbed.
 * Wumpus is disturbed by shooting arrows near it.*/
public class Wumpus extends MovingObject{
	/**Does not smell or kill if it is not alive*/
	private boolean alive;
	
	/**Constructor.
	 * @param x - x coordinate of the location
	 * @param y - y coordinate of the location
	 * @param g - reference to the game itself*/
	public Wumpus(int x, int y, Game g) {
		super(x, y, g);
		alive = true;
	}
	
	/**Constructor.
	 * @param g - reference to the game itself*/
	public Wumpus(Game g) {
		super(g);
		alive = true;
	}
	
	/**@return true if the monster is still deadly*/
	public boolean isAlive(){
		return alive;
	}
	
	/** Makes sure wumpus is not deadly any more*/
	public void die(){
		System.out.println("Wumpus is dead!");
		alive = false;
	}
	
	/** Moves onto a random adjacent cell*/
	public void moveRandomly(){
		if (alive){
		Random rn = new Random();
		int dir = rn.nextInt(3);//3 for number of directions 4
		Location l;
		switch (dir){
		 case 0:
			l = game.getBoard().getNorth(location);
			break;
		case 1:
			l = game.getBoard().getSouth(location);
			break;
		case 2:
			l = game.getBoard().getEast(location);
			break;
		case 3:
			l = game.getBoard().getWest(location);
			break;
		default:
			//this is redundant but required by the compiler
			l = game.getBoard().getNorth(location);
		}
		location = l;
		}
	}
	
}
