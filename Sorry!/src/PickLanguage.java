import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


@SuppressWarnings("serial")
public class PickLanguage extends JFrame{
	private MenuFrame mf;
	public PickLanguage(){
		super("Pick the language/ Choisissez la langue");
		JPanel panel= new JPanel();
		JLabel title = new JLabel("Pick the language/ Choisissez la langue",JLabel.CENTER);
		panel.add(title);
		JRadioButton jrbEng = new JRadioButton("English");
		JRadioButton jrbFrc = new JRadioButton("Français");
		ButtonGroup group = new ButtonGroup();
		group.add(jrbEng);
		group.add(jrbFrc);
		jrbEng.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{
				dispose();
				mf= new MenuFrame("english");;
				mf.setVisible(true);
			}
		});

		jrbFrc.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{
				dispose();
				mf= new MenuFrame("french");
				mf.setVisible(true);
			}
		});
		JPanel jplRadio = new JPanel();
		jplRadio.setLayout(new GridLayout(0,1));
		jplRadio.add(title);
		jplRadio.add(jrbEng);
		jplRadio.add(jrbFrc);
		this.add(jplRadio);
		this.setSize(350,200);
	}

}


