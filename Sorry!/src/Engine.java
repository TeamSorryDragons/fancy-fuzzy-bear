import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

public class Engine {
	private static HashMap<SorryFrame.Coordinate, Integer> coordsMap;
	BoardList board;
	Piece[] pieces;// Indices 0-3 are red, Indices 4-7 are blue, Indices 8-11
					// are Yellow, Indices 12-15 are green

	public Engine(BoardList board) {
		this.board = board;
	}

	/*
	 * public void testing() { try { move(1, this.pieces[14]); move(3,
	 * this.pieces[4]); } catch (Unstarted e) { e.printStackTrace(); } }
	 * 
	 * public void testing2() { try { move(1, this.pieces[9]); move(1,
	 * this.pieces[0]); } catch (Unstarted e) { e.printStackTrace(); } }
	 */

	public void newGame() {
		this.pieces = this.board.newGame();
	}

	/**
	 * Move a given piece the specified number of positions FORWARD on the
	 * board.
	 * 
	 * Don't try using negative numbers.
	 * 
	 * @param moves
	 * @param piece
	 * @throws Unstarted
	 *             if the game hasn't been generated
	 */
	public void move(int moves, Piece piece) throws Unstarted {
		Node piecenode = findNode(piece);
		piecenode.removePieceFromPieces(piece);
		ArrayList<Piece> strt = piecenode.move(moves,piece);
		if(strt != null && strt.size() > 0){
			for(int i = 0; i < strt.size(); i++){
				toStart(strt.get(i));
			}
		}
	}

	public Node findNode(Piece piece) {

		return this.board.getCornerPointers()[0].findNodeWithPiece(piece);
	}
	
	public Node findNodeByPosition(int i){
		return this.board.getCornerPointers()[0].findNodeWithPosition(i);
	}

/*	private Node findNode(Piece pawn, Node next) {
		if (next == null) {
			return null;
		}
		Piece[] currentPieces = next.getPieces();
		if (contains(currentPieces, pawn)) {
			return next;
		}

		if (next instanceof SlideNode) {
			SlideNode slide = (SlideNode) next;
			if (slide.getColor() == pawn.col
					&& !(slide.getSafeNode() instanceof MultiNode)) {
				Node maybeFoundIt = findNode(pawn, slide.getSafeNode());
				if (maybeFoundIt != null)
					return maybeFoundIt;
			}
			if (slide.getColor() == pawn.col && slide.getSafeNode() != null) {
				// we're looking at the start node right here for reasons
				if (contains(slide.getSafeNode().getPieces(), pawn))
					return slide.getSafeNode();
			}
		}

		if (next.getNext() == null)
			return null;

		return findNode(pawn, next.getNext());

	} */

	/**
	 * Helper method for moving pieces to the start position.
	 * 
	 * @param pawn
	 */
	protected void toStart(Piece pawn) {
		switch (pawn.col) {
		case red:
			this.board.getStartPointers()[0].addPieceToPieces(pawn);
			break;
		case blue:
			this.board.getStartPointers()[1].addPieceToPieces(pawn);
			break;
		case yellow:
			this.board.getStartPointers()[2].addPieceToPieces(pawn);
			break;
		case green:
			this.board.getStartPointers()[3].addPieceToPieces(pawn);
			break;
		}
	}

	/**
	 * Helper method to determine whether or not a pawn is contained within a
	 * given set of pawns.
	 * 
	 * @param pawns
	 * @param pawn
	 * @return
	 */
	protected static boolean contains(Piece[] pawns, Piece pawn) {
		for (Piece p : pawns) {
			if (p == pawn)
				return true;
		}
		return false;
	}

	/**
	 * Exception thrown is a game has not been started prior to interaction with
	 * that game.
	 * 
	 * @author TeamSorryDragons
	 */
	protected class Unstarted extends Exception {
		private static final long serialVersionUID = 1L;

		public Unstarted(String message) {
			super(message);
		}
	}

	/**
	 * Returns the node at the given coordinate position.
	 * 
	 * @param coordinate
	 * @return node corresponding to the given coordinate
	 */
	public Node convertCoordToNode(SorryFrame.Coordinate coordinate) {
		// TODO this
		if (coordsMap.containsKey(coordinate)) {
			return null;
		} else {
			// the map doesn't contain it, probably an invalid coordinate
			return null;
		}

	}

	protected static int getNodePosition(SorryFrame.Coordinate coord) {
		if (coordsMap == null)
			populateCoordsMap();
		if (coordsMap.containsKey(coord))
			return coordsMap.get(coord);
		throw new CoordinateOffOfBoardException("Bad coordinate access: "
				+ coord.getX() + " " + coord.getY());
	}

	private static void populateCoordsMap() {
		coordsMap = new HashMap<SorryFrame.Coordinate, Integer>();
		populateCorners();
		populateStartZones();
		populateHomeZones();
		populateSafeZones();
		populateSideLines();
	}

	private static void populateSideLines() {
		mapInsert(14, 15, 1);
		mapInsert(13, 15, 2);
		mapInsert(12, 15, 9);
		mapInsert(11, 15, 10);

		mapInsert(0, 14, 23);
		mapInsert(0, 13, 24);
		mapInsert(0, 12, 31);
		mapInsert(0, 11, 32);

		mapInsert(1, 0, 45);
		mapInsert(2, 0, 46);
		mapInsert(3, 0, 53);
		mapInsert(4, 0, 54);

		mapInsert(15, 1, 67);
		mapInsert(15, 2, 68);
		mapInsert(15, 3, 75);
		mapInsert(15, 4, 76);

	}

	private static void mapInsert(int x, int y, int pos) {
		coordsMap.put(new SorryFrame.Coordinate(x, y), pos);

	}

	private static void populateSafeZones() {
		int redZone = 2;
		for (int i = 15; i >= 10; i--)
			coordsMap.put(new SorryFrame.Coordinate(13, i), redZone++);

		int blueZone = 24;
		for (int i = 0; i <= 5; i++)
			coordsMap.put(new SorryFrame.Coordinate(i, 13), blueZone++);

		int yellowZone = 46;
		for (int i = 0; i <= 5; i++)
			coordsMap.put(new SorryFrame.Coordinate(2, i), yellowZone++);

		int greenZone = 68;
		for (int i = 15; i >= 10; i--)
			coordsMap.put(new SorryFrame.Coordinate(i, 2), greenZone++);
	}

	private static void populateHomeZones() {
		int redHome = 8;
		for (int i = 14; i >= 12; i--)
			for (int j = 9; j >= 7; j--)
				coordsMap.put(new SorryFrame.Coordinate(i, j), redHome);

		int blueHome = 30;
		for (int i = 6; i <= 8; i++)
			for (int j = 14; j >= 12; j--)
				coordsMap.put(new SorryFrame.Coordinate(i, j), blueHome);

		int yellowHome = 52;
		for (int i = 1; i <= 3; i++)
			for (int j = 6; j <= 8; j++)
				coordsMap.put(new SorryFrame.Coordinate(i, j), yellowHome);

		int greenHome = 74;
		for (int i = 7; i <= 9; i++)
			for (int j = 1; j <= 3; j++)
				coordsMap.put(new SorryFrame.Coordinate(i, j), greenHome);

	}

	private static void populateStartZones() {
		int redStart = 11;
		for (int i = 10; i <= 12; i++) {
			for (int j = 12; j <= 14; j++) {
				coordsMap.put(new SorryFrame.Coordinate(i, j), redStart);
			}
		}

		int blueStart = 33;
		for (int i = 10; i <= 12; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap.put(new SorryFrame.Coordinate(j, i), blueStart);
			}
		}

		int yellowStart = 55;
		for (int i = 3; i <= 5; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap.put(new SorryFrame.Coordinate(i, j), yellowStart);
			}
		}

		int greenStart = 77;
		for (int i = 3; i <= 5; i++) {
			for (int j = 12; j <= 14; j++)
				coordsMap.put(new SorryFrame.Coordinate(j, i), greenStart);
		}
	}

	private static void populateCorners() {
		int redSide = 12;
		int blueSide = 34;

		for (int i = 10; i >= 0; i--) {
			coordsMap.put(new SorryFrame.Coordinate(i, 15), redSide++);
			coordsMap.put(new SorryFrame.Coordinate(0, i), blueSide++);
		}

		int greenSide = 78;
		int yellowSide = 56;
		for (int i = 5; i <= 15; i++) {
			coordsMap.put(new SorryFrame.Coordinate(i, 0), yellowSide++);
			coordsMap.put(new SorryFrame.Coordinate(15, i), greenSide++);
		}
		coordsMap.put(new SorryFrame.Coordinate(15, 15), 0);

	}
}
