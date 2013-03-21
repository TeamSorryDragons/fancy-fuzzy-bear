import java.awt.*;
import javax.swing.*;

public class Main {
	static BoardList game;
	static Engine engine;
	static JFrame frame;

	public static void main(String args[]) {
		game = new BoardList();
		engine = new Engine(game);
		engine.newGame();
		frame = new SorryFrame(game, engine);
		for (int i = 0; i < 80; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			engine.testing();
			frame.repaint();
		}
		for (int i = 0; i < 67; i++)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			engine.testing2();
			frame.repaint();
		}
	}
}
