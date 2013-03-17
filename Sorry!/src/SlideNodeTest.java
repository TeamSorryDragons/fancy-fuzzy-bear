import static org.junit.Assert.*;

import org.junit.Test;

public class SlideNodeTest {

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
		assertNotNull(new SlideNode());
		assertNotNull(new SlideNode(new Node(), new Node()));
		assertNotNull(new SlideNode(Piece.COLOR.blue));
		assertNotNull(new SlideNode(new Node()));
		assertNotNull(new SlideNode(new Node(), new Node(), new Node(),
				Piece.COLOR.blue));
	}

	@Test
	public void testSetSafeNode() {
		SlideNode testNode = new SlideNode();
		// TODO implement this stuffs
	}

	@Test
	public void testGetSafeNode() {
		// TODO implement this stuffs
	}

	@Test
	public void testGetSetSlideColor() {
		SlideNode testNode = new SlideNode();
		testNode.setColor(Piece.COLOR.colorless);
		assertEquals(testNode.getColor(), Piece.COLOR.colorless);

		testNode.setColor(Piece.COLOR.red);
		assertEquals(testNode.getColor(), Piece.COLOR.red);

		testNode.setColor(Piece.COLOR.blue);
		assertEquals(testNode.getColor(), Piece.COLOR.blue);

		testNode.setColor(Piece.COLOR.green);
		assertEquals(testNode.getColor(), Piece.COLOR.green);

		testNode.setColor(Piece.COLOR.yellow);
		assertEquals(testNode.getColor(), Piece.COLOR.yellow);

		testNode = new SlideNode(Piece.COLOR.colorless);
		assertEquals(testNode.getColor(), Piece.COLOR.colorless);

		testNode = new SlideNode(Piece.COLOR.red);
		assertEquals(testNode.getColor(), Piece.COLOR.red);

		testNode = new SlideNode(Piece.COLOR.blue);
		assertEquals(testNode.getColor(), Piece.COLOR.blue);

		testNode = new SlideNode(Piece.COLOR.green);
		assertEquals(testNode.getColor(), Piece.COLOR.green);

		testNode = new SlideNode(Piece.COLOR.yellow);
		assertEquals(testNode.getColor(), Piece.COLOR.yellow);
	}

	@Test
	public void testToString() {
		SlideNode testNode = new SlideNode(Piece.COLOR.red);
		// check that the slide w/o a piece has the right colors, then check for
		// pieces
		assertEquals(testNode.toString(), "rsn|");

		Piece[] testPieces = new Piece[1];
		testPieces[0] = new Piece(Piece.COLOR.red);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "rsnr|");

		testPieces[0] = new Piece(Piece.COLOR.green);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "rsng|");

		testPieces[0] = new Piece(Piece.COLOR.blue);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "rsnb|");

		testPieces[0] = new Piece(Piece.COLOR.yellow);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "rsny|");

		// now working with a green slide
		testNode.setColor(Piece.COLOR.green);
		testNode.setPieces(new Piece[1]);
		assertEquals(testNode.toString(), "gsn|");

		testPieces[0] = new Piece(Piece.COLOR.red);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "gsnr|");

		testPieces[0] = new Piece(Piece.COLOR.green);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "gsng|");

		testPieces[0] = new Piece(Piece.COLOR.blue);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "gsnb|");

		testPieces[0] = new Piece(Piece.COLOR.yellow);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "gsny|");

		// now working with blue slide
		testNode.setColor(Piece.COLOR.blue);
		testNode.setPieces(new Piece[1]);
		assertEquals(testNode.toString(), "bsn|");

		testPieces[0] = new Piece(Piece.COLOR.red);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "bsnr|");

		testPieces[0] = new Piece(Piece.COLOR.green);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "bsng|");

		testPieces[0] = new Piece(Piece.COLOR.blue);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "bsnb|");

		testPieces[0] = new Piece(Piece.COLOR.yellow);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "bsny|");

		// YELLOW
		testNode.setColor(Piece.COLOR.yellow);
		testNode.setPieces(new Piece[1]);
		assertEquals(testNode.toString(), "ysn|");

		testPieces[0] = new Piece(Piece.COLOR.red);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "ysnr|");

		testPieces[0] = new Piece(Piece.COLOR.green);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "ysng|");

		testPieces[0] = new Piece(Piece.COLOR.blue);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "ysnb|");

		testPieces[0] = new Piece(Piece.COLOR.yellow);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "ysny|");
		
		// do it all over again with a head slide
		testNode = new SlideNode(Piece.COLOR.red);
		testNode.head = true;
		// check that the slide w/o a piece has the right colors, then check for
		// pieces
		assertEquals(testNode.toString(), "hrsn|");

		testPieces = new Piece[1];
		testPieces[0] = new Piece(Piece.COLOR.red);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hrsnr|");

		testPieces[0] = new Piece(Piece.COLOR.green);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hrsng|");

		testPieces[0] = new Piece(Piece.COLOR.blue);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hrsnb|");

		testPieces[0] = new Piece(Piece.COLOR.yellow);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hrsny|");

		// now working with a green slide
		testNode.setColor(Piece.COLOR.green);
		testNode.setPieces(new Piece[1]);
		assertEquals(testNode.toString(), "hgsn|");

		testPieces[0] = new Piece(Piece.COLOR.red);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hgsnr|");

		testPieces[0] = new Piece(Piece.COLOR.green);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hgsng|");

		testPieces[0] = new Piece(Piece.COLOR.blue);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hgsnb|");

		testPieces[0] = new Piece(Piece.COLOR.yellow);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hgsny|");

		// now working with blue slide
		testNode.setColor(Piece.COLOR.blue);
		testNode.setPieces(new Piece[1]);
		assertEquals(testNode.toString(), "hbsn|");

		testPieces[0] = new Piece(Piece.COLOR.red);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hbsnr|");

		testPieces[0] = new Piece(Piece.COLOR.green);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hbsng|");

		testPieces[0] = new Piece(Piece.COLOR.blue);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hbsnb|");

		testPieces[0] = new Piece(Piece.COLOR.yellow);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hbsny|");

		// YELLOW
		testNode.setColor(Piece.COLOR.yellow);
		testNode.setPieces(new Piece[1]);
		assertEquals(testNode.toString(), "hysn|");

		testPieces[0] = new Piece(Piece.COLOR.red);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hysnr|");

		testPieces[0] = new Piece(Piece.COLOR.green);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hysng|");

		testPieces[0] = new Piece(Piece.COLOR.blue);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hysnb|");

		testPieces[0] = new Piece(Piece.COLOR.yellow);
		testNode.setPieces(testPieces);
		assertEquals(testNode.toString(), "hysny|");
	}

}
