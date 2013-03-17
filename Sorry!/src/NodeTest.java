import static org.junit.Assert.*;

import org.junit.Test;

public class NodeTest {

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
		assertNotNull(new Node());
		assertNotNull(new Node(new Node(), new Node()));
	}

	@Test
	public void nextTest() {
		Node head = new Node();
		head.setNext(new Node());
		assertNotNull(head.getNext());
	}

	@Test
	public void prevTest() {
		Node head = new Node();
		head.setPrevious(new Node());
		assertNotNull(head.getPrevious());
	}

	@Test
	public void pieceTest() {
		Node head = new Node();
		Piece[] test = new Piece[1];
		test[0] = new Piece(Piece.COLOR.red);
		head.setPieces(test);
		assertArrayEquals(head.getPieces(), test);
		test[0] = new Piece(Piece.COLOR.green);
		head.setPieces(test);
		assertArrayEquals(head.getPieces(), test);
		test[0] = new Piece(Piece.COLOR.blue);
		head.setPieces(test);
		assertArrayEquals(head.getPieces(), test);
		test[0] = new Piece(Piece.COLOR.yellow);
		head.setPieces(test);
		assertArrayEquals(head.getPieces(), test);
	}

	@Test
	public void ExceptionTest() {
		try {
			Node head = new Node();
			Piece[] test = new Piece[2];
			test[0] = new Piece();
			test[1] = new Piece();
			head.setPieces(test);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
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
	public void toStringTest() {
		Node head = new Node();
		Piece[] test = new Piece[1];
		test[0] = new Piece(Piece.COLOR.red);
		head.setPieces(test);
		assertEquals(head.toString(), "nnr|");
		test[0] = new Piece(Piece.COLOR.blue);
		head.setPieces(test);
		assertEquals(head.toString(), "nnb|");
		test[0] = new Piece(Piece.COLOR.green);
		head.setPieces(test);
		assertEquals(head.toString(), "nng|");
		test[0] = new Piece(Piece.COLOR.yellow);
		head.setPieces(test);
		assertEquals(head.toString(), "nny|");
		test[0] = null;
		head.setPieces(test);
		assertEquals(head.toString(), "nn|");
		test[0] = new Piece();
		head.setPieces(test);
		assertEquals(head.toString(), "nnITBROKEWILLIS|");
	}

	@Test
	public void testSafeNodesToString() {
		Node head = new Node();
		head.setColor(Piece.COLOR.red);
		
		assertEquals(head.toString(), "rsf|");
		
		Piece[] test = new Piece[1];
		test[0] = new Piece(Piece.COLOR.red);
		head.setPieces(test);
		assertEquals(head.toString(), "rsfr|");
		test[0] = new Piece(Piece.COLOR.blue);
		head.setPieces(test);
		assertEquals(head.toString(), "rsfb|");
		test[0] = new Piece(Piece.COLOR.green);
		head.setPieces(test);
		assertEquals(head.toString(), "rsfg|");
		test[0] = new Piece(Piece.COLOR.yellow);
		head.setPieces(test);
		assertEquals(head.toString(), "rsfy|");
		test[0] = new Piece();
		head.setPieces(test);
		assertEquals(head.toString(), "rsfITBROKEWILLIS|");
		
		head = new Node();
		head.setColor(Piece.COLOR.blue);
		
		assertEquals(head.toString(), "bsf|");
		
		test = new Piece[1];
		test[0] = new Piece(Piece.COLOR.red);
		head.setPieces(test);
		assertEquals(head.toString(), "bsfr|");
		test[0] = new Piece(Piece.COLOR.blue);
		head.setPieces(test);
		assertEquals(head.toString(), "bsfb|");
		test[0] = new Piece(Piece.COLOR.green);
		head.setPieces(test);
		assertEquals(head.toString(), "bsfg|");
		test[0] = new Piece(Piece.COLOR.yellow);
		head.setPieces(test);
		assertEquals(head.toString(), "bsfy|");
		test[0] = new Piece();
		head.setPieces(test);
		assertEquals(head.toString(), "bsfITBROKEWILLIS|");
		
		head = new Node();
		head.setColor(Piece.COLOR.yellow);
		
		assertEquals(head.toString(), "ysf|");
		
		test = new Piece[1];
		test[0] = new Piece(Piece.COLOR.red);
		head.setPieces(test);
		assertEquals(head.toString(), "ysfr|");
		test[0] = new Piece(Piece.COLOR.blue);
		head.setPieces(test);
		assertEquals(head.toString(), "ysfb|");
		test[0] = new Piece(Piece.COLOR.green);
		head.setPieces(test);
		assertEquals(head.toString(), "ysfg|");
		test[0] = new Piece(Piece.COLOR.yellow);
		head.setPieces(test);
		assertEquals(head.toString(), "ysfy|");
		test[0] = new Piece();
		head.setPieces(test);
		assertEquals(head.toString(), "ysfITBROKEWILLIS|");
		
		head = new Node();
		head.setColor(Piece.COLOR.green);
		
		assertEquals(head.toString(), "gsf|");
		
		test = new Piece[1];
		test[0] = new Piece(Piece.COLOR.red);
		head.setPieces(test);
		assertEquals(head.toString(), "gsfr|");
		test[0] = new Piece(Piece.COLOR.blue);
		head.setPieces(test);
		assertEquals(head.toString(), "gsfb|");
		test[0] = new Piece(Piece.COLOR.green);
		head.setPieces(test);
		assertEquals(head.toString(), "gsfg|");
		test[0] = new Piece(Piece.COLOR.yellow);
		head.setPieces(test);
		assertEquals(head.toString(), "gsfy|");
		test[0] = new Piece();
		head.setPieces(test);
		assertEquals(head.toString(), "gsfITBROKEWILLIS|");
		
		
	}
}
