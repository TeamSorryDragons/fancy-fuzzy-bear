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
		assertEquals(hello,"Hello world");
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
		assertArrayEquals(head.getPieces(),test);
		test[0] = new Piece(Piece.COLOR.green);
		head.setPieces(test);
		assertArrayEquals(head.getPieces(),test);
		test[0] = new Piece(Piece.COLOR.blue);
		head.setPieces(test);
		assertArrayEquals(head.getPieces(),test);
		test[0] = new Piece(Piece.COLOR.yellow);
		head.setPieces(test);
		assertArrayEquals(head.getPieces(),test);
	}
	@Test
	public void ExceptionTest() {
		try{
			Node head = new Node();
			Piece[] test = new Piece[2];
			test[0] = new Piece();
			test[1] = new Piece();
			head.setPieces(test);
			fail();
		}
		catch (Exception e){
			assertTrue(true);
		}
	}
	@Test
	public void toStringTest() {
		Node head = new Node();
		Piece[] test = new Piece[1];
		test[0] = new Piece(Piece.COLOR.red);
		head.setPieces(test);
		assertEquals(head.toString(),"nnpr|");
		test[0] = new Piece(Piece.COLOR.blue);
		head.setPieces(test);
		assertEquals(head.toString(),"nnpb|");
		test[0] = new Piece(Piece.COLOR.green);
		head.setPieces(test);
		assertEquals(head.toString(),"nnpg|");
		test[0] = new Piece(Piece.COLOR.yellow);
		head.setPieces(test);
		assertEquals(head.toString(),"nnpy|");
		test[0] = null;
		head.setPieces(test);
		assertEquals(head.toString(),"nn|");
		test[0] = new Piece();
		head.setPieces(test);
		assertEquals(head.toString(),"nnBROKEN|");
	}
}
