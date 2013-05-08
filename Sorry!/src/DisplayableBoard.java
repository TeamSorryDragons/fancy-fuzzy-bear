import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class DisplayableBoard extends JComponent {
	private static final long serialVersionUID = 1L;
	EngineInterface gameEngine;
	private static BufferedImage[] image = new BufferedImage[4];
	private int size;

	public DisplayableBoard(EngineInterface eng) {
		super();
		this.gameEngine = eng;
		try {
			image[0] = ImageIO.read(this.getClass().getResource(
					"/images/Board.jpg"));
			image[1] = ImageIO.read(this.getClass().getResource(
					"/images/Board2.jpg"));
			image[2] = ImageIO.read(this.getClass().getResource(
					"/images/Board3.jpg"));
			image[3] = ImageIO.read(this.getClass().getResource(
					"/images/Board4.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		draw(g);
	}

	@SuppressWarnings("incomplete-switch")
	public void draw(Graphics g) {
		Node[] cornerPointers = this.gameEngine.getActualBoard()
				.getCornerPointers();
		Graphics2D g2 = (Graphics2D) g;
		g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 40));
		int i = 0;
		Player ply;
		if(this.gameEngine.getOwner() == null){
			ply = this.gameEngine.getActivePlayer();
		}
		else{
			ply = this.gameEngine.getOwner();
		}
		switch (ply.getColor()) {
		case red:
			i = 0;
			break;
		case blue:
			i = 1;
			break;
		case green:
			i = 3;
			break;
		case yellow:
			i = 2;
			break;
		}
		int size = image[i].getWidth() / 16;
		g.drawImage(image[i], 0, 0, null);
		this.size = size;
		Player pl;
		if(gameEngine.getOwner()== null){
			pl = gameEngine.getActivePlayer();
		}
		else{
			pl = gameEngine.getOwner();
		}
		draw(cornerPointers[i], cornerPointers[i].getNext(), Math.PI,
				image[i].getWidth() - 2 * size, image[i].getHeight()
						- this.size, g2, pl.getColor());
	}

	@SuppressWarnings("incomplete-switch")
	public void draw(Node start, Node next, Double angle, int x, int y,
			Graphics2D g, Piece.COLOR curcolor) {
		if (next.hasPiece()) {
			switch (next.firstPiece().col) {
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
				g.drawString(((MultiNode) next).numberOfPieces() + "",
						(int) Math.round(x + size * Math.cos(angle)),
						(int) Math.round(y - size * Math.sin(angle)));
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
		if (next.getPrevious() == null)
			return;
		if (next instanceof SlideNode) {
			if (((SlideNode) next).getSafeNode() != null) {
				draw(next,
						((SlideNode) next).getSafeNode(),
						angle - Math.PI / 2,
						(int) Math.round(x + size
								* Math.cos(angle - Math.PI / 2)),
						(int) Math.round(y - size
								* Math.sin(angle - Math.PI / 2)), g, curcolor);
			}
		}
		if (next.getNext().getColor() != Piece.COLOR.colorless
				&& next.getNext().getColor() != curcolor) {
			draw(start, next.getNext(), angle - Math.PI / 2,
					(int) Math.round(x + size * Math.cos(angle - Math.PI / 2)),
					(int) Math.round(y - size * Math.sin(angle - Math.PI / 2)),
					g, next.getNext().getColor());
		} else {
			draw(start, next.getNext(), angle,
					(int) Math.round(x + size * Math.cos(angle)),
					(int) Math.round(y - size * Math.sin(angle)), g, curcolor);
		}

	}
}
