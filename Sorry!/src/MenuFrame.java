import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * JFrame to display menu options to the user. Used to select game settings then
 * initiate a new game.
 * 
 * @author sturgedl. Created Apr 17, 2013.
 */
public class MenuFrame extends JFrame {
	private static final int TITLE_WIDTH = 1000;
	private static final int TITLE_HEIGHT = 100;

	public MenuFrame(String lang) {
		JPanel titleLabel = new JPanel();
		JLabel title = new JLabel("Sorry!©");
		title.setFont(new Font("Georgia", Font.BOLD, 60));
		titleLabel.setPreferredSize(new Dimension(TITLE_WIDTH, TITLE_HEIGHT));

		
	}

}
