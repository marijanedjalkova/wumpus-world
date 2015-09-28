
public abstract class MovingObject {
    protected Location location;
    protected Game game;
    
    public MovingObject(int x, int y, Game g){
    	location = new Location(x, y);
    	game = g;
    }
    
    public MovingObject(Game g){
    	this(0, 0, g);
    }
    
    public void setLocation(Location l){
    	location = l;
    }
    
    public Location getLocation(){
    	return location;
    }
    
   
}
