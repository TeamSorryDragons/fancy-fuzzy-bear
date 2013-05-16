import java.awt.Color;
import java.util.Date;
import java.util.Random;

/**
 * Pick a color to make the background of Sorry!.
 * 
 * @author sturgedl. Created May 16, 2013.
 */
public class ColorPicker {

	private Date today;

	public ColorPicker(Date date) {
		this.today = date;
	}

	public Color getTodaysColor() {
		Color ret = Color.PINK;
		switch (this.today.getDay()) {
		case 0:
			ret = Color.CYAN;
			break;
		case 1:
			ret = Color.GREEN;
			break;
		case 2:
			ret = Color.GRAY;
			break;
		case 3:
			ret = Color.ORANGE;
			break;
		case 4:
			ret = Color.BLUE;
			break;
		case 5:
			ret = Color.MAGENTA;
			break;
		case 6:
			ret = Color.RED;
			break;
		}
		return ret;
	}

	public Color getRandomColor() {
		Random rand = new Random();
		Color c = new Color(rand.nextInt(255), rand.nextInt(255),
				rand.nextInt(255));
		return c;
	}
}
