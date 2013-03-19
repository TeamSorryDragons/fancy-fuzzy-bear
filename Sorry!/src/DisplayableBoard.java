import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class DisplayableBoard extends JComponent {
	private static final long serialVersionUID = 1L;
	BoardList board;
	private static BufferedImage image;

	public DisplayableBoard(BoardList board) {
		super();
		this.board = board;
		try {
			image = ImageIO.read(this.getClass().getResource(
					"/images/Board.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}
}
