public interface EngineInterface {
	void insertPlayer(Player p);

	void newGame();

	boolean isValidMove(Piece p, int i, Player pl);

	int pawnMove(SorryFrame.Coordinate c, SorryFrame.Coordinate c2);

	void move(int i, Piece p, Node n) throws InvalidMoveException;

	Node findNode(Piece p);

	Node findNodeByPosition(int i);

	Card getNextCard();

	Node convertCoordToNode(SorryFrame.Coordinate c);

	void rotatePlayers();

	BoardList getActualBoard();

	boolean finalizeTurn();

	void revertBoard();

	boolean hasWon();
}
