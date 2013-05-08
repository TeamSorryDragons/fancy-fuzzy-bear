import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * Unit tests for the Piece container class.
 * 
 * @author TeamSorryDragons Created Mar 22, 2013.
 */
public class PieceTest {

	@SuppressWarnings("javadoc")
	@Test
	public void test() {
		assertFalse(false);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void javaTest() {
		String hello = "Hello world";
		assertEquals(hello, "Hello world");
	}

	@SuppressWarnings("javadoc")
	@Test
	public void nullTest() {
		assertNotNull(new Piece());
		assertNotNull(new Piece(Piece.COLOR.red));
		assertNotNull(new Piece(Piece.COLOR.blue));
		assertNotNull(new Piece(Piece.COLOR.green));
		assertNotNull(new Piece(Piece.COLOR.yellow));
	}
	@SuppressWarnings("javadoc")
	@Test
	public void colorIsColorTest() {
		assertEquals(new Piece().col,Piece.COLOR.colorless);
	}
}
