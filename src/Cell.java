
public class Cell {
	int xPos;
	int yPos;
	boolean isWumpus;
	boolean isBat;
	boolean isAdventurer;
	boolean isExit;
	boolean isTreasure;
	boolean isPit;
	private boolean[] percepts; //smell, breeze, glitter
	private final int SMELL = 0;
	private final int BREEZE = 1;
	private final int GLITTER = 2;
	
	public Cell(int x, int y){
		xPos = x;
		yPos = y;
		percepts = new boolean[3];
	}
	
	public void print(){
		System.out.print("<");
		if (isWumpus)
			System.out.print("W");
		if (isAdventurer)
			System.out.print("A");
		if (isTreasure)
			System.out.print("T");
		if (isExit)
			System.out.print("E");
		if (isPit)
			System.out.print("P");
		if (isBat)
			System.out.print("B");
		if (percepts[SMELL])
			System.out.print("sm");
		if (percepts[BREEZE])
			System.out.print("br");
		if (percepts[GLITTER])
			System.out.print("gl");
		System.out.print(">");
	}
	
	public void smells(){
		percepts[SMELL] = true;
	}
	
	public void glitters(){
		percepts[GLITTER] = true;
	}
	
	public void breezes(){
		percepts[BREEZE] = true;
	}
	
	public boolean getSmell(){
		return percepts[SMELL];
	}
	
	public boolean getBreeze(){
		return percepts[BREEZE];
	}
	
	public boolean getGlitter(){
		return percepts[GLITTER];
	}
	
}
