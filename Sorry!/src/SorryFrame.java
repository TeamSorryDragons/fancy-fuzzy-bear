import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class SorryFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private BoardList board;
	
	public SorryFrame(BoardList board){
		super("Sorry!");
		this.board = board;
		this.setSize(1000,1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent displayBoard = new DisplayableBoard(this.board);
		this.add(displayBoard);
//		displayBoard.setSize(width, height)
		this.setVisible(true);
		this.repaint();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
