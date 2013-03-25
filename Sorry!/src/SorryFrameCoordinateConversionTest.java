import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the screen coordinate to grid coordinate converter.
 * 
 * @author sturgedl. Created Mar 24, 2013.
 */
public class SorryFrameCoordinateConversionTest {

	@SuppressWarnings("javadoc")
	@Test
	public void testJUnit() {
		assertTrue(true);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testCoordinateConstructor() {
		assertNotNull(new SorryFrame.Coordinate(0, 0));

		SorryFrame.Coordinate c = new SorryFrame.Coordinate(0, 0);
		assertEquals(c.getX(), 0);
		assertEquals(c.getY(), 0);

		c.setX(5);
		assertEquals(c.getX(), 5);

		c.setY(7);
		assertEquals(c.getY(), 7);
		assertEquals(c.getX(), 5);

		SorryFrame.Coordinate c2 = new SorryFrame.Coordinate(5, 7);
		assertEquals(c, c2);

	}

	@SuppressWarnings("javadoc")
	@Test
	public void testTopLeftCorner() {
		checkCoordinates(new SorryFrame.Coordinate(0, 0),
				SorryFrame.convertClickToCoordinate(0, 0));
		checkCoordinates(new SorryFrame.Coordinate(0, 0),
				SorryFrame.convertClickToCoordinate(62, 62));
		checkCoordinates(new SorryFrame.Coordinate(0, 0),
				SorryFrame.convertClickToCoordinate(62.499999, 62.4999999));
		checkCoordinates(new SorryFrame.Coordinate(0, 0),
				SorryFrame.convertClickToCoordinate(62.4999999, 0));
		checkCoordinates(new SorryFrame.Coordinate(0, 0),
				SorryFrame.convertClickToCoordinate(0, 62.499999));
		checkCoordinates(new SorryFrame.Coordinate(0, 0),
				SorryFrame.convertClickToCoordinate(30, 30));
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testBlueSide() {
		checkCoordinates(new SorryFrame.Coordinate(0, 1),
				SorryFrame.convertClickToCoordinate(0, 62.5));
		checkCoordinates(new SorryFrame.Coordinate(0, 1),
				SorryFrame.convertClickToCoordinate(62.4999, 92.5));
		checkCoordinates(new SorryFrame.Coordinate(0, 1),
				SorryFrame.convertClickToCoordinate(30, 124.99999999));
		checkCoordinates(new SorryFrame.Coordinate(0, 7),
				SorryFrame.convertClickToCoordinate(0, 437.5));
		checkCoordinates(new SorryFrame.Coordinate(0, 7),
				SorryFrame.convertClickToCoordinate(62.4999, 499.9999999));
		checkCoordinates(new SorryFrame.Coordinate(0, 11),
				SorryFrame.convertClickToCoordinate(0, 687.5));
		checkCoordinates(new SorryFrame.Coordinate(0, 11),
				SorryFrame.convertClickToCoordinate(62.49999, 745));
		checkCoordinates(new SorryFrame.Coordinate(1, 11),
				SorryFrame.convertClickToCoordinate(62.5, 687.5));
		checkCoordinates(new SorryFrame.Coordinate(3, 11),
				SorryFrame.convertClickToCoordinate(190, 720));
		checkCoordinates(new SorryFrame.Coordinate(0, 15),
				SorryFrame.convertClickToCoordinate(30, 937.5));
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testRedSide() {
		checkCoordinates(new SorryFrame.Coordinate(15, 15),
				SorryFrame.convertClickToCoordinate(937.5, 937.5));
		checkCoordinates(new SorryFrame.Coordinate(14, 14),
				SorryFrame.convertClickToCoordinate(937.49999999, 937.49999999));
		checkCoordinates(new SorryFrame.Coordinate(13, 9),
				SorryFrame.convertClickToCoordinate(845, 614));
		checkCoordinates(new SorryFrame.Coordinate(11, 14),
				SorryFrame.convertClickToCoordinate(714, 919));
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGreenSide() {
		checkCoordinates(new SorryFrame.Coordinate(15, 0),
				SorryFrame.convertClickToCoordinate(942, 27));
		checkCoordinates(new SorryFrame.Coordinate(15, 0),
				SorryFrame.convertClickToCoordinate(942, 0));
		checkCoordinates(new SorryFrame.Coordinate(15, 0),
				SorryFrame.convertClickToCoordinate(999.99999, 27));

	}

	private static void checkCoordinates(SorryFrame.Coordinate c1,
			SorryFrame.Coordinate c2) {
		assertEquals(c1.getX(), c2.getX());
		assertEquals(c1.getY(), c2.getY());

	}

}
