
public class Adventurer extends MovingObject{
	private boolean collected;
    
    public Adventurer(int x, int y){
    	super(x, y);
    	collected = false;
    	
    }
    
    public Adventurer(){
    	super();
    	collected = false;
    }
    
    public void collectTreasure(){
    	collected = true;
    }
    
    public boolean collectedTreasure(){
    	return collected;
    }
    
    
    public void shoot(char d){
    	switch (d){
    	//TODO
    	case 'n': ; break;
    	case 's': ; break;
    	case 'w': ; break;
    	case 'e': ; break;
    	}
    }
	
	
}
