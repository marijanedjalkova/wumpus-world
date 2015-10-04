
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
		System.out.print("|");
		
		
		if (this instanceof PitCell)
			System.out.print("P");

		else if (board.game.character.getLocation().equalsTo(location))
			System.out.print("A");
		
		else if (board.game.wumpus.getLocation().equalsTo(location))
			System.out.print("W");
		
		else if (board.game.superBat.getLocation().equalsTo(location))
			System.out.print("B");
		else 
			System.out.print(" ");
		
		if (this instanceof TreasureCell)
			System.out.print("T");
		else
			System.out.print(" ");
		if (this instanceof ExitCell)
			System.out.print("E");
		else
			System.out.print(" ");
		if (smells())
			System.out.print("@");
		else
			System.out.print(" ");
		if (breezes())
			System.out.print("~");
		else
			System.out.print(" ");
		if (glitters())
			System.out.print("*");
		else
			System.out.print(" ");
	}
	
	public boolean smells(){
		//check if there is wumpus is alive and around
		Location wumpusLocation = board.game.wumpus.getLocation();
		if (board.game.wumpus.isAlive()){
		if (board.getNorth(location).equalsTo(wumpusLocation)) return true;
		if (board.getSouth(location).equalsTo(wumpusLocation)) return true;
		if (board.getEast(location).equalsTo(wumpusLocation)) return true;
		if (board.getWest(location).equalsTo(wumpusLocation)) return true;
		}
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
