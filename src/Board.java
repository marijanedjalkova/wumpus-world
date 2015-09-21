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
				boardObject[i][j] = new EmptyCell(i, j);
		}

		placeAdventurer();
		placeWumpus();
		placeBat();
		
		placeExit();
		placeTreasure();
		placePits(size * complexity / 5);
	}
	
	private void placeAdventurer(){
		Random rn = new Random();
		int playerX = rn.nextInt(size);
		int playerY = rn.nextInt(size);
		game.player.setLocation(new Location(playerX, playerY));
	}
	
	private void placeWumpus(){
		Location l = findPlayerFree();
		game.wumpus.setLocation(l);
	}
	
	
	private void placeBat(){
		Location l = findPlayerFree();
		game.superBat.setLocation(l);
	}
	
	private Location findPlayerFree(){
		Random rn = new Random();
		int x = 0;
		int y = 0;
		Location l;
		do {
			x = rn.nextInt(size);
			y = rn.nextInt(size);
			l = new Location(x, y);
		} while (game.player.getLocation() == l);
		return l;
	}
	
	private Location findFree(){
		Random rn = new Random();
		int x = 0;
		int y = 0;
		Location l;
		do {
			x = rn.nextInt(size);
			y = rn.nextInt(size);
			l = new Location(x, y);
		} while (game.player.getLocation() == l
				|| game.wumpus.getLocation() == l
				|| game.superBat.getLocation() ==l);
		return l;
	}
	
	public void print(){
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				boardObject[j][i].print();
			}
			System.out.println();
		}
	}
	
	private void placePits(int amount){
		for (int i = 0; i < amount; i++){
			Location l = findFree();
			boardObject[l.getX()][l.getY()] = new PitCell(l);
		}
	}
	
	private void placeExit(){
		Location l = findFree();
		boardObject[l.getX()][l.getY()] = new ExitCell(l);
	}
	
	private void placeTreasure(){
		Location l = findFree();
		boardObject[l.getX()][l.getY()] = new TreasureCell(l);
	}

	private Location getSouth(Location l){
		if (l.getY() < size - 1)
			return new Location(l.getX(), l.getY() + 1);
		return new Location(l.getX(), 0);
	}
	
	private Location getNorth(Location l){
		if (l.getY() > 0)
			return new Location(l.getX(), l.getY() - 1);
		return new Location(l.getX(), size - 1);
	}
	
	private Location getEast(Location l){
		if (l.getX() < size - 1)
			return new Location(l.getX() + 1, l.getY());
		return new Location(0, l.getY());		
	}
	
	private Location getWest(Location l){
		if (l.getX() > 0)
			return new Location (l.getX() - 1, l.getY());
		return new Location(size - 1, l.getY());
	}
	
	public void move(MovingObject object, char d) {
		Location curLoc = object.getLocation();
		switch (d) {
		case 'N':
			object.setLocation(getNorth(curLoc));
			break;
		case 'S':
			object.setLocation(getSouth(curLoc));
			break;
		case 'W':
			object.setLocation(getWest(curLoc));
			break;
		case 'E':
			object.setLocation(getEast(curLoc));
			break;
		}
	}
	
}
