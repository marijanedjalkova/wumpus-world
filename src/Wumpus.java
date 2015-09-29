import java.util.Random;


public class Wumpus extends MovingObject{

	private boolean alive;
	
	
	public Wumpus(int x, int y, Game g) {
		super(x, y, g);
		alive = true;
	}
	
	public Wumpus(Game g) {
		super(g);
		alive = true;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void die(){
		System.out.println("Wumpus is dead!");
		alive = false;
	}
	
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
