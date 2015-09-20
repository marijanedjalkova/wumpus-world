
public class Cell {
	int xPos;
	int yPos;
	boolean isWumpus;
	boolean isSmell;
	boolean isBreeze;
	boolean isBat;
	boolean isAdventurer;
	boolean isExit;
	boolean isTreasure;
	boolean isGlitter;
	boolean isPit;
	
	public Cell(int x, int y){
		xPos = x;
		yPos = y;
		
	}
	
	public void setAdventurer(Adventurer p){
		isAdventurer = true;
	}
	
	public void setWumpus(Wumpus w){
		isWumpus = true;
	}
	
}
