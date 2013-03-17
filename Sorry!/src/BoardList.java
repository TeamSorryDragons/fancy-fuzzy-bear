public class BoardList {
	Node[] cornerPointers;

	public BoardList() {
		cornerPointers = new Node[4];
		cornerPointers[0] = new Node(null, null);
		cornerPointers[1] = buildQuarterBoard(cornerPointers[0], Piece.COLOR.red);
		cornerPointers[2] = buildQuarterBoard(cornerPointers[1], Piece.COLOR.blue);
		cornerPointers[3] = buildQuarterBoard(cornerPointers[2], Piece.COLOR.yellow);
		Node ret = buildQuarterBoard(cornerPointers[3], Piece.COLOR.green);
		cornerPointers[0].setPrevious(ret.getPrevious());
		ret.getPrevious().setNext(cornerPointers[0]);
	}

	public String toString() {
		return toString(cornerPointers[0], cornerPointers[0].getNext()).toString();
	}
	private StringBuilder toString(Node start, Node next){
		StringBuilder ret = new StringBuilder();
		ret.append(next.toString());
		if(next.getNext() == null)
			return ret;
		if (start == next)
			return ret;
		if(next instanceof SlideNode){
			if (((SlideNode) next).getSafeNode() != null)
			{
				if(((SlideNode) next).getSafeNode() instanceof MultiNode)
					ret.append(toString(((SlideNode) next).getSafeNode(),((SlideNode) next).getSafeNode()));
				else
					ret.append(toString(start,((SlideNode) next).getSafeNode()));
			}
		}
		return ret.append(toString(start, next.getNext()));
		
	}

	/*
	 * public String toString(Node start){ Node temp = start; String ret = "";
	 * while(temp.next != start){
	 * 
	 * } }
	 */
	protected Node buildQuarterBoard(Node previous, Piece.COLOR col) {
		SlideNode one = new SlideNode(null, previous, null, col);
		one.head = true;
		previous.setNext(one);
		SlideNode two = new SlideNode(null, one, null, col); // This now goes to
		one.setNext(two);									 // safety
		Node three = new Node(null, two, col);
		two.setSafeNode(three);
		Node four = new Node(null, three, col);
		three.setNext(four);
		Node five = new Node(null, four, col);
		four.setNext(five);
		Node six = new Node(null, five, col);
		five.setNext(six);
		Node seven = new Node(null, six, col);
		six.setNext(seven);
		MultiNode eight = new MultiNode(null, seven, col);
		seven.setNext(eight);
		SlideNode nine = new SlideNode(null, two, null, col);
		two.setNext(nine);
		SlideNode ten = new SlideNode(null, nine, null, col);
		nine.setNext(ten);
		MultiNode eleven = new MultiNode(ten, null, col);
		ten.setSafeNode(eleven);
		Node twelve = new Node(null, ten);
		ten.setNext(twelve);
		Node thirteen = new Node(null, twelve);
		twelve.setNext(thirteen);
		Node fourteen = new Node(null, thirteen);
		thirteen.setNext(fourteen);
		Node fifteen = new Node(null, fourteen);
		fourteen.setNext(fifteen);
		SlideNode sixteen = new SlideNode(null, fifteen, null, col);
		fifteen.setNext(sixteen);
		sixteen.head = true;
		SlideNode seventeen = new SlideNode(null, sixteen, null, col);
		sixteen.setNext(seventeen);
		SlideNode eighteen = new SlideNode(null, seventeen, null, col);
		seventeen.setNext(eighteen);
		SlideNode nineteen = new SlideNode(null, eighteen, null, col);
		eighteen.setNext(nineteen);
		SlideNode twenty = new SlideNode(null, nineteen, null, col);
		nineteen.setNext(twenty);
		Node twentyOne = new Node(null, twenty);
		twenty.setNext(twentyOne);
		Node twentyTwo = new Node(null, twentyOne);
		twentyOne.setNext(twentyTwo);
		return twentyTwo;
	}
}
