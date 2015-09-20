
public class Board {
	int size;
	public Board(int size){
		this.size = size;
		initialize();
	}
	Adventurer player;
	Cell[][] boardObject = new Cell[size][size];
	
	private void initialize(){
		
	}
	
	public void move(MovingObject object, char d){
		switch (d){
		case 'N': ; break;
		case 'S': ; break;
		case 'W': ; break;
		case 'E': ; break;
		}
	}
}
