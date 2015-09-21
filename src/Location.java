
public class Location implements Comparable<Location>{
	private int x;
	private int y;
	
	public Location(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

	@Override
	public int compareTo(Location l) {
		if (x == l.getX() && y == l.getY())
				return 1;
		return 0;
	}
}
