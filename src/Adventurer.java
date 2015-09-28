
public class Adventurer extends MovingObject{
	private boolean collected;
    
    public Adventurer(int x, int y, Game g){
    	super(x, y, g);
    	collected = false;
    	game = g;
    }
    
    public Adventurer(Game g){
    	super(g);
    	collected = false;
    	game = g;
    }
    
    public void collectTreasure(){
    	collected = true;
    }
    
    public boolean collectedTreasure(){
    	return collected;
    }
    
    
    public void shoot(Location l){
    	if (game.wumpus.getLocation().equalsTo(l)){
    		game.wumpus.die();
    	} else {
    		game.wumpus.moveRandomly();
    	}
    }
	
	
}
