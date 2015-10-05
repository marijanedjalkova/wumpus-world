
public class UnknownCell extends Cell {

	public UnknownCell(int x, int y, Board b) {
		super(x, y, b);
	}
	
	public UnknownCell(Location l, Board b) {
		super(l, b);
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
