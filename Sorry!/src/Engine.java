import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Engine implements EngineInterface {
	public static final int NODE_NOT_FOUND = -128;
	public static final int SAME_NODE_SELECTED = 0;
	public static final int NO_PIECE_SELECTED = -256;
	public static final int INVALID_MOVE = -512;
	public static final int VALID_MOVE_NO_FINALIZE = -129;
	public static final int SUCCESSFUL_OPERATION = 191;
	public static final int HAS_WON = 237;
	private static HashContainer coords;
	private int remainingMoves = 0;
	protected BoardList board;
	protected BoardList backupBoard;
	protected Player owner;
	protected Piece[] pieces;// Indices 0-3 are red, Indices 4-7 are blue,
								// Indices 8-11
	// are Yellow, Indices 12-15 are green
	protected Player activePlayer;
	protected Deck deck;
	protected CircularLinkedList<Player> players;
	protected Card currentCard;

	protected boolean underTest = false;

	@SuppressWarnings("static-access")
	public Engine(BoardList board, String lang) {
		this.board = board;
		this.backupBoard = board.clone();
		this.players = new CircularLinkedList<Player>();
		this.deck = new Deck(lang);
		this.deck.shuffle();
		coords = new HashContainer();
		coords.populateCoordsMap();

	}

	@Override
	public void insertPlayer(Player bigP) {
		if (this.players.isEmpty()) {
			this.players.insertFirst(bigP);
			this.players.goToNextElement();
		} else {
			this.players.insertAfterActual(bigP);
			this.players.goToNextElement();
		}
	}

	@Override
	public void newGame() {
		this.pieces = this.board.newGame();
		this.backupBoard = this.board.clone();
	}

	public boolean isValidMove(Piece pawn, int numberMovesForward, Player player) {
		// start with a rudimentary, who owns this piece check
		if (pawn == null || pawn.col != player.getColor())
			return false;

		return true;
	}

	/**
	 * Use the original game rules to decide if a move is valid, then perform
	 * that move, allowing the nodes to do some validity checks.
	 * 
	 * @param pawn
	 * @param start
	 * @param end
	 * @param numberMovesForward
	 * @param numberMovesBackward
	 * @param player
	 * @return
	 */
	protected int checkValidityOriginalRules(Piece pawn, Node start, Node end,
			int numberMovesForward, int numberMovesBackward) {
		int moves = 0;
		int ninja_return = 0;
		int error = 1;
		switch (this.currentCard.cardNum) {
		case 2:
			// 2 forward, get another turn
			// for now, same rules as the default, but we might need a way to
			// instruct the GUI to give them another turn
			if (numberMovesForward != this.currentCard.cardNum)
				error = INVALID_MOVE;
			else {
				moves = 2;
				// advance player list so that current player gets another turn
				for (int j = 0; j < this.players.getNumberOfElements() - 1; j++) {
					this.players.goToNextElement();
				}
			}
			break;
		case 4:
			// 4 spots backward
			if (numberMovesBackward != this.currentCard.cardNum)
				error = INVALID_MOVE;
			else
				moves = -4;
			break;
		case 7:
			// 7 forward, or a split
			if (start.getPrevious() == null) {
				error = INVALID_MOVE;
				// trying to move from start
			}

			if (this.remainingMoves != 0) {
				// player is finishing a split
				if (numberMovesForward == this.remainingMoves) {
					moves = numberMovesForward;
					this.remainingMoves = 0;
				} else
					error = INVALID_MOVE;
			} else {
				// player is starting a split or using the whole card
				if (numberMovesForward == 7)
					moves = 7;
				else if (numberMovesForward < 7) {
					moves = numberMovesForward;
					this.remainingMoves = 7 - numberMovesForward;
					ninja_return = VALID_MOVE_NO_FINALIZE;
				} else
					error = INVALID_MOVE;
			}
			// not quite sure what this code is accomplishing
			break;
		case 10:
			// 10 forward, or 1 backward
			if (numberMovesForward == 10)
				moves = 10;
			else if (numberMovesBackward == 1)
				moves = -1;
			else
				error = INVALID_MOVE;
			break;
		case 11:
			// 11 spaces forward or a swap
			if (start.getPrevious() == null)
				// piece is at home, can't make the move
				error = INVALID_MOVE;
			if (numberMovesForward == 11)
				moves = 11;
			else if (end.hasPiece()
					&& start.canReceivePiece(end.firstPiece().col)) {
				moves = numberMovesForward;

			} else
				error = INVALID_MOVE;
			break;

		case 13:
			// Sorry card
			if (end.hasPiece() && start.getPrevious() == null)
				// piece is in start and the target has a piece on it
				moves = numberMovesForward;
			else
				error = INVALID_MOVE;
			break;
		default:
			// all remaining cases, simply allow move forward the number or no
			// dice
			if (numberMovesForward == this.currentCard.cardNum)
				moves = numberMovesForward;
			else
				error = INVALID_MOVE;
		}

		try {
			if (error < 1)
				return error;
			if (this.currentCard.cardNum == 13) {
				toStart(end.swap(start));
				return moves;
			}
			if (this.currentCard.cardNum == 11 && moves != 11) {
				Piece temp = end.swap(start);
				start.addPieceToPieces(temp);
				return moves;
			}
			move(moves, pawn, start);
		} catch (InvalidMoveException e) {
			return INVALID_MOVE;
		}

		if (ninja_return != 0)
			return ninja_return;
		return moves;
	}

	public boolean checkOwnerIsActive() {
		if (this.owner != null)
			return owner.getColor() == this.activePlayer.getColor();
		return true;
	}

	public int attemptMovingPawn(SorryFrame.Coordinate start,
			SorryFrame.Coordinate end) {
		Node first, second;
		try {
			first = this.convertCoordToNode(start);
			second = this.convertCoordToNode(end);
		} catch (CoordinateOffOfBoardException e) {
			return NODE_NOT_FOUND;
		}
		if (first == second)
			return SAME_NODE_SELECTED;

		int nodeCountForward = 0;
		try {
			nodeCountForward = first.countTo(second);
		} catch (NullPointerException e) {
			nodeCountForward = 0;
		}

		int nodeCountBackward = 0;
		try {
			nodeCountBackward = first.countBack(second);
		} catch (Exception e) {
			nodeCountBackward = 0;
		}
		if (!isValidMove(first.firstPiece(), nodeCountForward,
				this.activePlayer)) {
			return INVALID_MOVE;
		}

		int ret = checkValidityOriginalRules(first.firstPiece(), first, second,
				nodeCountForward, nodeCountBackward);

		int result = ret;// this.handleTurnUpdate(ret);
		return result;
	}

	private int handleTurnUpdate(int result) {
		if (result == Engine.SAME_NODE_SELECTED
				|| result == Engine.INVALID_MOVE
				|| result == Engine.NO_PIECE_SELECTED
				|| result == Engine.NODE_NOT_FOUND
				|| result == Engine.VALID_MOVE_NO_FINALIZE) {
			return result;
		} else {
			// turn is over, rotate
			if (!this.underTest) {
				this.rotatePlayers();
				this.getNextCard();
			}
			return result;
		}

	}

	@Override
	@SuppressWarnings("static-access")
	public int pawnMove(SorryFrame.Coordinate start, SorryFrame.Coordinate end) {
		// Start with error checking - are the coordinates and desired nodes
		// valid?
		if (this.checkOwnerIsActive())
			return this.attemptMovingPawn(start, end);
		return this.INVALID_MOVE;

	}

	/**
	 * Move a given piece the specified number of positions FORWARD on the
	 * board.
	 * 
	 * Don't try using negative numbers.
	 * 
	 * @param moves
	 * @param piece
	 * @throws Unstarted
	 *             if the game hasn't been generated
	 * @throws InvalidMoveException
	 */
	public void move(int moves, Piece piece, Node piecenode)
			throws InvalidMoveException {
		piecenode.removePieceFromPieces(piece);
		ArrayList<Piece> strt = piecenode.move(moves, piece);
		if (strt != null && strt.size() > 0) {
			for (int i = 0; i < strt.size(); i++) {
				toStart(strt.get(i));
			}
		}
	}

	public Node findNode(Piece piece) {
		return this.board.getCornerPointers()[0].findNodeWithPiece(piece);
	}

	public Node findNodeByPosition(int i) {
		return this.board.getCornerPointers()[0].findNodeWithPosition(i);
	}

	@Override
	public Card getNextCard() {
		this.currentCard = this.deck.getTopCard();
		return this.currentCard;

	}

	@Override
	public Card getCurrentCard() {
		return this.currentCard;
	}

	/**
	 * Helper method for moving pieces to the start position.
	 * 
	 * @param pawn
	 */
	protected void toStart(Piece pawn) {
		switch (pawn.col) {
		case red:
			this.board.getStartPointers()[0].addPieceToPieces(pawn);
			break;
		case blue:
			this.board.getStartPointers()[1].addPieceToPieces(pawn);
			break;
		case yellow:
			this.board.getStartPointers()[2].addPieceToPieces(pawn);
			break;
		case green:
			this.board.getStartPointers()[3].addPieceToPieces(pawn);
			break;
		}
	}

	/**
	 * Returns the node at the given coordinate position.
	 * 
	 * @param coordinate
	 * @return node corresponding to the given coordinate
	 */
	public Node convertCoordToNode(SorryFrame.Coordinate coordinate) {
		// TODO this
		int i = 0;
		switch (this.activePlayer.getColor()) {
		case red:
			i = 0;
			break;
		case blue:
			i = 1;
			break;
		case yellow:
			i = 2;
			break;
		case green:
			i = 3;
			break;
		}
		return this.findNodeByPosition(Engine.getNodePosition(coordinate, i));
	}

	protected static int getNodePosition(SorryFrame.Coordinate coord, int input) {
		HashMap<SorryFrame.Coordinate, Integer> tempCoordsMap = coords
				.getmap(input);

		if (tempCoordsMap.containsKey(coord))
			return tempCoordsMap.get(coord);
		throw new CoordinateOffOfBoardException("Bad coordinate access: "
				+ coord.getX() + " " + coord.getY());
	}

	/**
	 * Get the next player.
	 * 
	 */
	@Override
	public void rotatePlayers() {
		if (this.activePlayer != null)
			this.activePlayer.setActive(false);
		this.players.goToNextElement();
		this.activePlayer = this.players.getActualElementData();
		this.activePlayer.setActive(true);
		System.out.println("The current active player: "
				+ this.activePlayer.getName());

	}

	/**
	 * 
	 * Obtain the actual board for the current game session in which this game
	 * driving engine is in use. Likely to draw it for the player to witness the
	 * magnificence of this computerized Sorry! board game, which they are now
	 * playing for entertainment purposes.
	 * 
	 * @return board
	 */
	@Override
	public BoardList getActualBoard() {
		return this.board;
	}

	/**
	 * Finalize the player's turn by setting the temporary movement board to the
	 * real board. Reset the temp board.
	 * 
	 * @return if the active player won
	 * 
	 */
	@Override
	public boolean finalizeTurn() {
		// TODO implement it, when the time comes
		this.backupBoard = this.board.clone();
		this.rotatePlayers();
		this.getNextCard();
		return hasWon();
	}

	/**
	 * 
	 * Reverts the current board to the state prior to starting the current
	 * player's turn. Used in the event of a turn forfeit.
	 * 
	 */
	public void revertBoard() {
		// TODO implement once a board can be read from a file.
		this.board = this.backupBoard.clone();
		return;
	}

	public boolean hasWon() {
		int spot = 0;
		Player pl;
		if(this.owner == null){
			pl = this.activePlayer;
		}
		else{
			pl = this.owner;
		}
		switch (pl.getColor()) {
		case red:
			spot = 0;
			break;
		case blue:
			spot = 1;
			break;
		case yellow:
			spot = 2;
			break;
		case green:
			spot = 3;
			break;
		}
		Piece[] piecesInHome = this.board.getHomePointers()[spot].getPieces();
		for (int i = 0; i < 4; i++) {
			if (piecesInHome[i] == null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Player getActivePlayer() {
		// TODO Auto-generated method stub
		return this.activePlayer;
	}

	@Override
	public void load(BoardList board, BoardList clone, Piece[] pieceList) {
		this.board = board;
		this.backupBoard = clone;
		this.pieces = pieceList;

	}

	@Override
	public void save(File save) throws IOException {
		PrintWriter output = new PrintWriter(save);
		output.println(this.getActivePlayer().getName() + "|"
				+ this.getActivePlayer().getColor().toString());
		for (int i = 0; i < (this.players.getNumberOfElements() - 1); i++) {
			this.players.goToNextElement();
			this.activePlayer = this.players.getActualElementData();
			output.println(this.getActivePlayer().getName() + "|"
					+ this.getActivePlayer().getColor().toString());
		}
		this.players.goToNextElement();
		this.activePlayer = this.players.getActualElementData();
		output.println();
		output.println(this.board.toString());
		output.close();
	}

	@Override
	public void forfeit() {
		revertBoard();
		this.rotatePlayers();
		this.getNextCard();
	}

	@Override
	public void getUpdatedInfo() {
		return;
	}

	@SuppressWarnings("static-access")
	public static void main(String args[]) {
		Engine.coords = new HashContainer();
		Engine.coords.populateCoordsMap();
		for (int k = 0; k < 4; k++) {
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					try {
						int coord = Engine.getNodePosition(
								new SorryFrame.Coordinate(i, j), k);
						if (coord >= 88)
							System.out.println("Found bad one >= 88: " + i
									+ "  " + j + "  map: " + k);
						if (coord < 0)
							System.out.println("Found bad one < 0: " + i + " "
									+ j);
					} catch (CoordinateOffOfBoardException e) {
						// expected
					}
				}
			}
		}
	}

	@Override
	public Player getOwner() {
		// TODO Auto-generated method stub
		return null;
	}
}
