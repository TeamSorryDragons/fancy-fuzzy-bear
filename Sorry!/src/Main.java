import java.awt.*;
import javax.swing.*;

public class Main {
	static BoardList game;
	static Engine engine;
	static JFrame frame;
	public static void main(String args[]){
		game = new BoardList();
		engine = new Engine(game);
		engine.newGame();
		frame = new SorryFrame(game,engine);
		try {
			Thread.sleep( 500 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		engine.testing();
	}
}
