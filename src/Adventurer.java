
public class Adventurer extends MovingObject{

    
    public Adventurer(int x, int y){
    	super(x, y);
    	
    }
    
    
    public void die(){
    	System.out.println("Sorry, you lost!");
    	//end the game.isLost();?
    }
    
    public void shoot(char d){
    	switch (d){
    	//TODO
    	case 'N': ; break;
    	case 'S': ; break;
    	case 'W': ; break;
    	case 'E': ; break;
    	}
    }
	
	
}
