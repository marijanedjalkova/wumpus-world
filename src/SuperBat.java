import java.util.Random;


public class SuperBat extends MovingObject {

	public SuperBat(int x, int y) {
		super(x, y);
		
	}
	
	public SuperBat(){
		super();
	}
	
	public void move(Adventurer player, Board b){
		Random rn = new Random();
		int x, y;
		Location l;
		do{
		x = rn.nextInt(b.getSize());
		y = rn.nextInt(b.getSize());
		l = new Location(x, y);
		} while (!(b.getCell(l) instanceof EmptyCell));
		player.setLocation(l);
	}

}
