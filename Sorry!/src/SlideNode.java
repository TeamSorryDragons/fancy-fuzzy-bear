import java.util.ArrayList;

public class SlideNode extends Node {
	private Node safe;
	protected boolean head;

	public SlideNode() {
		super();
		this.safe = null;
		this.head = false;
	}

	public SlideNode(Piece.COLOR col) {
		super();
		this.safe = null;
		this.setColor(col);
		this.head = false;
	}

	public SlideNode(Node next, Node prev) {
		super(next, prev);
		this.safe = null;
		this.head = false;
	}

	// public SlideNode(Node safe) {
	// super();
	// this.safe = safe;
	// this.head = false;
	// }

	public SlideNode(Node next, Node prev, Node safe, Piece.COLOR col) {
		super(next, prev);
		this.safe = safe;
		this.setColor(col);
		this.head = false;
	}

	public Node getSafeNode() {
		return this.safe;
	}

	public void setSafeNode(Node newSafe) {
		this.safe = newSafe;
	}

	public String toString() {
		StringBuilder ret = new StringBuilder();
		if (this.head)
			ret.append("h");
		switch (this.getColor()) {
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
		}

		ret.append("sn");

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
			}
		}
		ret.append("|");
		return ret.toString();
	}

	public Node findNodeWithPiece(Piece p, Node start) {
		if (this.pieces[0] == p) {
			return this;
		} else if (p.col == this.getColor() && this.safe != null) {
			Node temp = this.safe.findNodeWithPiece(p, start);
			if (temp != null)
				return temp;
		}
		return this.getNext().findNodeWithPiece(p, start);
	}

	public Node findNodeWithPosition(int i) {
		if (i == 0) {
			return this;
		} else if (this.safe != null) {
			Node temp = this.safe.findNodeWithPosition(i - 1);
			if (temp != null) {
				return temp;
			} else if (this.safe instanceof MultiNode) {
				i -= 1;
			} else {
				i -= 6;
			}
		}
		return this.getNext().findNodeWithPosition(i - 1);
	}

	public ArrayList<Piece> move(int moves, Piece p) {
		ArrayList<Piece> ret = new ArrayList<Piece>();
		if (moves == 0) {
			if (this.head && p.col != this.getColor()) {
				Node temp = this;
				while (temp.getNext() instanceof SlideNode) {
					if(temp.hasPiece()){
						ret.add(temp.firstPiece());
						temp.removePieceFromPieces(temp.firstPiece());
					}
					temp = temp.getNext();
				}
				Piece temp2 = null;
				if(temp.hasPiece()){
					temp2 = temp.firstPiece();
					temp.removePieceFromPieces(temp2);
					ret.add(temp2);
				}
				temp.addPieceToPieces(p);
				return ret;
			} else {
				Piece temp2 = null;
				if(hasPiece()){
					temp2 = this.firstPiece();
					this.removePieceFromPieces(temp2);
					ret.add(temp2);
				}
				this.addPieceToPieces(p);
				return ret;
			}
		}
		else if(this.getSafeNode() != null && !(this.getSafeNode() instanceof MultiNode) && this.getColor() == p.col){
			return this.safe.move(moves-1, p);
		}
		return this.getNext().move(moves-1, p);
	}
}
