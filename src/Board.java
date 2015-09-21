import java.util.Random;

public class Board {
	int size;
	Cell[][] boardObject;
	Game game;

	public Board(int size, Game g, int complexity) {
		this.size = size;
		game = g;
		initialize(complexity);

		
	}

	public void initialize(int complexity) {
		//create empty cells
		boardObject = new Cell[size][size];
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++)
				boardObject[i][j] = new Cell(i, j);
		}

		placeAdventurer();
		placeWumpus();
		placeExit();
		placeTreasure();
		placePits(size * complexity / 5);
		placeBats(size * complexity / 10);
	}
	
	
	private void placeBats(int amount){
		Random rn = new Random();
		for (int i = 0; i < amount; i++){
			int batX = 0;
			int batY = 0;
			do{
				batX = rn.nextInt(size);
				batY = rn.nextInt(size);
			} while (boardObject[batX][batY].isAdventurer || boardObject[batX][batY].isPit);
			boardObject[batX][batY].isBat = true;
		}
	}
	
	private void placePits(int amount){
		Random rn = new Random();
		for (int i = 0; i < amount; i++){
			int pitX = 0;
			int pitY = 0;
			do{
				pitX = rn.nextInt(size);
				pitY = rn.nextInt(size);
			} while (boardObject[pitX][pitY].isAdventurer || boardObject[pitX][pitY].isWumpus);
			boardObject[pitX][pitY].isPit = true;
			
			boardObject[pitX][getNorth(pitY)].breezes();
			boardObject[pitX][getSouth(pitY)].breezes();
			boardObject[getEast(pitX)][pitY].breezes();
			boardObject[getWest(pitX)][pitY].breezes();
		}
	}
	
	public void print(){
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				boardObject[j][i].print();
			}
			System.out.println();
		}
	}
	
	private void placeAdventurer(){
		Random rn = new Random();
		int playerX = rn.nextInt(size);
		int playerY = rn.nextInt(size);
		boardObject[playerX][playerY].isAdventurer = true;
		game.player= new Adventurer(playerX, playerY);
	}
	
	private void placeWumpus(){
		Random rn = new Random();
		int wumpusX = 0;
		int wumpusY = 0;
		do {
			wumpusX = rn.nextInt(size);
			wumpusY = rn.nextInt(size);
		} while (boardObject[wumpusX][wumpusY].isAdventurer);

		boardObject[wumpusX][wumpusY].isWumpus = true;
		
		//place stink
		boardObject[wumpusX][getNorth(wumpusY)].smells();
		boardObject[wumpusX][getSouth(wumpusY)].smells();
		boardObject[getEast(wumpusX)][wumpusY].smells();
		boardObject[getWest(wumpusX)][wumpusY].smells();
	}
	
	private void placeExit(){
		Random rn = new Random();
				int exitX = 0;
				int exitY = 0;
				do {
					exitX = rn.nextInt(size);
					exitY = rn.nextInt(size);
				} while (boardObject[exitX][exitY].isAdventurer);
				boardObject[exitX][exitY].isExit = true;
	}
	
	private void placeTreasure(){
		//place treasure
		Random rn = new Random();
		int treasureX = 0;
		int treasureY = 0;
		do {
			treasureX = rn.nextInt(size);
			treasureY = rn.nextInt(size);
		} while (boardObject[treasureX][treasureY].isAdventurer);
		boardObject[treasureX][treasureY].isTreasure = true;

		//place glitter
		boardObject[treasureX][getNorth(treasureY)].glitters();
		boardObject[treasureX][getSouth(treasureY)].glitters();
		boardObject[getEast(treasureX)][treasureY].glitters();
		boardObject[getWest(treasureX)][treasureY].glitters();
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
