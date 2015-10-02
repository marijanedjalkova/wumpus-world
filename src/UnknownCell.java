
public class UnknownCell extends Cell {
	boolean smells, glitters, breezes;
	

	public UnknownCell(int x, int y, Board b) {
		super(x, y, b);
	}
	
	public UnknownCell(Location l, Board b) {
		super(l, b);
	}

}
