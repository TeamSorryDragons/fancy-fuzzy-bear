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
		assertEquals(hello,"Hello world");
	}
	@Test
	public void nullTest() {
		assertNotNull(new SlideNode());
		assertNotNull(new SlideNode(new Node(), new Node()));
		assertNotNull(new SlideNode(Piece.COLOR.blue));
		assertNotNull(new SlideNode(new Node()));
		assertNotNull(new SlideNode(new Node(), new Node(), new Node(), Piece.COLOR.blue));
	}

}
