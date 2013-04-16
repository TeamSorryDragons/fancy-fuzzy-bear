import static org.junit.Assert.*;

import org.junit.Test;

public class MultiNodeTest {

	@Test
	public void test() {
		assertFalse(false);
	}

	@Test
	public void javaTest() {
		String hello = "Hello world";
		assertEquals(hello, "Hello world");
	}

	@Test
	public void nullTest() {
		assertNotNull(new MultiNode());
		assertNotNull(new MultiNode(new Node(), new Node(), Piece.COLOR.red));
		assertNotNull(new MultiNode(new Node(), new Node(), new Piece[4]));
	}

	@Test
	public void testSetBadPieceNumber() {
		MultiNode test = new MultiNode();
		try {
			test.setPieces(new Piece[5]);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		try {
			test.setPieces(new Piece[3]);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

	}

	@Test
	public void pieceSetGetTest() {
		MultiNode test = new MultiNode();
		Piece[] pieces = new Piece[4];

		test.setPieces(pieces);

		assertArrayEquals(test.getPieces(), pieces);

		for (int i = 0; i < 4; i++)
			pieces[i] = new Piece(Piece.COLOR.green);

		assertArrayEquals(test.getPieces(), pieces);

		assertEquals(test.getPieces()[2].col, Piece.COLOR.green);
	}

	@Test
	public void testAddPieces() {
		MultiNode test = new MultiNode();
		Piece[] pieces = new Piece[4];

		test.setColor(Piece.COLOR.blue);

		test.setPieces(pieces);
		test.addPieceToPieces(new Piece(Piece.COLOR.blue));
		assertEquals(test.getPieces()[0].col, Piece.COLOR.blue);

		test.addPieceToPieces(new Piece(Piece.COLOR.blue));
		assertEquals(test.getPieces()[1].col, Piece.COLOR.blue);
		test.addPieceToPieces(new Piece(Piece.COLOR.blue));
		test.addPieceToPieces(new Piece(Piece.COLOR.blue));
		try {
			test.addPieceToPieces(new Piece(Piece.COLOR.blue));
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}

		try {
			test.addPieceToPieces(new Piece(Piece.COLOR.yellow));
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		pieces = new Piece[4];
		pieces[0] = new Piece(Piece.COLOR.green);
		try {
			test.setPieces(pieces);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testRemovePieces() {
		MultiNode test = new MultiNode();
		test.setColor(Piece.COLOR.red);
		Piece[] pieces = new Piece[4];
		Piece p = new Piece(Piece.COLOR.red);

		test.setPieces(pieces);
		test.addPieceToPieces(p);

		test.removePieceFromPieces(p);
		assertNull(test.getPieces()[0]);

		test.addPieceToPieces(p);
		test.addPieceToPieces(p);
		test.removePieceFromPieces(p);
		test.removePieceFromPieces(new Piece(Piece.COLOR.green));
		assertEquals(test.getPieces()[0], p);
		assertNull(test.getPieces()[1]);
	}

	@Test
	public void testToString() {
		MultiNode test = new MultiNode();

		String[] prefixes = { "r", "b", "g", "y" };
		Piece.COLOR[] colors = { Piece.COLOR.red, Piece.COLOR.blue,
				Piece.COLOR.green, Piece.COLOR.yellow };

		for (int i = 0; i < prefixes.length; i++) {
			test.setColor(colors[i]);
			for (int k = 0; k <= 4; k++) {
				String tests = prefixes[i] + "mn" + k + "|";
				assertEquals(tests, test.toString());
				if (k != 4) {
					test.addPieceToPieces(new Piece(colors[i]));
				}
			}
			test = new MultiNode();
		}
	}

	@Test
	public void testHasPiece() {
		MultiNode test = new MultiNode();
		assertFalse(test.hasPiece());
		Piece test2 = new Piece();
		test.addPieceToPieces(test2);
		assertTrue(test.hasPiece());
	}

	@Test
	public void testFirstPiece() {
		MultiNode test = new MultiNode();
		assertNull(test.firstPiece());
		Piece[] test2 = { null, null, new Piece(), null };
		test.setPieces(test2);
		assertEquals(test.firstPiece(), test2[2]);
	}

	@Test
	public void testExceptions() {
		MultiNode test = new MultiNode();
		MultiNode test2 = new MultiNode();
		test.setPrevious(test2);
		try {
			test.move(1, new Piece(Piece.COLOR.red));
		} catch (InvalidMoveException e) {
		}
	}
	
	@Test
	public void testCountBack(){
		MultiNode test = new MultiNode();
		MultiNode test2 = new MultiNode();
		test.setPrevious(test2);
		assertEquals(1,test.countBack(test2));
		assertEquals(0,test.countBack(test));
		test.setPrevious(null);
		try{
			test.countBack(test2);
		}catch(Exception e){}
	}
	
	@Test
	public void testNumberOfPieces(){
		MultiNode test = new MultiNode();
		assertEquals(0,test.numberOfPieces());
		test.addPieceToPieces(new Piece());
		assertEquals(1,test.numberOfPieces());
		test.addPieceToPieces(new Piece());
		assertEquals(2,test.numberOfPieces());
		test.addPieceToPieces(new Piece());
		assertEquals(3,test.numberOfPieces());
		test.addPieceToPieces(new Piece());
		assertEquals(4,test.numberOfPieces());
		
	}
}
