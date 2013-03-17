
public class Node {
	private Node next;
	private Node previous;
	private Piece[] pieces;
	public Node(){
		this.next = null;
		this.previous = null;
		this.pieces = new Piece[1];
	}
	public Node(Node nextN,Node prevN){
		this.next = nextN;
		this.previous = prevN;
		this.pieces = new Piece[1];
	}
	public Node getNext(){
		return this.next;
	}
	public Node getPrevious(){
		return this.previous;
	}
	public void setNext(Node newNext){
		this.next = newNext;
	}
	public void setPrevious(Node newPrev){
		this.previous = newPrev;
	}
	public Piece[] getPieces(){
		return pieces;
	}
	public void setPieces(Piece[] newPieces){
		if(newPieces.length != 1){
			throw new IllegalArgumentException("Piece Array Too Big");
		}
		this.pieces = newPieces;
	}
	public String toString(){
		if(this.pieces[0] != null){
			switch(this.pieces[0].col){
			case red: return "nnpr|";
			case blue: return "nnpb|";
			case green: return "nnpg|";
			case yellow: return "nnpy|";
			default: return "nnBROKEN|";
			}
		}
		else
			return "nn|";
	}
}