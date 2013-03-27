import static org.junit.Assert.*;

import org.junit.Test;


/**
 * TODO Put here a description of what this class does.
 *
 * @author sturgedl.
 *         Created Mar 27, 2013.
 */
public class PlayerTest {

	@Test
	public void test() {
		assertNotNull(new Player(Piece.COLOR.green, "Dave"));
	}
	
	@Test
	public void testPlayerContents(){
		Player p = new Player(Piece.COLOR.yellow, "James Cameron");
		assertEquals(p.getColor(), Piece.COLOR.yellow);
		assertEquals(p.getName(), "James Cameron");
		assertFalse(p.getActive());
	}
	
	@Test
	public void testChangeContents(){
		Player p = new Player(Piece.COLOR.green, "Sherlock Holmes");
		p.setColor(Piece.COLOR.red);
		assertEquals(p.getColor(), Piece.COLOR.red);
		
		p.setName("Charles Dickens");
		assertEquals(p.getName(), "Charles Dickens");
		
		p.setActive(true);
		assertTrue(p.getActive());
	}

}
