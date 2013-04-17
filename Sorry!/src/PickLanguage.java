import java.awt.Graphics2D;
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
	private MenuFrame mf;
	public PickLanguage(){
		super("Pick the language/ Choisissez la langue");
		JPanel panel= new JPanel();
		Graphics2D g = (Graphics2D) panel.getGraphics();
		g.drawString("Pick the language/ Choisissez la langue", 10,20);
		JRadioButton jrbEng = new JRadioButton("English");
		JRadioButton jrbFrc = new JRadioButton("Français");
		ButtonGroup group = new ButtonGroup();
		group.add(jrbEng);
		group.add(jrbFrc);
		JButton done=new JButton("Done");
		jrbEng.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{
				mf= new MenuFrame("english");
				setVisible(false);
				mf.setVisible(true);
			}
		});

		jrbFrc.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{
				mf= new MenuFrame("french");
				setVisible(false);
				mf.setVisible(true);
			}
		});
		JPanel jplRadio = new JPanel();
		jplRadio.setLayout(new GridLayout(0, 1));
		jplRadio.add(jrbEng);
		jplRadio.add(jrbFrc);
		this.add(jplRadio);
		this.setSize(200,200);
	}

}


