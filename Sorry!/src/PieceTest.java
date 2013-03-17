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

}
