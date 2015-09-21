
public class Location{
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
	
	public boolean equalsTo(Location l){
		return x == l.getX() && y == l.getY();
	}
	
	@Override
	public String toString(){
		return "(" + x + "," + y + ")";
	}
}