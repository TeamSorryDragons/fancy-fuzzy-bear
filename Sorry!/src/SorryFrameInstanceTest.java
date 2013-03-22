import static org.junit.Assert.*;

import org.junit.Test;


public class SorryFrameInstanceTest {

	@Test
	public void test() {
		try{
			BoardList game = new BoardList();
			Engine engine = new Engine(game);
			engine.newGame();
			SorryFrame frame = new SorryFrame(game, engine);
			assertTrue(true);
		}
		catch(Exception e){
			fail();
		}
	}

}
