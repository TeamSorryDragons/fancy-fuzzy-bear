public class SlideNode extends Node {
	private Node safe;
	Piece.COLOR slideColor;

	public SlideNode() {
		super();
		this.safe = null;
		slideColor = Piece.COLOR.red;
	}

	public SlideNode(Piece.COLOR col) {
		super();
		this.safe = null;
		slideColor = col;
	}

	public SlideNode(Node next, Node prev) {
		super(next, prev);
		this.safe = null;
		slideColor = Piece.COLOR.red;
	}

	public SlideNode(Node safe) {
		super();
		this.safe = safe;
		slideColor = Piece.COLOR.red;
	}

	public SlideNode(Node next, Node prev, Node safe, Piece.COLOR col) {
		super(next, prev);
		this.safe = safe;
		slideColor = col;
	}

	public Piece.COLOR getColor() {
		return slideColor;
	}

	public void setColor(Piece.COLOR col) {
		this.slideColor = col;
	}

	public Node getSafeNode() {
		return this.safe;
	}

	public void setSafeNode(Node newSafe) {
		this.safe = newSafe;
	}
}
