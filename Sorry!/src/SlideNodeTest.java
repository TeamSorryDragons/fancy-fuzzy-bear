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
		assertNotNull(new SlideNode(new Node(), new Node()));
		assertNotNull(new SlideNode(new Node(), new Node(), new Node(),
				Piece.COLOR.blue));
		SlideNode test = new SlideNode();
		assertTrue(test instanceof SlideNode);
		assertFalse(null instanceof SlideNode);
	}

	@Test
	public void testSetSafeNode() {
		SlideNode testNode = new SlideNode();
		testNode.setSafeNode(new Node());
		assertNotNull(testNode.getSafeNode());
	}

	@Test
	public void testGetSafeNode() {
		SlideNode test = new SlideNode();
		Node safe = new Node();
		safe.setColor(Piece.COLOR.yellow);
		test.setSafeNode(safe);

		assertEquals(test.getSafeNode(), safe);
		assertEquals(test.getSafeNode().getColor(), Piece.COLOR.yellow);
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

	@Test
	public void testCountTo() {
		SlideNode test = new SlideNode(Piece.COLOR.red);
		SlideNode test2 = new SlideNode(test, null, null, Piece.COLOR.red);
		MultiNode test6 = new MultiNode(test, null, Piece.COLOR.red);
		test.setSafeNode(test6);
		Node test3 = new Node(null, test2);
		Node test4 = new Node(null, test3);
		test3.setNext(test4);
		Node test5 = new Node(null, test4);
		test4.setNext(test5);
		test.setPrevious(test2);
		test2.setSafeNode(test3);
		assertEquals(0, test2.countTo(test2));
		assertEquals(1, test2.countTo(test));
		assertEquals(1, test2.countTo(test3));
		assertEquals(3, test2.countTo(test5));
		assertEquals(1, test.countTo(test6));
	}

	@Test
	public void testCountBack() {
		SlideNode test = new SlideNode(Piece.COLOR.red);
		SlideNode test2 = new SlideNode(test, null, null, Piece.COLOR.red);
		MultiNode test6 = new MultiNode(test, null, Piece.COLOR.red);
		test.setPrevious(test2);
		test2.setSafeNode(test6);
		test.setSafeNode(new Node(new Node(), test));
		test.getSafeNode().getNext().setPrevious(test.getSafeNode());
		assertEquals(test.countBack(test2), 1);
		assertEquals(test.getSafeNode().getNext().countBack(test2), 3);
		assertEquals(
				test.getSafeNode().getNext().countBack(test.getSafeNode()), 1);
		try {
			test.countBack(test.getSafeNode());
		} catch (Exception e) {
		}
		try{
			test2.countBack(test6);
		} catch(Exception e) {
		}
	}
	
	@Test
	public void testCanReceivePawn(){
		SlideNode target = new SlideNode();
		target.setColor(Piece.COLOR.yellow);
		assertTrue(target.canReceivePiece(Piece.COLOR.green));
		assertTrue(target.canReceivePiece(Piece.COLOR.blue));
		assertTrue(target.canReceivePiece(Piece.COLOR.yellow));
		assertTrue(target.canReceivePiece(Piece.COLOR.red));
		
		target.setColor(Piece.COLOR.colorless);
		assertTrue(target.canReceivePiece(Piece.COLOR.green));
		assertTrue(target.canReceivePiece(Piece.COLOR.blue));
		assertTrue(target.canReceivePiece(Piece.COLOR.yellow));
		assertTrue(target.canReceivePiece(Piece.COLOR.red));
	}
}
