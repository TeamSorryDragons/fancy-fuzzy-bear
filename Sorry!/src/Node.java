import java.util.ArrayList;

public class Node {
	private Node next;
	private Node previous;
	protected Piece[] pieces;
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
	
	public Node findNodeWithPiece(Piece p){//we always start at the corner node which is a normal node
		if(this.pieces[0] == p){
			return this;
		}
		else{
			return this.next.findNodeWithPiece(p,this);
		}
	}
	public Node findNodeWithPiece(Piece p, Node start){
		if(this == start){
			throw new IllegalArgumentException();
		}
		else if(this.pieces[0] == p){
			return this;
		}
		else{
			return this.next.findNodeWithPiece(p,start);
		}
	}
	public Node findNodeWithPosition(int i){
		if(i > 87){
			throw new IndexOutOfBoundsException();
		}
		else if(i == 0){
			return this;
		}
		else{
			return this.next.findNodeWithPosition(i-1);
		}
	}
	public ArrayList<Piece> move(int moves, Piece p) throws InvalidMoveException{
		ArrayList<Piece> ret = new ArrayList<Piece>();
		if(moves != 0){
			return direction(moves).move(goTo(moves), p);
		}
		else{
			if(hasPiece()){
				Piece temp = firstPiece();
				removePieceFromPieces(temp);
				addPieceToPieces(p);
				ret.add(temp);
				return ret;
			}
			else{
				addPieceToPieces(p);
				return null;
			}
		}
	}
	protected Node direction(int moves){
		if(moves > 0){
			return this.getNext();
		}
		else{ //this never gets called unless moves != 0
			return this.getPrevious();
		}
	}
	protected int goTo(int moves){
		if(moves < 0){
			return moves+1;
		}
		else
			return moves-1;
	}
	public int countTo(Node node){
		if(this == node){
			return 0;
		}
		else{
			return 1 + this.next.countTo(node);
		}
	}
	public int countBack(Node node){
		if(this == node){
			return 0;
		}
		else{
			return 1+this.previous.countBack(node);
		}
	}
}