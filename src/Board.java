import java.util.ArrayList;
import java.util.Random;

public class Board {
	private int size;
	private Cell[][] boardObject;
	Game game;
	public TreasureCell treasure;
	public ExitCell exit;
	public ArrayList<PitCell> pitCells;

	public Board(int size, Game g, int complexity) {
		this.size = size;
		pitCells = new ArrayList<PitCell>();
		game = g;
		initialize(complexity);	
	}

	public void initialize(int complexity) {
		//create empty cells
		boardObject = new Cell[size][size];
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++)
				boardObject[i][j] = new EmptyCell(i, j, this);
		}
		
		//place landscape
		placeExit();
		placeTreasure();
		placePits(size * complexity / 5);

		//place characters
		placeMoving(game.player);
		placeMoving(game.wumpus);
		placeMoving(game.superBat);
	
	}
	
	private void placeMoving(MovingObject character){
		Location l;
		do {
			l = findEmpty();
		} while (game.player.getLocation().equalsTo(l) || game.wumpus.getLocation().equalsTo(l)
				|| game.superBat.getLocation().equalsTo(l));
		character.setLocation(l);
	}
	
	private Location findEmpty(){
		Random rn = new Random();
		int x = 0;
		int y = 0;
		do {
			x = rn.nextInt(size);
			y = rn.nextInt(size);
		} while (!(boardObject[x][y] instanceof EmptyCell));
		return new Location(x, y);
	}
	
	public void print(){
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				boardObject[j][i].print();
			}
			System.out.println();
			for (int k = 0; k < 110; k++)
				System.out.print("-");
			System.out.println();
		}
	}
	
	private void placePits(int amount){
		for (int i = 0; i < amount; i++){
			Location l = findEmpty();
			PitCell newPit = new PitCell(l, this);
			pitCells.add(newPit);
			boardObject[l.getX()][l.getY()] = newPit;
		}
	}
	
	private void placeExit(){
		Location l = findEmpty();
		ExitCell newExit = new ExitCell(l, this);
		exit = newExit;
		boardObject[l.getX()][l.getY()] = exit;
	}
	
	private void placeTreasure(){
		Location l = findEmpty();
		TreasureCell newTreasure = new TreasureCell(l, this);
		treasure = newTreasure;
		boardObject[l.getX()][l.getY()] = treasure;
	}
	
	public Cell getCell(Location l){
		return boardObject[l.getX()][l.getY()];
	}
	
	public Cell getCell(int x, int y){
		return boardObject[x][y];
	}

	public Location getSouth(Location l){
		if (l.getY() < size - 1)
			return new Location(l.getX(), l.getY() + 1);
		return new Location(l.getX(), 0);
	}
	
	public Location getNorth(Location l){
		if (l.getY() > 0)
			return new Location(l.getX(), l.getY() - 1);
		return new Location(l.getX(), size - 1);
	}
	
	public Location getEast(Location l){
		if (l.getX() < size - 1)
			return new Location(l.getX() + 1, l.getY());
		return new Location(0, l.getY());		
	}
	
	public Location getWest(Location l){
		if (l.getX() > 0)
			return new Location (l.getX() - 1, l.getY());
		return new Location(size - 1, l.getY());
	}
	
	public void move(MovingObject object, char d) {
		Location curLoc = object.getLocation();
		System.out.println(curLoc);
		switch (d) {
		case 'n':
			if (object instanceof Adventurer)
				game.player.setLocation(getNorth(curLoc));
			if (object instanceof Wumpus)
				game.wumpus.setLocation(getNorth(curLoc));
			if (object instanceof SuperBat)
				game.superBat.setLocation(getNorth(curLoc));
			break;
		case 's':
			if (object instanceof Adventurer)
				game.player.setLocation(getSouth(curLoc));
			if (object instanceof Wumpus)
				game.wumpus.setLocation(getSouth(curLoc));
			if (object instanceof SuperBat)
				game.superBat.setLocation(getSouth(curLoc));
			break;
		case 'w':
			if (object instanceof Adventurer)
				game.player.setLocation(getWest(curLoc));
			if (object instanceof Wumpus)
				game.wumpus.setLocation(getWest(curLoc));
			if (object instanceof SuperBat)
				game.superBat.setLocation(getWest(curLoc));
			break;
		case 'e':
			if (object instanceof Adventurer)
				game.player.setLocation(getEast(curLoc));
			if (object instanceof Wumpus)
				game.wumpus.setLocation(getEast(curLoc));
			if (object instanceof SuperBat)
				game.superBat.setLocation(getEast(curLoc));
			break;
		case 'u':
			if (object instanceof Adventurer){
				game.player.shoot(getNorth(game.player.getLocation()));
			}
			break;
		case 'd':
			if (object instanceof Adventurer){
				game.player.shoot(getSouth(game.player.getLocation()));
			}
			break;
		case 'l':
			if (object instanceof Adventurer){
				game.player.shoot(getWest(game.player.getLocation()));
			}
			break;
		case 'r':
			if (object instanceof Adventurer){
				game.player.shoot(getEast(game.player.getLocation()));
			}
			break;
		}
	}
	
	public int getSize(){
		return size;
	}
	
}
