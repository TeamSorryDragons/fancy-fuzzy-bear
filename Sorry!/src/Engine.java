public class Engine {
	BoardList board;
	Piece[] pieces;// Indices 0-3 are red, Indices 4-7 are blue, Indices 8-11
					// are Yellow, Indices 12-15 are green

	public Engine(BoardList board) {
		this.board = board;
	}

/*	public void testing() {
		try {
			move(1, this.pieces[14]);
			move(3, this.pieces[4]);
		} catch (Unstarted e) {
			e.printStackTrace();
		}
	}

	public void testing2() {
		try {
			move(1, this.pieces[9]);
			move(1, this.pieces[0]);
		} catch (Unstarted e) {
			e.printStackTrace();
		}
	} */

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
		for (int i = 0; i < moves; i++) {
			if (piecenode instanceof SlideNode) {
				if (piecenode.getColor() == piece.col) {
					if (((SlideNode) piecenode).getSafeNode() != null
							&& !(((SlideNode) piecenode).getSafeNode() instanceof MultiNode)) {
						piecenode = ((SlideNode) piecenode).getSafeNode();
						continue;
					}
				}
			}
			if (piecenode.getNext() == null) {
				break;
			} else {
				piecenode = piecenode.getNext();
			}
		}
		try {
			piecenode.addPieceToPieces(piece);
		} catch (IndexOutOfBoundsException e) {
			Piece[] pawnArray = piecenode.getPieces();
			toStart(pawnArray[0]);
			piecenode.removePieceFromPieces(pawnArray[0]);
			piecenode.addPieceToPieces(piece);
		}
	}

	private Node findNode(Piece piece) {

		return findNode(piece, this.board.getCornerPointers()[0]);
	}

	private Node findNode(Piece pawn, Node next) {
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

	}

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
}
