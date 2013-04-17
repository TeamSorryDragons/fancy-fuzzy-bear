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
		else if(this.getSafeNode() != null && !(this.getSafeNode() instanceof MultiNode) && this.getColor() == p.col && moves > 0){
			try {
				return this.safe.move(moves-1, p);
			} catch (InvalidMoveException e) {
				e.printStackTrace();
			}
		}
		try {
			return direction(moves).move(goTo(moves), p);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
			return ret;
		}
	}
	
	public int countTo(Node node){
		if(this == node){
			return 0;
		}
		else if(this.getSafeNode() != null && this.getSafeNode().getPrevious() != null){
			Node temp = this.getSafeNode();
			boolean breaker = false;
			int temp2 = 0;
			while(temp != null){
				if(temp == node){
					breaker = true;
					break;
				}
				else{
					temp2 ++;
					temp = temp.getNext();
				}
			}
			if(breaker){
				return 1 + temp2;
			}
		}
		else if(this.getSafeNode() != null && this.getSafeNode() == node){
			return 1;
		}
		return 1 + this.getNext().countTo(node);
	}
	
	public int countBack(Node node){
		if(this == node){
			return 0;
		}
		else if(this.getSafeNode() != null && this.getSafeNode().getPrevious() != null){
			Node temp = this.getSafeNode();
			boolean breaker = false;
			while(temp != null){
				if(temp == node){
					breaker = true;
					break;
				}
				else{
					temp = temp.getNext();
				}
			}
			if(breaker){
				throw new IllegalArgumentException("You decided that going forwards on a slide was backwards.");
			}
		}
		else if(this.getSafeNode() != null && this.getSafeNode() == node){
			throw new IllegalArgumentException("You decided that going forwards into start was backwards.");
		}
		return 1 + this.getPrevious().countBack(node);
	}
	
	public Piece swap(Node node) throws InvalidMoveException{
		Piece piece = node.firstPiece();
		node.removePieceFromPieces(piece);
		Piece piece2 = firstPiece();
		removePieceFromPieces(piece2);
		Node temp = this;
		if(piece.col != this.getColor() && this.head){
			while(temp.getNext() instanceof SlideNode){
				temp = temp.getNext();
			}
		}
		temp.addPieceToPieces(piece);
		return piece2;
	}
	
	public boolean canReceivePiece(Piece.COLOR col){
		return true;
	}
}
