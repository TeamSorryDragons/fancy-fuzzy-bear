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
		draw(g);
	}

	public void draw(Graphics g) {
		Node[] cornerPointers = board.getCornerPointers();
		int size = image.getWidth() / 16;
		Graphics2D g2 = (Graphics2D) g;
		draw(cornerPointers[0], cornerPointers[0].getNext(), Math.PI,
				image.getWidth() - 2*size, image.getHeight() - size, size, g,
				Piece.COLOR.red);
	}

	public void draw(Node start, Node next, Double angle, int x, int y,
			int size, Graphics g, Piece.COLOR curcolor) {
		Graphics2D g2 = (Graphics2D) g;
		if (start == next)
			return;
		if (next.getPieces()[0] != null) {
			System.out.println(x + " : " + y + " THAR BE PIECES " + angle);
			switch (next.getPieces()[0].col) {
			case blue:
				g2.setColor(Color.blue);
				break;
			case green:
				g2.setColor(Color.green);
				break;
			case red:
				g2.setColor(Color.red);
				break;
			case yellow:
				g2.setColor(Color.yellow);
				break;
			}
			g2.fillOval(x, y, size, size);
			g2.setColor(Color.black);
			g2.drawOval(x, y, size, size);
		}
		if (next.getNext() == null)
			return;
		if (next instanceof SlideNode) {
			if (((SlideNode) next).getSafeNode() != null) {
				draw(next, ((SlideNode) next).getSafeNode(), angle
						- Math.PI / 2,
						(int) Math.round(x + size * Math.cos(angle - Math.PI / 2)),
						(int) Math.round(y - size * Math.sin(angle - Math.PI / 2)), size,
						g, curcolor);
			}
		}
		if (next.getNext().getColor() != Piece.COLOR.colorless
				&& next.getNext().getColor() != curcolor) {
			draw(start, next.getNext(), angle - Math.PI / 2, (int) Math.round(x + size
					* Math.cos(angle - Math.PI / 2)),
					(int) Math.round(y - size * Math.sin(angle - Math.PI / 2)), size, g,
					next.getNext().getColor());
		} else {
			draw(start, next.getNext(), angle,
					(int) Math.round(x + size * Math.cos(angle)),
					(int) Math.round(y - size * Math.sin(angle)), size, g, curcolor);
		}

	}
}
