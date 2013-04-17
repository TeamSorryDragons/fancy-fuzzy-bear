import java.awt.*;
import javax.swing.*;

public class Main {
	static JFrame frame;

	public static void main(String args[]) {
		PickLanguage start = new PickLanguage();
		start.setVisible(true);
		start.repaint();
		frame = new SorryFrame("english");
	}
}
