import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Contains the graphic user interface portion of the graphical user interface
 * for Sorry! in which people will choose things and get information about the
 * current game state in a graphical fashion.
 * 
 * @author sturgedl. Created Apr 10, 2013.
 */
public class UIComponent extends JPanel {
	public static final int CARD_HOLDER_HEIGHT = 650;
	public static final int PLAYER_INFO_HEIGHT = 225;
	public static final int BUTTON_PANE_HEIGHT = 100;
	public static final int TOP_PAD = 25;
	public static final int HORIZONTAL_PAD = 25;
	
	public static final int CARD_HEIGHT = 600;
	public static final int CARD_WIDTH = 300;
	
	private Card currentCard;
	private Player activePlayer;
	private SorryFrame gameFrame;
	ArrayList<JButton> interfaceButtons;
	JPanel cardHolder;
	JPanel playerInformation;
	JPanel buttonPane;
	
	JLabel playerNameText;
	JLabel playerPiecesInStart;
	
	public UIComponent(int width, int height, SorryFrame frame){
		this.gameFrame = frame;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(width, height);
		
		this.setBackground(Color.CYAN);
		
		this.cardHolder = new JPanel();
		this.cardHolder.setSize(width - HORIZONTAL_PAD, CARD_HOLDER_HEIGHT);
		this.add(this.cardHolder);
		
		this.playerInformation = new JPanel();
		this.playerInformation.setSize(width - HORIZONTAL_PAD, PLAYER_INFO_HEIGHT);
		this.add(this.playerInformation);
		
		this.buttonPane = new JPanel();
		this.buttonPane.setSize(width - HORIZONTAL_PAD, BUTTON_PANE_HEIGHT);
		this.add(this.buttonPane);
		
		this.initializeCardHolder();
		this.initializePlayerInfo();
		this.initializeButtonPane();
		
		this.setVisible(true);
		this.repaint();
		
	}
	
	
	private void initializeCardHolder(){
		Graphics2D g2 = (Graphics2D) this.cardHolder.getGraphics();
		// TODO draw the card right here and stuff
		this.cardHolder.add(Box.createRigidArea(new Dimension(this.getWidth(), CARD_HOLDER_HEIGHT)));
		this.cardHolder.setBackground(Color.YELLOW);
		
	}
	
	private void initializePlayerInfo(){
		this.playerInformation.add(Box.createRigidArea(new Dimension(this.getWidth(), PLAYER_INFO_HEIGHT)));
		this.playerInformation.setBackground(Color.RED);
	}
	
	private void initializeButtonPane(){
		JButton exitButton = new JButton("Exit");
		JButton saveButton = new JButton("Save");
		JButton forfeitButton = new JButton("Forfeit Turn");
		
		exitButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				UIComponent.this.gameFrame.quitGame();
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIComponent.this.gameFrame.saveGame();
			}
		});
		
		forfeitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIComponent.this.gameFrame.forfeitTurn();
			}
		});
		
		this.interfaceButtons = new ArrayList<JButton>();
		this.interfaceButtons.add(exitButton);
		this.interfaceButtons.add(saveButton);
		this.interfaceButtons.add(forfeitButton);
		
		this.buttonPane.add(exitButton);
		this.buttonPane.add(saveButton);
		this.buttonPane.add(forfeitButton);
	}
}
