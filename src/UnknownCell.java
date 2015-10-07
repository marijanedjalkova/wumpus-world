
public class UnknownCell extends Cell {
	public boolean dangerous;

	public UnknownCell(int x, int y, Board b) {
		super(x, y, b);
		dangerous = false;
	}
	
	public UnknownCell(Location l, Board b) {
		super(l, b);
		dangerous = false;
	}
	
	public boolean smells(){
		return false;
	}
	
	public boolean breezes(){
		return false;
	}
	
	public boolean glitters(){
		return false;
	}


}
