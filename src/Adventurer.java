
public class Adventurer extends MovingObject{

    
    public Adventurer(int x, int y){
    	super(x, y);
    	
    }
    
    public Adventurer(){
    	super();
    }
    
    
    public void die(){
    	System.out.println("Sorry, you lost!");
    	//end the game.isLost();?
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
