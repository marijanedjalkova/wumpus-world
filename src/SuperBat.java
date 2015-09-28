import java.util.Random;


public class SuperBat extends MovingObject {

	public SuperBat(int x, int y, Game g) {
		super(x, y, g);
		
	}
	
	public SuperBat(Game g){
		super(g);
	}
	
	public void move(Adventurer player, Board b){
		Random rn = new Random();
		int size = b.getSize();
		int x, y;
		do{
		x = rn.nextInt(size);
		y = rn.nextInt(size);
		} while (!(b.getCell(x, y) instanceof EmptyCell));
		player.setLocation(new Location(x, y));
	}

}
