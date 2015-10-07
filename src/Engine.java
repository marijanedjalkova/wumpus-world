/** Class with the main method*/
public class Engine {

	public static void main(String[] args) {
		int complexity = 5;
		int size = 10;
		//TODO replace with a customised value
		Game game = new Game(complexity, size);
		game.start();

	}

}
