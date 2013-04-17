import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * JFrame to display menu options to the user. Used to select game settings then
 * initiate a new game.
 * 
 * @author sturgedl. Created Apr 17, 2013.
 */
public class MenuFrame extends JFrame {
	private static final int FRAME_WIDTH = 1330;
	private static final int FRAME_HEIGHT = 1040;

	private static final int TITLE_WIDTH = 1000;
	private static final int TITLE_HEIGHT = 100;

	private JPanel buttonPanel;
	private ArrayList<JButton> buttons;

	public MenuFrame(String lang) {
		super();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

		JPanel titleLabel = new JPanel();
		titleLabel.setBackground(Color.CYAN);

		JLabel title = new JLabel("Sorry!©");
		title.setFont(new Font("Georgia", Font.BOLD, 60));
		titleLabel.setPreferredSize(new Dimension(TITLE_WIDTH, TITLE_HEIGHT));
		titleLabel.add(title, BorderLayout.CENTER);
		this.add(titleLabel, BorderLayout.NORTH);

		this.buttonPanel = new JPanel();
		this.buttons = new ArrayList<JButton>();

		this.initializeButtons();
	}

	private void initializeButtons() {
		if (this.buttonPanel == null)
			this.buttonPanel = new JPanel();
		if (this.buttons == null)
			this.buttons = new ArrayList<JButton>();

		JButton newGame = new JButton();
		this.buttons.add(newGame);
		JButton loadGame = new JButton();
		this.buttons.add(loadGame);
		JButton instructions = new JButton();
		this.buttons.add(instructions);
		JButton exit = new JButton();
		this.buttons.add(exit);

		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub.

			}
		});

		loadGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub.

			}
		});

		instructions.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MenuFrame.this.displayInstructions();

			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

	}
	
	private void displayInstructions(){
		
		
		
	}

	public static void main(String args[]) {
		JFrame frame = new MenuFrame("English");
	}

}
