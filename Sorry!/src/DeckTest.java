import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;

/**
 * @author TeamSorryDragons
 *
 */
public class DeckTest {
	protected Deck e;
	protected Deck f;
	
	@Test
	public void test() {
		assertFalse(false);
	}
	
	@Test
	public void testInitializeEnglish() {
		e=new Deck("english");
		String s="";
		for(Card c: e.cards)
			s+=c.cardNum + " ";
		assertEquals(e.toString(),s);
	}
	@Test
	public void testGetTopCardEnglish() {
		Deck e=new Deck("english");
		assertEquals(e.cards[e.topCard],e.getTopCard());
	}
	
	@Test
	public void testGetTopCardShuffleEnglish(){
		Deck e=new Deck("english");
		for(int i=0; i<e.cards.length;i++)
			e.getTopCard();
		assertEquals(e.cards[e.topCard],e.getTopCard());
	}
	@Test
	public void testInitializeFrench() {
		Deck f=new Deck("french");
		String s="";
		for(Card c: f.cards)
			s+=c.cardNum + " ";
		
		assertEquals(f.toString(), s);
	}
	
	@Test
	public void testGetTopCardFrench(){
		Deck f=new Deck("french");
		assertEquals(f.cards[f.topCard],f.getTopCard());
	}
	@Test
	public void testGetTopCardShuffleFrench(){
		Deck f=new Deck("french");
		for(int i=0; i<f.cards.length;i++)
			f.getTopCard();
		assertEquals(f.cards[f.topCard],f.getTopCard());
	}
	@Test
	public void testThrowFileNotFoundException() throws FileNotFoundException{
		
		new Deck("spanish");
	}
	@Test
	public void testCardToString(){
		Card test = new Card(1,"This is a card");
		assertEquals("1 - This is a card", test.toString());
	}
}
