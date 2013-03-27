/**
 * Circularly linked list.
 * 
 * Credit to Lajos Arpad from StackOverflow, thank you sir.
 * 
 * @author not me. Created Mar 27, 2013.
 * @param <T> 
 */
public class CircularLinkedList<T> {

	private ListNode<T> head = null;
	private int numberOfElements = 0;
	private ListNode<T> actualElement = null;
	private int index = 0;

	/**
	 * Is it empty?
	 *
	 * @return boolean
	 */
	public boolean isEmpty() {
		return (this.numberOfElements == 0);
	}

	/**
	 * How many elements?
	 *
	 * @return puppies
	 */
	public int getNumberOfElements() {
		return this.numberOfElements;
	}

	/**
	 * Insert a node.  You know.
	 *
	 * @param data
	 */
	public void insertFirst(T data) {
		if (!(isEmpty())) {
			this.index++;
		}
		ListNode<T> listNode = new ListNode<T>(data, this.head);
		this.head = listNode;
		this.numberOfElements++;
	}

	/**
	 * Insert a node after the current position.
	 *
	 * @param data
	 */
	public void insertAfterActual(T data) {
		ListNode<T> listNode = new ListNode<T>(data, this.actualElement.next);
		this.actualElement.next = listNode;
		this.numberOfElements++;
	}

	/**
	 * Delete the head.
	 *
	 * @return did it work?
	 */
	public boolean deleteFirst() {
		if (isEmpty())
			return false;
		if (this.index > 0)
			this.index--;
		this.head = this.head.next;
		this.numberOfElements--;
		return true;
	}

	/**
	 * Delete current element.
	 *
	 * @return did it work?
	 */
	public boolean deleteActualElement() {
		if (this.index > 0) {
			this.numberOfElements--;
			this.index--;
			ListNode<T> listNode = this.head;
			while (listNode.next.equals(this.actualElement) == false)
				listNode = listNode.next;
			listNode.next = this.actualElement.next;
			this.actualElement = listNode;
			return true;
		} else {
			this.actualElement = this.head.next;
			this.index = 0;
			return deleteFirst();
		}
	}

	/**
	 * Moves to the next element.
	 *
	 * @return did it work?
	 */
	public boolean goToNextElement() {
		if (isEmpty())
			return false;
		this.index = (this.index + 1) % this.numberOfElements;
		if (this.index == 0)
			this.actualElement = this.head;
		else
			this.actualElement = this.actualElement.next;
		return true;
	}

	/**
	 * Get current node's data.
	 *
	 * @return data
	 */
	public T getActualElementData() {
		return this.actualElement.data;
	}

	/**
	 * Set the data in the current node.
	 *
	 * @param data
	 */
	public void setActualElementData(T data) {
		this.actualElement.data = data;
	}

	/**
	 * Again, credit where it be due.  Node stuffs.
	 *
	 * @author Lajos Arpad, my main bro.
	 *         Created Mar 27, 2013.
	 * @param <T>
	 */
	@SuppressWarnings("hiding")
	class ListNode<T> {
		@SuppressWarnings("javadoc")
		public ListNode<T> next;
		@SuppressWarnings("javadoc")
		public T data;

		/**
		 * Constructor.
		 *
		 * @param data
		 * @param next
		 */
		public ListNode(T data, ListNode<T> next) {
			this.next = next;
			this.data = data;
		}
	}
}
