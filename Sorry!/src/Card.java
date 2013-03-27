/**
 * @author TeamSorryDragons
 *
 */
public class Card {

	protected int cardNum;
	protected String desc;
	public Card(int cardNumber, String description){
		desc=description;
		cardNum=cardNumber;
	}
	@Override
	public String toString(){
		return cardNum + " - " +desc;
	}
}
