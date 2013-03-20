import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

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
				image.getWidth() - 2 * size, image.getHeight() - size, size, g2,
				Piece.COLOR.red);
	}

	@SuppressWarnings("incomplete-switch")
	public void draw(Node start, Node next, Double angle, int x, int y,
			int size, Graphics2D g, Piece.COLOR curcolor) {
		if (next.getPieces()[0] != null) {
			switch (next.getPieces()[0].col) {
			case blue:
				g.setColor(Color.blue);
				break;
			case green:
				g.setColor(Color.green);
				break;
			case red:
				g.setColor(Color.red);
				break;
			case yellow:
				g.setColor(Color.yellow);
				break;
			}
			if (next instanceof MultiNode) {
				g.fillOval((int) Math.round(x + size * Math.cos(angle)),
						(int) Math.round(y - size * Math.sin(angle)), size,
						size);
				g.setColor(Color.black);
				g.drawOval((int) Math.round(x + size * Math.cos(angle)),
						(int) Math.round(y - size * Math.sin(angle)), size,
						size);
			} else {
				g.fillOval(x, y, size, size);
				g.setColor(Color.black);
				g.drawOval(x, y, size, size);
			}

		}
		if (start == next)
			return;
		if (next.getNext() == null)
			return;
		if (next instanceof SlideNode) {
			if (((SlideNode) next).getSafeNode() != null) {
				draw(next,
						((SlideNode) next).getSafeNode(),
						angle - Math.PI / 2,
						(int) Math.round(x + size
								* Math.cos(angle - Math.PI / 2)),
						(int) Math.round(y - size
								* Math.sin(angle - Math.PI / 2)), size, g,
						curcolor);
			}
		}
		if (next.getNext().getColor() != Piece.COLOR.colorless
				&& next.getNext().getColor() != curcolor) {
			draw(start, next.getNext(), angle - Math.PI / 2,
					(int) Math.round(x + size * Math.cos(angle - Math.PI / 2)),
					(int) Math.round(y - size * Math.sin(angle - Math.PI / 2)),
					size, g, next.getNext().getColor());
		} else {
			draw(start, next.getNext(), angle,
					(int) Math.round(x + size * Math.cos(angle)),
					(int) Math.round(y - size * Math.sin(angle)), size, g,
					curcolor);
		}

	}
}
