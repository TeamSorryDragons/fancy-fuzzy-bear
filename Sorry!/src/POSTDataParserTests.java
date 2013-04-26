import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Test cases for parsing POST data by the SorryServer.
 * 
 * @author sturgedl. Created Apr 22, 2013.
 */
public class POSTDataParserTests {

	@Test
	public void testParseEmptyData() {
		SorryServer.POSTDataContainer target = SorryServer
				.parseServerInput(new String[0]);
		assertNotNull(target);
		assertFalse(target.isValidData);
	}

	@Test
	public void testParseDataWithoutEntry() {
		String[] input = new String[1];
		input[0] = "user";

		try {
			SorryServer.parseServerInput(input);
			fail();
		} catch (IllegalArgumentException e) {
			// expected exception
		}

		input[0] = "user=";
		try {
			SorryServer.parseServerInput(input);
			fail();
		} catch (IllegalArgumentException e) {
			// expected exception
		}

		input[0] = "flaming-hippos=";
		try {
			SorryServer.parseServerInput(input);
			fail();
		} catch (IllegalArgumentException e) {
			// expected exception
		}
	}

	@Test
	public void testParseUserNames() {
		String[] input = new String[1];
		input[0] = "user=Bob Barker";

		SorryServer.POSTDataContainer target = SorryServer
				.parseServerInput(input);
		assertNotNull(target);
		assertFalse(target.isValidData);
		assertEquals("Bob Barker", target.userName);

		input = new String[2];
		input[0] = "user=Étienne Gérard";
		input[1] = "user=Jimbo Hippo Flyer James";
		try {
			SorryServer.parseServerInput(input);
			fail();
		} catch (IllegalArgumentException e) {
			// expected exception
		}

		input[0] = "user=Étienne Gérard";
		input[1] = null;
		target = SorryServer.parseServerInput(input);
		assertEquals(target.userName, "Étienne Gérard");

		input[0] = null;
		input[1] = "user=Étienne Gérard";
		target = SorryServer.parseServerInput(input);
		assertEquals(target.userName, "Étienne Gérard");
	}

	@Test
	public void testParseFirstCoordinate() {
		String[] input = new String[1];
		input[0] = "coord1=(14, 8)";
		SorryServer.POSTDataContainer target = SorryServer
				.parseServerInput(input);
		assertNotNull(target);
		assertFalse(target.isValidData);
		assertEquals(new SorryFrame.Coordinate(14, 8), target.firstCoord);
		assertNull(target.secondCoord);
		assertNull(target.userName);

		input = new String[2];
		input[0] = "coord1=(7, 8)";
		input[1] = "coord1=(24, 9)";
		try {
			SorryServer.parseServerInput(input);
			fail();
		} catch (IllegalArgumentException e) {
			// expected exception
		}

	}

	@Test
	public void testParseSecondCoordinate() {
		String[] input = new String[1];
		input[0] = "coord2=(14, 8)";
		SorryServer.POSTDataContainer target = SorryServer
				.parseServerInput(input);
		assertNotNull(target);
		assertFalse(target.isValidData);
		assertEquals(new SorryFrame.Coordinate(14, 8), target.secondCoord);
		assertNull(target.firstCoord);
		assertNull(target.userName);

		input = new String[2];
		input[0] = "coord2=(7, 8)";
		input[1] = "coord2=(24, 9)";
		try {
			SorryServer.parseServerInput(input);
			fail();
		} catch (IllegalArgumentException e) {
			// expected exception
		}

	}

	@Test
	public void testParseCoordinatesHelper() {
		String coordinate = "(3, 4)";
		SorryFrame.Coordinate target = SorryServer
				.parseDatumToCoordinate(coordinate);
		assertEquals(target, new SorryFrame.Coordinate(3, 4));

		verifyFaultyInput("3,4");
		verifyFaultyInput("3,4,5");
		verifyFaultyInput("(3,4,5)");
		verifyFaultyInput("(a,5)");
		verifyFaultyInput("(101,5");
		verifyFaultyInput("78,5)");
		verifyFaultyInput("(79,b)");
		verifyFaultyInput("");

		coordinate = "(  3  ,   4  )";
		target = SorryServer.parseDatumToCoordinate(coordinate);
		assertEquals(target, new SorryFrame.Coordinate(3, 4));

	}

	@Test
	public void testPOSTDataContainerValidity() {
		SorryServer.POSTDataContainer target = new SorryServer.POSTDataContainer();
		assertFalse(target.checkValidity());

		target.userName = "Harry Potter";
		assertFalse(target.checkValidity());

		target.userName = null;
		target.firstCoord = new SorryFrame.Coordinate(0, 0);
		assertFalse(target.checkValidity());

		target.userName = null;
		target.firstCoord = null;
		target.secondCoord = new SorryFrame.Coordinate(0, 0);
		assertFalse(target.checkValidity());

		target.userName = "Buffalo";
		target.firstCoord = new SorryFrame.Coordinate(0, 0);
		target.secondCoord = null;
		assertFalse(target.checkValidity());

		target.userName = null;
		target.firstCoord = new SorryFrame.Coordinate(0, 0);
		target.secondCoord = new SorryFrame.Coordinate(0, 0);
		assertFalse(target.checkValidity());

		target.userName = "Whale Rider at large";
		target.firstCoord = null;
		target.secondCoord = new SorryFrame.Coordinate(0, 0);
		assertFalse(target.checkValidity());

		target.userName = "Whale Rider at large";
		target.firstCoord = new SorryFrame.Coordinate(0, 0);
		target.secondCoord = new SorryFrame.Coordinate(0, 0);
		assertTrue(target.checkValidity());
		
		target.firstCoord = null;
		target.secondCoord = null;
		target.action = SorryServer.PerformableAction.FORFEIT;
		assertTrue(target.checkValidity());
		
		target.action = SorryServer.PerformableAction.FINALIZE;
		assertTrue(target.checkValidity());
		
		target.userName = null;
		assertFalse(target.checkValidity());

	}
	
	@Test
	public void testParsePerformableActions(){
		assertEquals(SorryServer.stringToPerformable("  forfeit  "), SorryServer.PerformableAction.FORFEIT);
		assertEquals(SorryServer.stringToPerformable("finalize"), SorryServer.PerformableAction.FINALIZE);
		assertEquals(SorryServer.stringToPerformable("Han Solo Was Here"), SorryServer.PerformableAction.UNSPECIFIED);
		assertEquals(SorryServer.stringToPerformable(""), SorryServer.PerformableAction.UNSPECIFIED);
		assertEquals(SorryServer.stringToPerformable(" "), SorryServer.PerformableAction.UNSPECIFIED);
		
		String[] lines = new String[1];
		lines[0] = "desired-action=forfeit";
		
		SorryServer.parseServerInput(lines);
		
	}

	private void verifyFaultyInput(String test) {
		try {
			SorryServer.parseDatumToCoordinate(test);
			fail();
		} catch (IllegalArgumentException e) {
			// expected exception
		}
	}

}
