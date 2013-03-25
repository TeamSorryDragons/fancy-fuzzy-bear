import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class SorryFrame extends JFrame implements ActionListener {
	public static final int BOARD_WIDTH = 1000;
	public static final int BOARD_HEIGHT = 1000;
	public static final int BOARD_ROWS = 16;
	public static final int BOARD_COLS = 16;
	public static final double CELL_WIDTH = ((double) BOARD_WIDTH / BOARD_COLS);
	public static final double CELL_HEIGHT = ((double) BOARD_HEIGHT / BOARD_ROWS);

	private static final long serialVersionUID = 1L;
	private BoardList board;
	private Engine engine;

	/*
	 * Indices 0-3 are red, Indices 4-7 are blue, Indices 8-11 are Yellow,
	 * Indices 12-15 are green
	 */
	public SorryFrame(BoardList board, Engine engine) {
		super("Sorry!");
		this.board = board;
		this.engine = engine;
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent displayBoard = new DisplayableBoard(this.board);
		this.add(displayBoard);
		// displayBoard.setSize(width, height)
		this.setVisible(true);
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Container class for mouse-click coordinates. Really just to provide
	 * convenience, because Java is really horrible at dealing with multiple
	 * return values. If this was a nice language like Python or Scheme or
	 * really almost anything else then I could just return a tuple but because
	 * it's Java and whatnot I have to write an entire freaking class just to
	 * conveniently return 2 integers. So yeah. It contains 2 integers.
	 * 
	 * @author sturgedl. Created Mar 24, 2013.
	 */
	protected static class Coordinate {
		private int x;
		private int y;

		/**
		 * Makes a coordinate. Don't be stupid.
		 * 
		 * @param x
		 * @param y
		 */
		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@SuppressWarnings("javadoc")
		public int getX() {
			return this.x;
		}

		@SuppressWarnings("javadoc")
		public int getY() {
			return this.y;
		}

		@SuppressWarnings("javadoc")
		public void setX(int x) {
			this.x = x;
		}

		@SuppressWarnings("javadoc")
		public void setY(int y) {
			this.y = y;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Coordinate)
				return this.equals((Coordinate) o);
			return false;
		}

		/**
		 * Checks if 2 coordinates are equal, based on their x and y values.
		 * 
		 * @param c
		 * @return true if equal... duh
		 */
		public boolean equals(Coordinate c) {
			return this.x == c.x && this.y == c.y;
		}

	}

	/**
	 * Given an (x, y) tuple of doubles, will return an appropriate board grid
	 * coordinate of integers.
	 * 
	 * @param x
	 * @param y
	 * 
	 * @return Coordinate, position on board corresponding to x and y
	 */
	public static Coordinate convertClickToCoordinate(double x, double y) {
		int xCoord = (int) Math.floor((x / CELL_WIDTH));
		int yCoord = (int) Math.floor((y / CELL_HEIGHT));

		return new Coordinate(xCoord, yCoord);
	}

}
