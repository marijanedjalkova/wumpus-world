import java.util.Random;

public class Board {
	int size;
	Cell[][] boardObject;

	public Board(int size) {
		this.size = size;
		initialize();
	}

	private void initialize() {
		//create empty cells
		boardObject = new Cell[size][size];
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				boardObject[i][j] = new Cell(i, j);
			}
		}
		
		//place an adventurer
		Random rn = new Random();
		int playerX = rn.nextInt(size);
		int playerY = rn.nextInt(size);

		Adventurer player = new Adventurer(playerX, playerY);
		boardObject[playerX][playerY].setAdventurer(player);
		
		//place the Wumpus
		int wumpusX = 0;
		int wumpusY = 0;
		do {
			wumpusX = rn.nextInt(size);
			wumpusY = rn.nextInt(size);
		} while (wumpusX == playerX && wumpusY == playerY);
		Wumpus wumpus = new Wumpus(wumpusX, wumpusY);
		boardObject[wumpusX][wumpusY].setWumpus(wumpus);
		
		//place stink
		

	}

	private int getNorth(int y){
		if (y < size - 1)
			return y + 1;
		return 0;
	}
	
	private int getSouth(int y){
		if (y > 0)
			return y - 1;
		return size - 1;
	}
	
	private int getEast(int x){
		if (x < size - 1)
			return x + 1;
		return 0;		
	}
	
	private int getWest(int x){
		if (x > 0)
			return x - 1;
		return size - 1;
	}
	
	public void move(MovingObject object, char d) {
		switch (d) {
		case 'N':
			;
			break;
		case 'S':
			;
			break;
		case 'W':
			;
			break;
		case 'E':
			;
			break;
		}
	}
}
