
public abstract class Cell {
	private Location location;

	
	public Cell(int x, int y){
		location = new Location(x, y);
	}
	
	public Cell(Location l){
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
