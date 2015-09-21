
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
		Location wumpusLocation = board.game.wumpus.getLocation();
		if (board.getNorth(location) == wumpusLocation) return true;
		if (board.getSouth(location) == wumpusLocation) return true;
		if (board.getEast(location) == wumpusLocation) return true;
		if (board.getWest(location) == wumpusLocation) return true;
		return false;		
	}
	
	public boolean glitters(){
		//check if there is treasure around 
		if (board.getCell(board.getNorth(location)) instanceof TreasureCell) return true;
		if (board.getCell(board.getSouth(location)) instanceof TreasureCell) return true;
		if (board.getCell(board.getEast(location)) instanceof TreasureCell) return true;
		if (board.getCell(board.getWest(location)) instanceof TreasureCell) return true;
		return false;
	}
	
	public boolean breezes(){
		//check if there is pit around
		if (board.getCell(board.getNorth(location)) instanceof PitCell) return true;
		if (board.getCell(board.getSouth(location)) instanceof PitCell) return true;
		if (board.getCell(board.getEast(location)) instanceof PitCell) return true;
		if (board.getCell(board.getWest(location)) instanceof PitCell) return true;
		return false;
	}
	
	
}
