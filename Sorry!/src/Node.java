public class Node {
	private Node next;
	private Node previous;
	private Piece[] pieces;
	private Piece.COLOR col;
	private final int MAX_PIECES = 1;

	public Node() {
		this.next = null;
		this.previous = null;
		this.pieces = new Piece[1];
		this.col = Piece.COLOR.colorless;
	}

	public Node(Node nextN, Node prevN) {
		this.next = nextN;
		this.previous = prevN;
		this.pieces = new Piece[1];
		this.col = Piece.COLOR.colorless;
	}

	public Node(Node nextN, Node prevN, Piece.COLOR color) {
		this.next = nextN;
		this.previous = prevN;
		this.pieces = new Piece[1];
		this.col = color;
	}

	public Node getNext() {
		return this.next;
	}

	public Node getPrevious() {
		return this.previous;
	}

	public void setNext(Node newNext) {
		this.next = newNext;
	}

	public void setPrevious(Node newPrev) {
		this.previous = newPrev;
	}

	public Piece[] getPieces() {
		return pieces;
	}

	public void setPieces(Piece[] newPieces) {
		if (newPieces.length != 1) {
			throw new IllegalArgumentException("Piece Array Too Big");
		}
		this.pieces = newPieces;
	}

	public String toString() {
		StringBuilder ret = new StringBuilder();
		switch (this.getColor()) {
		case red:
			ret.append("rsf");
			break;
		case blue:
			ret.append("bsf");
			break;
		case yellow:
			ret.append("ysf");
			break;
		case green:
			ret.append("gsf");
			break;
		default:
			ret.append("nn");
			break;
		}

		if (this.getPieces()[0] != null) {
			switch (this.getPieces()[0].col) {
			case red:
				ret.append("r");
				break;
			case blue:
				ret.append("b");
				break;
			case yellow:
				ret.append("y");
				break;
			case green:
				ret.append("g");
				break;
			default:
				ret.append("ITBROKEWILLIS");
				break;
			}
		}
		ret.append("|");
		return ret.toString();
	}

	public Piece.COLOR getColor() {
		return this.col;
	}

	public void setColor(Piece.COLOR col) {
		this.col = col;
	}

	public void addPieceToPieces(Piece p) {
		if (this.pieces[0] == null) {
			this.pieces[0] = p;
		}

		else
			throw new IndexOutOfBoundsException("No more room on this node");
	}

	public void removePieceFromPieces(Piece p) {
		this.pieces[0] = null;
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
}