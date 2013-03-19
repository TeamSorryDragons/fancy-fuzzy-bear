import java.awt.*;
import javax.swing.*;

public class Main {
	static BoardList game;
	static JFrame frame;
	public static void main(String args[]){
		game = new BoardList();
		game.newGame();
		frame = new SorryFrame(game);
	}
}
