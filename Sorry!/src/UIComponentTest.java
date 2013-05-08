import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

/**
 * Simple test cases to ensure the GUI component of Sorry displays the correct
 * card and player.
 * 
 * @author sturgedl. Created May 8, 2013.
 */
public class UIComponentTest {

	@Test
	public void testGUIDisplaysCard() {
		Engine testEngine = new Engine(new BoardList(), "english");
		SorryFrame testFrame = new SorryFrame("english", testEngine);
		UIComponent target = new UIComponent(30, 30, testFrame, "english");
		testEngine.activePlayer = new Player(Piece.COLOR.blue, "Dave");
		testEngine.currentCard = new Card(1, "Do cool stuff");
		testFrame.currentCard = testEngine.currentCard;
		target.update();

		assertEquals(target.playerInformation.getBackground(), Color.BLUE);
		assertEquals(target.card.getText(), "1");
		assertEquals(target.card3.getText(), "1");
		assertEquals(target.card2.getText(), "Do cool stuff");

		testFrame.currentCard = new Card(42, "Does not even exist");
		target.update();
		assertEquals(target.card.getText(), "42");
		assertEquals(target.card3.getText(), "42");
		assertEquals(target.card2.getText(), "Does not even exist");
		
		testFrame.currentCard = new Card(13, "SORRY you lose");
		target.update();
		assertEquals(target.card.getText(), "Sorry!");
		assertEquals(target.card3.getText(), "!Sorry");
		assertEquals(target.card2.getText(), "SORRY you lose");
	}

	@Test
	public void testGUIDisplaysUser() {
		Engine testEngine = new Engine(new BoardList(), "english");
		SorryFrame testFrame = new SorryFrame("english", testEngine);
		UIComponent target = new UIComponent(30, 30, testFrame, "english");
		testEngine.activePlayer = new Player(Piece.COLOR.blue, "Dave");
		testEngine.currentCard = new Card(1, "Do cool stuff");
		testFrame.currentCard = testEngine.currentCard;
		target.update();

		assertEquals(target.playerInformation.getBackground(), Color.BLUE);
		assertEquals(target.playerHolder.getText(), "Dave");

		testEngine.activePlayer = new Player(Piece.COLOR.green, "Harry Potter");
		target.update();
		assertEquals(target.playerInformation.getBackground(), Color.GREEN);
		assertEquals(target.playerHolder.getText(), "Harry Potter");
		
		testEngine.activePlayer = new Player(Piece.COLOR.yellow, "Harry Potter");
		target.update();
		assertEquals(target.playerInformation.getBackground(), Color.YELLOW);
		assertEquals(target.playerHolder.getText(), "Harry Potter");
		
		testEngine.activePlayer = new Player(Piece.COLOR.red, "Harry Potter");
		target.update();
		assertEquals(target.playerInformation.getBackground(), Color.RED);
		assertEquals(target.playerHolder.getText(), "Harry Potter");
	}

}
