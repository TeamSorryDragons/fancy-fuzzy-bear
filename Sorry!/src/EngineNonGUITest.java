import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Class for testing the Engine, using Non-GUI methods.
 * 
 * @author TeamSorryDragons
 * 
 */
public class EngineNonGUITest {

	@SuppressWarnings("javadoc")
	@Test
	public void test() {
		assertNotNull(new Engine(new BoardList()));
	}

	@SuppressWarnings("javadoc")
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
			e.move(1, e.pieces[0],e.board.getStartPointers()[0]);
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

	@SuppressWarnings("javadoc")
	@Test
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
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn3|nn|nn|nn|nn"
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
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsfb|bsf|bsf|bsf|bmn0|bsn|bsn|bmn3|nn|nn|nn|nn"
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

	@SuppressWarnings("javadoc")
	@Test
	public void toStartTest() {
		BoardList board = new BoardList();
		Engine e = new Engine(board);
		e.newGame();
		movePawn(1, e.pieces[7], e);
		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn4|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsnb|bmn3|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");
		movePawn(16, e.pieces[1], e);
		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn3|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsnr|bmn4|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");
		movePawn(1, e.pieces[9], e);
		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn3|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsnr|bmn4|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysny|ymn3"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");
		movePawn(15, e.pieces[1], e);
		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn3|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn4|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysnr|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");
		movePawn(1, e.pieces[13], e);

		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn3|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn4|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysnr|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsng|gmn3|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");
		movePawn(15, e.pieces[1], e);

		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn3|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn4|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsnr|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");
		movePawn(1, e.pieces[12], e);

		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn4|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn4|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsng|gmn3|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");
	}

	@Test
	public void testSlideMoves() {
		BoardList board = new BoardList();
		Engine e = new Engine(board);
		e.newGame();
		movePawn(13, e.pieces[0], e);
		assertEquals(
				board.toString(),
				"hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn3|nn|nn|nn|nn|hrsn|rsn|rsn"
						+ "|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsnr|bmn4|nn|nn|nn|nn"
						+ "|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4"
						+ "|nn|nn|nn|nn|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0"
						+ "|gsn|gsn|gmn4|nn|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|");
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testCoordinateToNodeCorners() {
		BoardList board = new BoardList();
		Engine e = new Engine(board);
		e.newGame();

		// assertEquals(e.convertCoordToNode(new SorryFrame.Coordinate(0, 0)),
		// board.getCornerPointers()[2]);

	}

	@SuppressWarnings("javadoc")
	@Test
	public void testCoordinateToIntCorners() {
		assertEquals(Engine.getNodePosition(new SorryFrame.Coordinate(15, 15)),
				0);
		assertEquals(Engine.getNodePosition(new SorryFrame.Coordinate(0, 15)),
				22);
		assertEquals(Engine.getNodePosition(new SorryFrame.Coordinate(0, 0)),
				44);
		assertEquals(Engine.getNodePosition(new SorryFrame.Coordinate(15, 0)),
				66);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testCoordinateToIntStarts() {
		for (int i = 10; i <= 12; i++) {
			for (int j = 12; j <= 14; j++)
				assertEquals(
						Engine.getNodePosition(new SorryFrame.Coordinate(i, j)),
						11);
		}

		for (int i = 3; i <= 5; i++) {
			for (int j = 14; j >= 12; j--)
				assertEquals(
						Engine.getNodePosition(new SorryFrame.Coordinate(j, i)),
						77);
		}

		for (int i = 3; i <= 5; i++) {
			for (int j = 1; j <= 3; j++) {
				assertEquals(
						Engine.getNodePosition(new SorryFrame.Coordinate(i, j)),
						55);
			}
		}

		for (int i = 1; i <= 3; i++) {
			for (int j = 12; j >= 10; j--) {
				assertEquals(
						Engine.getNodePosition(new SorryFrame.Coordinate(i, j)),
						33);
			}
		}

	}

	@SuppressWarnings("javadoc")
	@Test
	public void testCoordinateToIntHomeZones() {
		for (int i = 14; i >= 12; i--) {
			for (int j = 7; j <= 9; j++)
				assertEquals(
						Engine.getNodePosition(new SorryFrame.Coordinate(i, j)),
						8);
		}

		for (int i = 9; i >= 7; i--) {
			for (int j = 1; j <= 3; j++)
				assertEquals(
						Engine.getNodePosition(new SorryFrame.Coordinate(i, j)),
						74);
		}

		for (int i = 1; i <= 3; i++)
			for (int j = 6; j <= 8; j++)
				assertEquals(
						Engine.getNodePosition(new SorryFrame.Coordinate(i, j)),
						52);

		for (int i = 6; i <= 8; i++)
			for (int j = 14; j >= 12; j--)
				assertEquals(
						Engine.getNodePosition(new SorryFrame.Coordinate(i, j)),
						30);

	}

	@SuppressWarnings("javadoc")
	@Test
	public void testCoordinateToIntSafeZones() {
		int green = 68;
		for (int i = 15; i >= 10; i--)
			assertEquals(
					Engine.getNodePosition(new SorryFrame.Coordinate(i, 2)),
					green++);

		int red = 2;
		for (int i = 15; i >= 10; i--)
			assertEquals(
					Engine.getNodePosition(new SorryFrame.Coordinate(13, i)),
					red++);

		int blue = 24;
		for (int i = 0; i <= 5; i++)
			assertEquals(
					Engine.getNodePosition(new SorryFrame.Coordinate(i, 13)),
					blue++);

		int yellow = 46;
		for (int i = 0; i <= 5; i++)
			assertEquals(
					Engine.getNodePosition(new SorryFrame.Coordinate(2, i)),
					yellow++);

	}

	@SuppressWarnings("javadoc")
	@Test
	public void testCoordinateToIntSideLines() {
		checkCoordinateInt(15, 15, 0);
		checkCoordinateInt(14, 15, 1);
		checkCoordinateInt(13, 15, 2);
		checkCoordinateInt(12, 15, 9);
		checkCoordinateInt(11, 15, 10);
		checkCoordinateInt(10, 15, 12);

		int red = 13;
		for (int i = 9; i >= 0; i--)
			checkCoordinateInt(i, 15, red++);

		checkCoordinateInt(0, 15, 22);
		checkCoordinateInt(0, 14, 23);
		checkCoordinateInt(0, 13, 24);
		checkCoordinateInt(0, 12, 31);
		checkCoordinateInt(0, 11, 32);
		checkCoordinateInt(0, 10, 34);

		int blue = 35;
		for (int i = 9; i >= 0; i--)
			checkCoordinateInt(0, i, blue++);

		checkCoordinateInt(0, 0, 44);
		checkCoordinateInt(1, 0, 45);
		checkCoordinateInt(2, 0, 46);
		checkCoordinateInt(3, 0, 53);
		checkCoordinateInt(4, 0, 54);

		int yellow = 56;
		for (int i = 5; i <= 15; i++)
			checkCoordinateInt(i, 0, yellow++);

		checkCoordinateInt(15, 0, 66);
		checkCoordinateInt(15, 1, 67);
		checkCoordinateInt(15, 2, 68);
		checkCoordinateInt(15, 3, 75);
		checkCoordinateInt(15, 4, 76);

		int green = 78;
		for (int i = 5; i < 15; i++)
			checkCoordinateInt(15, i, green++);

	}

	private void checkCoordinateInt(int x, int y, int pos) {
		assertEquals(Engine.getNodePosition(new SorryFrame.Coordinate(x, y)),
				pos);
	}

	private void movePawn(int num, Piece p, Engine e) {
		try {
			e.move(num, p, e.findNode(p));
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	@Test
	public void testFindNodeWithPiece() {
		BoardList temp = new BoardList();
		Engine e = new Engine(temp);
		e.newGame();
		Piece p = temp.getStartPointers()[0].getPieces()[0];
		assertEquals(e.findNode(p), temp.getStartPointers()[0]);
	}

	@Test
	public void testFindNodeWithPosition() {
		BoardList temp = new BoardList();
		Engine e = new Engine(temp);
		e.newGame();
		assertEquals(e.findNodeByPosition(87),
				temp.getCornerPointers()[0].getPrevious());
		assertEquals(e.findNodeByPosition(11), temp.getStartPointers()[0]);
		assertEquals(e.findNodeByPosition(8), temp.getHomePointers()[0]);
	}

	@Test
	public void testIsValidMoveNoMove() {
		BoardList board = new BoardList();
		Engine e = new Engine(board);
		e.newGame();
		assertTrue(e.isValidMove(e.pieces[0], 0, new Player(Piece.COLOR.red,
				"Bob Dole")));
	}

	@Test
	public void testPanwMovement() {
		BoardList board = new BoardList();
		Engine e = new Engine(board);
		e.getNextCard();
		assertEquals(e.pawnMove(new SorryFrame.Coordinate(0, 0),
				new SorryFrame.Coordinate(0, 0)), 0);
	}

	@Test
	public void testCountTo() {
		BoardList board = new BoardList();
		Engine e = new Engine(board);
		int count = board.cornerPointers[0].countTo(board.cornerPointers[1]);
		assertEquals(count, 15);
	}

	@Test
	public void testIsValidPlayerCheck() {
		BoardList board = new BoardList();
		Engine e = new Engine(board);
		Player p = new Player(Piece.COLOR.red, "James Bond");

		e.newGame();

		assertTrue(e.isValidMove(e.pieces[0], 0, p));

		assertFalse(e.isValidMove(e.pieces[0], 0, new Player(Piece.COLOR.blue,
				"Steve Jobs")));

		for (int i = 1; i < 4; i++)
			assertTrue(e.isValidMove(e.pieces[i], 0, p));

		assertFalse(e.isValidMove(e.pieces[4], 0, p));
	}

	@Test
	public void testPawnMoveSamePositions() {
		BoardList board = new BoardList();
		Engine e = new Engine(board);
		e.newGame();

		assertEquals(Engine.SAME_NODE_SELECTED, e.pawnMove(
				new SorryFrame.Coordinate(0, 0),
				new SorryFrame.Coordinate(0, 0)));

		for (int i = 0; i < 16; i++) {
			assertEquals(Engine.SAME_NODE_SELECTED, e.pawnMove(
					new SorryFrame.Coordinate(i, 0), new SorryFrame.Coordinate(
							i, 0)));
			assertEquals(0, e.pawnMove(new SorryFrame.Coordinate(0, i),
					new SorryFrame.Coordinate(0, i)));
		}
	}

	@Test
	public void testPawnMoveInvalidCoordinates() {
		BoardList board = new BoardList();
		Engine e = new Engine(board);
		e.newGame();

		assertEquals(Engine.NODE_NOT_FOUND, e.pawnMove(
				new SorryFrame.Coordinate(-1, -1), new SorryFrame.Coordinate(0,
						0)));

		assertEquals(Engine.NODE_NOT_FOUND, e.pawnMove(
				new SorryFrame.Coordinate(0, 0), new SorryFrame.Coordinate(-1,
						-1)));

		assertEquals(Engine.NODE_NOT_FOUND, e.pawnMove(
				new SorryFrame.Coordinate(1, 1),
				new SorryFrame.Coordinate(0, 0)));

		assertEquals(Engine.NODE_NOT_FOUND, e.pawnMove(
				new SorryFrame.Coordinate(6, 6), new SorryFrame.Coordinate(-1,
						-1)));

		assertEquals(Engine.NODE_NOT_FOUND, e.pawnMove(
				new SorryFrame.Coordinate(7, 7),
				new SorryFrame.Coordinate(7, 7)));

	}

	@Test
	public void testInsertPlayers() {
		BoardList board = new BoardList();
		Engine e = new Engine(board);

		Player john = new Player(Piece.COLOR.green, "Johnny Depp");
		Player bill = new Player(Piece.COLOR.blue, "Bill Gates");
		Player siriam = new Player(Piece.COLOR.yellow, "Whale Rider");
		Player buffalo = new Player(Piece.COLOR.red, "Buffalo");

		e.insertPlayer(john);
		e.insertPlayer(bill);

		e.rotatePlayers();

		assertEquals(e.activePlayer, john);
		e.rotatePlayers();
		assertEquals(e.activePlayer, bill);
		e.rotatePlayers();
		assertEquals(e.activePlayer, john);

		e.insertPlayer(siriam);
		assertEquals(e.activePlayer, john);

		e.rotatePlayers();
		assertEquals(e.activePlayer, bill);

		e.rotatePlayers();
		assertEquals(e.activePlayer, john);

		e.rotatePlayers();
		assertEquals(e.activePlayer, siriam);

		e.insertPlayer(buffalo);
		assertEquals(e.activePlayer, siriam);
	}

}
