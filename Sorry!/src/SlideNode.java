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

//	public SlideNode(Node safe) {
//		super();
//		this.safe = safe;
//		this.head = false;
//	}

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
}
