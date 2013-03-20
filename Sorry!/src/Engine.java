public class Engine {
	BoardList board;
	Piece[] pieces;// Indices 0-3 are red, Indices 4-7 are blue, Indices 8-11
					// are Yellow, Indices 12-15 are green

	public Engine(BoardList board) {
		this.board = board;
	}
	
	public void testing(){
		try {
			move(3,pieces[5]);
			move(4,pieces[2]);
			move(2,pieces[9]);
			move(1,pieces[14]);
		} catch (Unstarted e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void newGame() {
		pieces = this.board.newGame();
	}

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
		
		return findNode(piece, board.getCornerPointers()[0]);
	}

	private Node findNode(Piece pawn, Node next) {
		if(next == null){
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
			if(slide.getColor() == pawn.col && slide.getSafeNode() != null){
				// we're looking at the start node right here for reasons
				if (contains(slide.getSafeNode().getPieces(), pawn))
					return slide.getSafeNode();
			}
		}
		
		if (next.getNext() == null)
			return null;
		
		
		return findNode(pawn, next.getNext());

	}

	protected void toStart(Piece pawn) {
		switch (pawn.col) {
		case red:
			board.getStartPointers()[0].addPieceToPieces(pawn);
			break;
		case blue:
			board.getStartPointers()[1].addPieceToPieces(pawn);
			break;
		case yellow:
			board.getStartPointers()[2].addPieceToPieces(pawn);
			break;
		case green:
			board.getStartPointers()[3].addPieceToPieces(pawn);
			break;
		}
	}

	protected static boolean contains(Piece[] pawns, Piece pawn) {
		for (Piece p : pawns) {
			if (p == pawn)
				return true;
		}
		return false;
	}

	protected class Unstarted extends Exception {
		private static final long serialVersionUID = 1L;

		public Unstarted(String message) {
			super(message);
		}
	}
}
