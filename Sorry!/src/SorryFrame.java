import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class SorryFrame extends JFrame implements ActionListener {
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

}
