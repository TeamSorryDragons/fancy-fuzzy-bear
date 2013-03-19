import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DisplayableBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	BoardList board;
	private static BufferedImage image;
	public DisplayableBoard(BoardList board){
		super();
		this.board = board;
		try {
			DisplayableBoard.image = ImageIO.read(new File("board.bmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void paintComponent(Graphics g){
		g.drawImage(DisplayableBoard.image, 0, 0, null);
	}
}
