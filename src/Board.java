import java.util.ArrayList;
import java.util.Random;

public class Board {
	private int size;
	private Cell[][] boardObject;
	Game game;
	public TreasureCell treasure;
	public ExitCell exit;
	public ArrayList<PitCell> pitCells;

	public Board(int size, Game g) {
		this.size = size;
		game = g;
	}

	public void initialize(int complexity) {
		pitCells = new ArrayList<PitCell>();
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
		
		checkPits();

		//place characters
		placeMoving(game.character);
		placeMoving(game.wumpus);
		placeMoving(game.superBat);
	
	}
	
	private void checkPits(){
		Random rn = new Random();
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				Cell curCell = boardObject[i][j];
				//check 4 surrounding squares
				Cell northCell = getCell(getNorth(curCell.location));
				Cell southCell = getCell(getSouth(curCell.location));
				Cell eastCell = getCell(getEast(curCell.location));
				Cell westCell = getCell(getWest(curCell.location));
				//if all four of them are taken, replace a random one by an empty square
				if (northCell instanceof PitCell && southCell instanceof PitCell &&
						eastCell instanceof PitCell && westCell instanceof PitCell){
					int direction = rn.nextInt(3);
					Location l;
					switch (direction){
					case 0:
						l = getNorth(curCell.location);
						break;
					case 1:
						l = getSouth(curCell.location);
						break;
					case 2:
						l = getEast(curCell.location);
						break;
					case 3:
						l = getWest(curCell.location);
						break;
					default:
						l = getNorth(curCell.location);
						break;
					}
					boardObject[l.getX()][l.getY()] = new EmptyCell(l, this);
					for (int k = 0; k < pitCells.size(); k++){
						PitCell cur = pitCells.get(k);
						if (cur.location.equalsTo(l)){
							pitCells.remove(k);
							break;
						}
					}
				}
			}
		}
	}
	
	private void placeMoving(MovingObject character){
		Location l;
		do {
			l = findEmpty();
		} while (game.character.getLocation().equalsTo(l) || game.wumpus.getLocation().equalsTo(l)
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
	
	public void print(AIPlayer player, boolean true_game){
		for (int k = 0; k < 7* size; k++)
			System.out.print("-");
		System.out.println();
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				boardObject[j][i].print(player, true_game);
			}
			System.out.println("|");
			for (int k = 0; k < 7*size; k++)
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
	
	public Cell[][] getBoardObject(){
		return boardObject;
	}
	
	public void setBoardObject(Cell[][] array){
		boardObject = array;
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
				game.character.setLocation(getNorth(curLoc));
			if (object instanceof Wumpus)
				game.wumpus.setLocation(getNorth(curLoc));
			if (object instanceof SuperBat)
				game.superBat.setLocation(getNorth(curLoc));
			break;
		case 's':
			if (object instanceof Adventurer)
				game.character.setLocation(getSouth(curLoc));
			if (object instanceof Wumpus)
				game.wumpus.setLocation(getSouth(curLoc));
			if (object instanceof SuperBat)
				game.superBat.setLocation(getSouth(curLoc));
			break;
		case 'w':
			if (object instanceof Adventurer)
				game.character.setLocation(getWest(curLoc));
			if (object instanceof Wumpus)
				game.wumpus.setLocation(getWest(curLoc));
			if (object instanceof SuperBat)
				game.superBat.setLocation(getWest(curLoc));
			break;
		case 'e':
			if (object instanceof Adventurer)
				game.character.setLocation(getEast(curLoc));
			if (object instanceof Wumpus)
				game.wumpus.setLocation(getEast(curLoc));
			if (object instanceof SuperBat)
				game.superBat.setLocation(getEast(curLoc));
			break;
		case 'u':
			if (object instanceof Adventurer){
				game.character.shoot(getNorth(game.character.getLocation()));
			}
			break;
		case 'd':
			if (object instanceof Adventurer){
				game.character.shoot(getSouth(game.character.getLocation()));
			}
			break;
		case 'l':
			if (object instanceof Adventurer){
				game.character.shoot(getWest(game.character.getLocation()));
			}
			break;
		case 'r':
			if (object instanceof Adventurer){
				game.character.shoot(getEast(game.character.getLocation()));
			}
			break;
		}
	}
	
	public int getSize(){
		return size;
	}
	
}
