import static org.junit.Assert.*;

import org.junit.Test;


public class BoardListTests {

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
		assertNotNull(new BoardList());
	}
	@Test
	public void constructorTest() {
		BoardList board = new BoardList();
		assertEquals(
				"nn|rsn|rsn|rsf|rsf|rsf|rsf|rsf|rhn|rsn|rsn|rst|nn|nn|nn|nn|rsn|rsn|rsn" +
				"|rsn|rsn|nn|nn|bsn|bsn|bsf|bsf|bsf|bsf|bsf|bhn|bsn|bsn|bst|nn|nn|nn|nn" +
				"|bsn|bsn|bsn|bsn|bsn|nn|nn|ysn|ysn|ysf|ysf|ysf|ysf|ysf|yhn|ysn|ysn|yst" +
				"|nn|nn|nn|nn|ysn|ysn|ysn|ysn|ysn|nn|nn|gsn|gsn|gsf|gsf|gsf|gsf|gsf|ghn" +
				"|gsn|gsn|gst|nn|nn|nn|nn|gsn|gsn|gsn|gsn|gsn|nn|",
				board.toString());
	}
}
