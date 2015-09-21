
public abstract class MovingObject {
    protected Location location;
    
    public MovingObject(int x, int y){
    	location = new Location(x, y);
    }
    
    public MovingObject(){
    	this(0, 0);
    }
    
    public void setLocation(Location l){
    	location = l;
    }
    
    public Location getLocation(){
    	return location;
    }
    
   
}
