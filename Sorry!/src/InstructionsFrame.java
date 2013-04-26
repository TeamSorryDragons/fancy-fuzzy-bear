import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Frame to contain the instructions for Sorry!. Displays the instructions and a
 * button to close the frame.
 * 
 * @author sturgedl. Created Apr 17, 2013.
 */
@SuppressWarnings("serial")
public class InstructionsFrame extends JFrame {
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;

	public InstructionsFrame(String lang) {
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);

		try {
			String fileName = InstructionsFrame.obtainFileName(lang);	
			JEditorPane instr = createEditorPane(fileName);
			JScrollPane scroll = createScrollPane(instr);
			this.getContentPane().add(scroll, BorderLayout.CENTER);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		this.pack();
		this.repaint();

	}
	
	protected static JScrollPane createScrollPane(JEditorPane instrPane){
		JScrollPane scroll = new JScrollPane(instrPane);
		scroll.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		scroll.setMinimumSize(new Dimension(100, 100));
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scroll;
	}
	
	protected static JEditorPane createEditorPane(String fileName) throws IOException{
		URL location = InstructionsFrame.class.getResource(fileName);
		if (location == null)
			System.err.println("Couldn't find the file: " + fileName);
		JEditorPane instrPane = new JEditorPane();
		instrPane.setPage(location);
		instrPane.setContentType("text/html; charset=utf-8");
		return instrPane;
	}

	/**
	 * What does it sound like? Probably does that.
	 * 
	 * @param lang
	 * @return
	 */
	protected static String obtainFileName(String lang) {
		return "/images/instructions_" + lang + ".html";
	}

	public static void main(String args[]) {
		new InstructionsFrame("french");
	}

}
