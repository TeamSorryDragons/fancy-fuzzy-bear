import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class DisplayableBoard extends JComponent {
	private static final long serialVersionUID = 1L;
	BoardList board;
	private static ImageIcon image;
	public DisplayableBoard(BoardList board){
		super();
		this.setSize(1000, 1000);
		this.board = board;
//		File fil = new File("Board.jpg");
		image = new ImageIcon("Board.jpg");
		this.setBackground(Color.GREEN);
	}
	public void paintComponent(Graphics g){
		image.paintIcon(this,  g, 100, 100);
	}
}
