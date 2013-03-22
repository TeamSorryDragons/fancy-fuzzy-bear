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
		assertNotNull(new MultiNode(new Node(), new Node(),Piece.COLOR.red));
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

		try {
			test.addPieceToPieces(new Piece(Piece.COLOR.yellow));
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		try {
			pieces = new Piece[4];
			pieces[0] = new Piece(Piece.COLOR.green);
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
}
