import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Date;

import org.junit.Test;


/**
 * Class to unit test the color picker class.
 *
 * @author sturgedl.
 *         Created May 16, 2013.
 */
public class ColorPickerTests {

	@Test
	public void test(){
		assertNotNull(new ColorPicker(new Date()));
	}
	
	@Test
	public void testSunday(){
		ColorPicker target = new ColorPicker(new Date(0, 0, 0));
		assertEquals(target.getTodaysColor(), Color.CYAN);
	}
	
	@Test
	public void testMonday(){
		ColorPicker target = new ColorPicker(new Date(0, 0, 1));
		assertEquals(target.getTodaysColor(), Color.GREEN);
	}
	
	@Test
	public void testTuesday(){
		ColorPicker target = new ColorPicker(new Date(0, 0, 2));
		assertEquals(target.getTodaysColor(), Color.GRAY);
	}
	
	@Test
	public void testWednesday(){
		ColorPicker target = new ColorPicker(new Date(0, 0, 3));
		assertEquals(target.getTodaysColor(), Color.ORANGE);
	}
	
	@Test
	public void testThursday(){
		ColorPicker target = new ColorPicker(new Date(0, 0, 4));
		assertEquals(target.getTodaysColor(), Color.BLUE);
	}
	
	@Test
	public void testFriday(){
		ColorPicker target = new ColorPicker(new Date(0, 0, 5));
		assertEquals(target.getTodaysColor(), Color.MAGENTA);
	}
	
	@Test
	public void testSaturday(){
		ColorPicker target = new ColorPicker(new Date(0, 0, 6));
		assertEquals(target.getTodaysColor(), Color.RED);
	}
}
