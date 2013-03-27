import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

/**
 * @author TeamSorryDragons
 *
 */
public class DeckTest {
	
	private Deck d;
	
	@Test
	public void test() {
		assertFalse(false);
	}
	@Test
	public void testInitialize() throws FileNotFoundException{
		d=new Deck("eng");
		String s="";
		for(Card c: d.cards)
			s+=c.cardNum + " ";
		assertEquals(d.toString(),s);
	}
	@Test
	public void testGetTopCard() throws FileNotFoundException{
		d=new Deck("eng");
		assertEquals(d.cards[d.topCard],d.getTopCard());
	}
	@Test
	public void testGetTopCardShuffle() throws FileNotFoundException{
		d=new Deck("eng");
		for(int i=0; i<d.cards.length;i++)
			d.getTopCard();
		assertEquals(d.cards[d.topCard],d.getTopCard());
	}
}
