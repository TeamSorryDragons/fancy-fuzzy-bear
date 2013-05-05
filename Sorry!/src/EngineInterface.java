import java.io.File;
import java.io.IOException;

public interface EngineInterface {
	void insertPlayer(Player p);

	void newGame();

	int pawnMove(SorryFrame.Coordinate c, SorryFrame.Coordinate c2);

	Card getNextCard();
	Card getCurrentCard();

	void rotatePlayers();

	BoardList getActualBoard();

	boolean finalizeTurn();

	Player getActivePlayer();

	void load(BoardList board, BoardList clone, Piece[] pieceList);

	void save(File save) throws IOException;

	void forfeit();

	void getUpdatedInfo();
}
