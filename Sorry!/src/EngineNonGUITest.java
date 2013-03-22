import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Class for testing the Engine, using Non-GUI methods.
 * 
 * @author TeamSorryDragons
 * 
 */
public class EngineNonGUITest {

	@Test
	public void test() {
		assertNotNull(new Engine(new BoardList()));
	}

	@Test
	public void testMoveOnePieceOnce() {
		BoardList board = new BoardList();
		Engine e = new Engine(board);
		e.newGame();
		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn4|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn4|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");

		try {
			e.move(1, e.pieces[0]);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsnr|rmn3|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn4|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");
	}

	public void testMoveOnePieceToEnd() {
		BoardList board = new BoardList();
		Engine e = new Engine(board);

		e.newGame();

		Piece pawn = e.pieces[5]; // that's a blue piece
		assertEquals(pawn.col, Piece.COLOR.blue);

		movePawn(1, pawn, e);

		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn4|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsnb|bmn3|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");

		movePawn(5, pawn, e);
		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn4|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn3|nn|nn|nn|nn"
						+ "|hbsnb|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");

		movePawn(8, pawn, e);
		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn4|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn3|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysnb|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");

		movePawn(25, pawn, e);
		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn4|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn4|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsnb|gsn|nn|nn|");

		movePawn(19, pawn, e);
		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn4|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsnb|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn3|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");

		movePawn(1, pawn, e);
		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn4|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsnb|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn3|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");

		movePawn(2, pawn, e);
		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn4|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsfb|bsf|bsf|bmn0|bsn|bsn|bmn3|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");

		movePawn(4, pawn, e);
		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn4|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn1|bsn|bsn|bmn3|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");

	}

	private void movePawn(int num, Piece p, Engine e) {
		try {
			e.move(num, p);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}
}
