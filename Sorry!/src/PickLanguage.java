import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;



public class PickLanguage extends JFrame{
	public PickLanguage(){
		JFrame frame = new JFrame();
		JPanel panel= new JPanel();
		JRadioButton jrbEng = new JRadioButton("English");
		JRadioButton jrbFrc = new JRadioButton("French");
		ButtonGroup group = new ButtonGroup();
		group.add(jrbEng);
		group.add(jrbFrc);
		JButton done=new JButton("Done");
		jrbEng.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{
				MenuFrame mf= new MenuFrame("english");
			}
		});

		jrbFrc.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{
				MenuFrame mf= new MenuFrame("french");
			}
		});
		JPanel jplRadio = new JPanel();
		jplRadio.setLayout(new GridLayout(0, 1));
		jplRadio.add(jrbEng);
		jplRadio.add(jrbFrc);
		frame.add(jplRadio);
	}

}


