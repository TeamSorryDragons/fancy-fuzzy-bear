import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * JFrame to display menu options to the user. Used to select game settings then
 * initiate a new game.
 * 
 * @author sturgedl. Created Apr 17, 2013.
 */
public class MenuFrame extends JFrame {
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 800;

	private static final int TITLE_WIDTH = 1000;
	private static final int TITLE_HEIGHT = 100;

	private JPanel buttonPanel;
	private ArrayList<JButton> buttons;

	private String language;
	private String[] labels;
	
	private JTextField p1;
	private JTextField p2;
	private JTextField p3;
	private JTextField p4;

	public MenuFrame(String lang) {
		super();
		this.language = lang;
		this.labels = MenuFrame.obtainButtonLabels(lang);
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
		this.add(buttonPanel);
	}

	/**
	 * Fetch the button labels from an appropriate location based on the given
	 * language.
	 * 
	 * @param lang
	 * @return
	 */
	private static String[] obtainButtonLabels(String lang) {
		String[] ret = new String[4];
		Scanner in;
		try {
			in = new Scanner(new File(lang +".txt"));
			for (int x = 0; x < 26; x++)
				in.nextLine();
			
			ret[0] = in.nextLine();
			ret[1] = in.nextLine();
			ret[2] = in.nextLine();
			ret[3] = in.nextLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	private void initializeButtons() {
		if (this.buttonPanel == null)
			this.buttonPanel = new JPanel();
		if (this.buttons == null)
			this.buttons = new ArrayList<JButton>();

		JButton newGame = new JButton(this.labels[0]);
		this.buttons.add(newGame);
		JButton loadGame = new JButton(this.labels[1]);
		this.buttons.add(loadGame);
		JButton instructions = new JButton(this.labels[2]);
		this.buttons.add(instructions);
		JButton exit = new JButton(this.labels[3]);
		this.buttons.add(exit);

		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MenuFrame.this.getContentPane().removeAll();
				MenuFrame.this.repaint();
				JPanel newGamePanel = new JPanel();
				newGamePanel.setBackground(Color.black);
//				newGamePanel.setLayout(new GridLayout(3,1));
//				JLabel playerName = new JLabel();
//				playerName.setText("Name your Players");
//				newGamePanel.add(playerName);
//				JPanel textBoxPanel = new JPanel();
//				textBoxPanel.setLayout(new GridLayout(4 , 2));
//				JLabel red = new JLabel("red");
//				JLabel blue = new JLabel("blue");
//				JLabel green = new JLabel("green");
//				JLabel yellow =new JLabel("yellow");
//				
//				p1 = new JTextField();
//				p2 = new JTextField();
//				p3 = new JTextField();
//				p4 = new JTextField();
//				p1.setEditable(true);
//				p2.setEditable(true);
//				p3.setEditable(true);
//				p4.setEditable(true);
//				textBoxPanel.add(red);
//				textBoxPanel.add(p1);
//				textBoxPanel.add(blue);
//				textBoxPanel.add(p2);;
//				textBoxPanel.add(yellow);
//				textBoxPanel.add(p3);
//				textBoxPanel.add(green);
//				textBoxPanel.add(p4);
//				newGamePanel.add(textBoxPanel);
//				JButton btnStart = new JButton("Start");
//				btnStart.addActionListener(new ActionListener(){
//					@Override
//					public void actionPerformed(ActionEvent evt) {
//					    ArrayList<String> player=new ArrayList<String>();
//						if(MenuFrame.this.p1.getText()!=null)
//							player.add(p1.getText()+"|red");
//						if(MenuFrame.this.p2.getText()!=null)
//							player.add(p2.getText()+"|blue");
//						if(MenuFrame.this.p3.getText()!=null)
//							player.add(p3.getText()+"|yellow");
//						if(MenuFrame.this.p4.getText()!=null)
//							player.add(p4.getText()+"|green");
//						MenuFrame.this.createNewGame(player);
//					}
//					
//				});
//				newGamePanel.add(btnStart);
				newGamePanel.setVisible(true);
				newGamePanel.setPreferredSize(new Dimension(600,800));
				MenuFrame.this.getContentPane().add(newGamePanel);
				MenuFrame.this.repaint();
			}
		});

		loadGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MenuFrame.this.loadExistingGame();

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

		for (JButton b : this.buttons)
			this.buttonPanel.add(b);

	}

	protected void loadExistingGame() {
		// TODO update to reflect the constructor reading from a file
//		this.setEnabled(false);
		SorryFrame sorry = new SorryFrame(this.language);
		sorry.load("save.txt");
		this.dispose();
		sorry.start();
	}

	/**
	 * Method to ask the user for names then create a new save file, then load
	 * the fresh game.
	 * 
	 */
	protected void createNewGame(ArrayList<String> players){
		// TODO Auto-generated method stub.
		SorryFrame sorry = new SorryFrame(this.language);
		sorry.passPlayers(players);
		this.dispose();

	}

	private void displayInstructions() {
		JFrame instr = new InstructionsFrame(this.language);
	}

	public static void main(String args[]) {
		JFrame frame = new MenuFrame("english");
		//JFrame frame2 = new SorryFrame("english");
	}

}
