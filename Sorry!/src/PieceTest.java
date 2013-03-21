import static org.junit.Assert.*;

import org.junit.Test;



public class PieceTest {

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
		assertNotNull(new Piece());
		assertNotNull(new Piece(Piece.COLOR.red));
		assertNotNull(new Piece(Piece.COLOR.blue));
		assertNotNull(new Piece(Piece.COLOR.green));
		assertNotNull(new Piece(Piece.COLOR.yellow));
	}
	@Test
	public void equalsTest(){
		assertFalse(new Piece(Piece.COLOR.blue) == new Piece(Piece.COLOR.blue));
		assertFalse(new Piece() == new Piece());
		Piece a = new Piece();
		assertTrue(a == a); //testing object memory construction.
		Piece b = new Piece(Piece.COLOR.blue);
		assertTrue(b == b); //testing object memory construction.
		Piece c = new Piece(Piece.COLOR.blue);
		assertFalse(b == c);
	}
}
