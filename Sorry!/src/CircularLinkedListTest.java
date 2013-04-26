import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Test cases for the CircularLinkedList.
 *
 * @author sturgedl.
 *         Created Mar 27, 2013.
 */
public class CircularLinkedListTest {

	@Test
	public void test() {
		assertNotNull(new CircularLinkedList<Object>());
	}
	
	@Test
	public void testEmpty(){
		CircularLinkedList<Integer> ls = new CircularLinkedList<Integer>();
		assertTrue(ls.isEmpty());
		
		ls.insertFirst(0);
		assertFalse(ls.isEmpty());
		
		ls.deleteFirst();
		assertTrue(ls.isEmpty());
	}
	
	@Test
	public void testInsert(){
		CircularLinkedList<Integer> ls = new CircularLinkedList<Integer>();
		
		ls.insertFirst(0);
		ls.goToNextElement();
		ls.insertAfterActual(1);
		
		assertEquals((int)ls.getActualElementData(), 0);
		ls.goToNextElement();
		assertEquals((int)ls.getActualElementData(), 1);
		
		ls.insertAfterActual(2);
		assertEquals((int)ls.getActualElementData(), 1);
		
		ls.goToNextElement();
		assertEquals((int)ls.getActualElementData(), 2);
		
		assertEquals(ls.getNumberOfElements(), 3);
		
		ls.insertFirst(4);
		ls.goToNextElement();
		assertEquals((int)ls.getActualElementData(), 4);	
	}
	
	@Test
	public void testDelete(){
		CircularLinkedList<Integer> ls = new CircularLinkedList<Integer>();
		
		assertFalse(ls.goToNextElement());
		
		ls.insertFirst(0);
		ls.deleteActualElement();
		
		assertTrue(ls.isEmpty());
		
		ls.insertFirst(0);
		ls.goToNextElement();
		ls.insertAfterActual(1);
		
		assertEquals((int)ls.getActualElementData(), 0);
		
		ls.goToNextElement();
		ls.deleteActualElement();
		assertEquals(ls.getNumberOfElements(), 1);
		ls.goToNextElement();
		assertEquals((int)ls.getActualElementData(), 0);
		
		ls.insertAfterActual(1);
		ls.insertAfterActual(2);
		// 0 -> 2 -> 1
		assertEquals((int)ls.getActualElementData(), 0);
		ls.deleteFirst();
		assertEquals((int)ls.getActualElementData(), 0);
		
		ls.insertFirst(3);
		ls.insertFirst(4);
		ls.insertFirst(5);
		ls.insertFirst(6);
		assertEquals((int)ls.getActualElementData(), 0);
		
		ls.goToNextElement();
		assertEquals((int)ls.getActualElementData(), 2);
		ls.goToNextElement();
		assertEquals((int)ls.getActualElementData(), 6);
		
		for (int i=0; i < 3; i++)
			ls.goToNextElement();
		assertEquals((int)ls.getActualElementData(), 3);
		
		ls.deleteActualElement();
		ls.goToNextElement();
		assertEquals((int)ls.getActualElementData(), 2);
		
		for (int i=0; i < 5; i++)
			ls.deleteFirst();
		assertFalse(ls.deleteFirst());
	}
	
	@Test
	public void testChangeData(){
		CircularLinkedList<Integer> ls = new CircularLinkedList<Integer>();
		
		ls.insertFirst(7);
		ls.goToNextElement();
		assertEquals((int)ls.getActualElementData(), 7);
		ls.insertFirst(8);
		ls.setActualElementData(0);
		assertEquals((int)ls.getActualElementData(), 0);
		ls.goToNextElement();
		assertEquals((int)ls.getActualElementData(), 8);
	}
	
	

}
