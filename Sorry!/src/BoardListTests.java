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
	public void getCornerPointerTest(){
		BoardList board = new BoardList();
		for(int i=0;i<4; i++)
			assertEquals(board.getCornerPointers()[i].toString(), "nn|");
	}
	@Test
	public void getHomePointerTest(){
		BoardList board = new BoardList();
		assertEquals(board.getHomePointers()[0].toString(), "rmn0|");
		assertEquals(board.getHomePointers()[1].toString(), "bmn0|");
		assertEquals(board.getHomePointers()[2].toString(), "ymn0|");
		assertEquals(board.getHomePointers()[3].toString(), "gmn0|");
	}
	@Test
	public void getStartPointerTest(){
		BoardList board = new BoardList();
		assertEquals(board.getStartPointers()[0].toString(), "rmn0|");
		assertEquals(board.getStartPointers()[1].toString(), "bmn0|");
		assertEquals(board.getStartPointers()[2].toString(), "ymn0|");
		assertEquals(board.getStartPointers()[3].toString(), "gmn0|");
	}
	@Test
	public void newGameTest() {
		BoardList board = new BoardList();
		board.newGame();
		//assertEquals(board.toString(),"nn|");
		assertEquals(
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn4|nn|nn|nn|nn|hrsn|rsn|rsn" +
				"|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn4|nn|nn|nn|nn" +
				"|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4" +
				"|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0" +
				"|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|",
				board.toString());
	}
	@Test
	public void constructorTest() {
		BoardList board = new BoardList();
		//assertEquals(board.toString(),"nn|");
		assertEquals(
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn0|nn|nn|nn|nn|hrsn|rsn|rsn" +
				"|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn0|nn|nn|nn|nn" +
				"|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn0" +
				"|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0" +
				"|gsn|gsn|gmn0|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|",
				board.toString());
	}
}
