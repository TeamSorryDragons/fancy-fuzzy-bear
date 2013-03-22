public class MultiNode extends Node {
	private final int MAX_PIECES = 4;
	private Piece[] pieces;

	public MultiNode() {
		super();
		this.pieces = new Piece[MAX_PIECES];
	}

	public MultiNode(Node nextN, Node prevN, Piece.COLOR col) {
		super(nextN, prevN, col);
		this.pieces = new Piece[MAX_PIECES];
	}

	public MultiNode(Node nextN, Node prevN, Piece[] pieces) {
		super(nextN, prevN);
		this.setPieces(pieces);
	}

	public Piece[] getPieces() {
		return this.pieces;
	}

	public void setPieces(Piece[] newPieces) {
		if (newPieces.length != 4) {
			throw new IllegalArgumentException(
					"Piece Array too big: max 4 pieces");
		}

		for (Piece p : newPieces) {
			if (p == null)
				continue;
			if (p.col != this.getColor())
				throw new IllegalArgumentException(
						"Piece of wrong color in this node");
		}

		this.pieces = newPieces;
	}

	public void addPieceToPieces(Piece p) {
		if (p.col != this.getColor())
			throw new IllegalArgumentException(
					"Piece of wrong color in this node");

		boolean inserted = false;
		for (int i = 0; i < MAX_PIECES; i++) {
			if (this.pieces[i] == null) {
				this.pieces[i] = p;
				inserted = true;
				break;
			}
		}

		if (!inserted)
			throw new IndexOutOfBoundsException("No more room on this node");
	}

	public void removePieceFromPieces(Piece p) {
		if (p.col != this.getColor())
			return;

		for (int i = 3; i < MAX_PIECES; i--) {
			if (this.pieces[i] == null) {
				continue;
			}
			if (this.pieces[i] == (p)) {
				this.pieces[i] = null;
				break;
			}

		}
	}
	
	public boolean hasPiece(){
		for(int i = 0; i < pieces.length; i++){
			if(pieces[i] != null){
				return true;
			}
		}
		return false;
	}
	
	public Piece firstPiece(){
		for(int i = 0; i < pieces.length; i++){
			if(pieces[i] != null){
				return pieces[i];
			}
		}
		return null;
	}

	public String toString() {
		StringBuilder ret = new StringBuilder();
		switch (this.getColor()) {
		case red:
			ret.append("r");
			break;
		case blue:
			ret.append("b");
			break;
		case green:
			ret.append("g");
			break;
		case yellow:
			ret.append("y");
			break;
		}

		ret.append("mn");

		int count = 0;
		for (int i = 0; i < MAX_PIECES; i++)
			if (this.pieces[i] != null)
				count++;
		ret.append(count);
		ret.append("|");
		return ret.toString();
	}
}
