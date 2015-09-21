
public abstract class Cell {
	protected Location location;
	protected Board board;

	
	public Cell(int x, int y, Board b){
		board = b;
		location = new Location(x, y);
	}
	
	public Cell(Location l, Board b){
		board = b;
		location = l;
	}
	
	public Location getLocation(){
		return location;
	}
	
	public void print(){
		System.out.print("<");
		//TODO
		System.out.print(">");
	}
	
	public boolean smells(){
		//check if there is wumpus around 
		
	}
	
	public boolean glitters(){
		//check if there is treasure around 
	}
	
	public boolean breezes(){
		//check if there is pit around
	}
	
	
}
