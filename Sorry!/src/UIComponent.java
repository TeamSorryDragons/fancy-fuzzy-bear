import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Contains the graphic user interface portion of the graphical user interface
 * for Sorry! in which people will choose things and get information about the
 * current game state in a graphical fashion.
 * 
 * @author sturgedl. Created Apr 10, 2013.
 */
@SuppressWarnings("serial")
public class UIComponent extends JPanel {
	public static final int CARD_HOLDER_HEIGHT = 650;
	public static final int PLAYER_INFO_HEIGHT = 225;
	public static final int BUTTON_PANE_HEIGHT = 100;
	public static final int TOP_PAD = 25;
	public static final int HORIZONTAL_PAD = 25;

	public static final int CARD_HEIGHT = 600;
	public static final int CARD_WIDTH = 300;

	private Card currentCard;
	private SorryFrame gameFrame;
	private String[] buttonLabels;
	ArrayList<JButton> interfaceButtons;
	JPanel cardHolder;
	JPanel playerInformation;
	JPanel buttonPane;

	JLabel playerNameText;
	JLabel playerPiecesInStart;
	private FileReader fr;

	JTextArea card;
	JTextArea card2;
	JTextArea card3;
	
	JTextArea playerHolder;

	public UIComponent(int width, int height, SorryFrame frame, String lang) {
		this.gameFrame = frame;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(width, height);
		this.setBackground(Color.CYAN);

		this.cardHolder = new JPanel();
		this.cardHolder.setSize(width - HORIZONTAL_PAD, CARD_HOLDER_HEIGHT);
		this.add(this.cardHolder);

		this.playerInformation = new JPanel();
		this.playerInformation.setSize(width - HORIZONTAL_PAD,
				PLAYER_INFO_HEIGHT);
		this.add(this.playerInformation);
		this.playerNameText=new JLabel();
		
		
		this.buttonPane = new JPanel();
		this.buttonPane.setSize(width - HORIZONTAL_PAD, BUTTON_PANE_HEIGHT);
		this.add(this.buttonPane);
		try {
			fr = new FileReader(lang + ".txt");
		} catch (FileNotFoundException e) {
		}
		Scanner in = new Scanner(fr);
		for (int i = 0; i < 23; i++)
			in.nextLine();
		buttonLabels = new String[3];
		for (int i = 0; i < 3; i++)
			buttonLabels[i] = in.nextLine();
		this.initializeCardHolder();
		this.initializePlayerInfo();
		this.initializeButtonPane();

		this.setVisible(true);
		this.repaint();

	}

	//This updates the text components of the GUI
	public void update() {
		this.currentCard = this.gameFrame.currentCard;
		this.gameFrame.engine.getActivePlayer();
		if (this.currentCard.cardNum != 13) {
			this.card.setText(this.currentCard.cardNum + "");
			this.card2.setText("" + this.currentCard.desc);
			this.card3.setText("" + this.currentCard.cardNum);
		} else {
			this.card.setText("Sorry!");
			this.card2.setText("" + this.currentCard.desc);
			this.card3.setText("!Sorry");
		}
		if(this.gameFrame.engine.getActivePlayer().getColor()==Piece.COLOR.blue){
			this.playerHolder.setForeground(Color.WHITE);
			this.playerInformation.setBackground(Color.BLUE);
			this.playerHolder.setBackground(Color.BLUE);
		}
		else if(this.gameFrame.engine.getActivePlayer().getColor()==Piece.COLOR.green){
			this.playerHolder.setForeground(Color.BLACK);
			this.playerInformation.setBackground(Color.GREEN);
			this.playerHolder.setBackground(Color.GREEN);
		}
		else if(this.gameFrame.engine.getActivePlayer().getColor()==Piece.COLOR.yellow){
			this.playerHolder.setForeground(Color.BLACK);
			this.playerInformation.setBackground(Color.YELLOW);
			this.playerHolder.setBackground(Color.YELLOW);
		}
		else{
			this.playerHolder.setForeground(Color.WHITE);
			this.playerInformation.setBackground(Color.RED);
			this.playerHolder.setBackground(Color.RED);
		}
		this.playerHolder.setText(this.gameFrame.engine.getActivePlayer().getName());
	}

	private void initializeCardHolder() {
		// TODO draw the card right here and stuff
		this.card = new JTextArea();
		this.card.setFont(new Font(this.card.getFont().getName(), Font.PLAIN,
				40));
		this.card.setPreferredSize(new Dimension(this.getWidth(), CARD_HOLDER_HEIGHT/4));
		this.card.setWrapStyleWord(true);
		this.card.setLineWrap(true);
		this.card.setBackground(Color.BLACK);
		this.card.setEditable(false);
		this.card.setForeground(Color.WHITE);
		this.card2 = new JTextArea();
		this.card2.setFont(new Font(this.card2.getFont().getName(), Font.PLAIN,
				20));
		this.card2.setPreferredSize(new Dimension(this.getWidth(), CARD_HOLDER_HEIGHT/2));
		this.card2.setWrapStyleWord(true);
		this.card2.setLineWrap(true);
		this.card2.setBackground(Color.BLACK);
		this.card2.setEditable(false);
		this.card2.setForeground(Color.WHITE);
		this.card3 = new JTextArea();
		this.card3.setFont(new Font(this.card3.getFont().getName(), Font.PLAIN,
				40));
		this.card3.setPreferredSize(new Dimension(this.getWidth(), CARD_HOLDER_HEIGHT/4));
		this.card3.setWrapStyleWord(true);
		this.card3.setLineWrap(true);
		this.card3.setBackground(Color.BLACK);
		this.card3.setEditable(false);
		this.card3.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.card3.setForeground(Color.WHITE);
		this.cardHolder.setLayout(new BorderLayout());
		this.cardHolder.add(card,BorderLayout.NORTH);
		this.cardHolder.add(card2,BorderLayout.CENTER);
		this.cardHolder.add(card3,BorderLayout.SOUTH);
		this.cardHolder.setBackground(Color.BLACK);

	}

	

	private void initializePlayerInfo() {
		this.playerHolder = new JTextArea();
		this.playerHolder.setPreferredSize(new Dimension(this.getWidth(),PLAYER_INFO_HEIGHT));
		this.playerHolder.setFont(new Font(this.playerHolder.getFont().getName(), Font.PLAIN, 60));
		this.playerHolder.setLineWrap(true);
		this.playerHolder.setWrapStyleWord(true);
		this.playerHolder.setBackground(Color.RED);
		this.playerInformation.add(this.playerHolder);
		this.playerInformation.setBackground(Color.RED);
	}

	private void initializeButtonPane() {
		JButton exitButton = new JButton(buttonLabels[0]);
		JButton saveButton = new JButton(buttonLabels[1]);
		JButton forfeitButton = new JButton(buttonLabels[2]);

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
