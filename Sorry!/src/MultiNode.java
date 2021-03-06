import java.util.ArrayList;

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
	public Node findNodeWithPiece(Piece p, Node start){
		for(int i = 0; i < MAX_PIECES; i++){
			if(this.pieces[i] == p){
				return this;
			}
		}
		return null;
	}
	public Node findNodeWithPosition(int i){
		if(i == 0){
			return this;
		}
		else
			return null;
	}
	public ArrayList<Piece> move(int moves, Piece p) throws InvalidMoveException{
		if(this.getPrevious() != null){
			if(moves == 0){
				this.addPieceToPieces(p);
				return null;
			}
			else{
				throw new InvalidMoveException("You moved past home");
			}
		}
		else{
			if(moves < 0){
				throw new InvalidMoveException("You tried to move backwards from start");
			}
			if(moves != 1 && moves != 2){
				throw new InvalidMoveException("You tried to leave home without a 1 or a 2");
			}
			return direction(moves).move(goTo(moves), p);
		}
	}
	public int countBack(Node node){
		if(this == node){
			return 0;
		}
		else if(this.getPrevious() != null){
			return 1 + this.getPrevious().countBack(node);
		}
		else{
			throw new IllegalArgumentException();
		}
	}
	public Piece swap(Node node) throws InvalidMoveException{
		throw new InvalidMoveException("You can't swap into home");
	}
	public int numberOfPieces(){
		int count = 0;
		for(int i = 0; i < pieces.length; i++){
			if(pieces[i] != null){
				count++;
			}
		}
		return count;
	}
	
	public boolean canReceivePiece(Piece.COLOR col){
		return this.getColor() == col;
	}
}
