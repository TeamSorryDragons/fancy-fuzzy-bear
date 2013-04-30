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
	private static HashMap<SorryFrame.Coordinate, Integer> coordsMap;
	private static HashMap<SorryFrame.Coordinate, Integer> coordsMap1;
	private static HashMap<SorryFrame.Coordinate, Integer> coordsMap2;
	private static HashMap<SorryFrame.Coordinate, Integer> coordsMap3;
	private int remainingMoves = 0;
	protected BoardList board;
	protected BoardList backupBoard;
	protected Piece[] pieces;// Indices 0-3 are red, Indices 4-7 are blue,
								// Indices 8-11
	// are Yellow, Indices 12-15 are green
	protected Player activePlayer;
	protected Deck deck;
	protected CircularLinkedList<Player> players;
	protected Card currentCard;

	public Engine(BoardList board, String lang) {
		this.board = board;
		this.backupBoard = board.clone();
		this.players = new CircularLinkedList<Player>();
		this.deck = new Deck(lang);
		this.deck.shuffle();
		populateCoordsMap();

	}

	public void insertPlayer(Player bigP) {
		if (this.players.isEmpty()) {
			this.players.insertFirst(bigP);
			this.players.goToNextElement();
		} else {
			this.players.insertAfterActual(bigP);
			this.players.goToNextElement();
		}
	}



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
			else if (end.hasPiece() && start.canReceivePiece(end.firstPiece().col)) {
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

	public int pawnMove(SorryFrame.Coordinate start, SorryFrame.Coordinate end) {
		Node first, second;
		// Start with error checking - are the coordinates and desired nodes
		// valid?
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

		return ret;
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

	public Card getNextCard() {
		this.currentCard = this.deck.getTopCard();
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
		HashMap<SorryFrame.Coordinate, Integer> tempCoordsMap = coordsMap;
		switch (input) {
		case 0:
			tempCoordsMap = coordsMap;
			break;
		case 1:
			tempCoordsMap = coordsMap1;
			break;
		case 2:
			tempCoordsMap = coordsMap2;
			break;
		case 3:
			tempCoordsMap = coordsMap3;
			break;
		}
		if (tempCoordsMap.containsKey(coord))
			return tempCoordsMap.get(coord);
		throw new CoordinateOffOfBoardException("Bad coordinate access: "
				+ coord.getX() + " " + coord.getY());
	}

	private static void populateCoordsMap() {
		coordsMap = new HashMap<SorryFrame.Coordinate, Integer>();
		coordsMap1 = new HashMap<SorryFrame.Coordinate, Integer>();
		coordsMap2 = new HashMap<SorryFrame.Coordinate, Integer>();
		coordsMap3 = new HashMap<SorryFrame.Coordinate, Integer>();
		populateCorners();
		populateStartZones();
		populateHomeZones();
		populateSafeZones();
		populateSideLines();
	}

	private static void populateSideLines() {
		mapInsert(14, 15, 1);
		mapInsert(13, 15, 2);
		mapInsert(12, 15, 9);
		mapInsert(11, 15, 10);

		mapInsert(0, 14, 23);
		mapInsert(0, 13, 24);
		mapInsert(0, 12, 31);
		mapInsert(0, 11, 32);

		mapInsert(1, 0, 45);
		mapInsert(2, 0, 46);
		mapInsert(3, 0, 53);
		mapInsert(4, 0, 54);

		mapInsert(15, 1, 67);
		mapInsert(15, 2, 68);
		mapInsert(15, 3, 75);
		mapInsert(15, 4, 76);

		mapInsert1(14, 15, 23);
		mapInsert1(13, 15, 24);
		mapInsert1(12, 15, 31);
		mapInsert1(11, 15, 32);

		mapInsert1(0, 14, 45);
		mapInsert1(0, 13, 46);
		mapInsert1(0, 12, 53);
		mapInsert1(0, 11, 54);

		mapInsert1(1, 0, 67);
		mapInsert1(2, 0, 68);
		mapInsert1(3, 0, 75);
		mapInsert1(4, 0, 76);

		mapInsert1(15, 1, 1);
		mapInsert1(15, 2, 2);
		mapInsert1(15, 3, 9);
		mapInsert1(15, 4, 10);

		mapInsert2(14, 15, 45);
		mapInsert2(13, 15, 46);
		mapInsert2(12, 15, 53);
		mapInsert2(11, 15, 54);

		mapInsert2(0, 14, 67);
		mapInsert2(0, 13, 68);
		mapInsert2(0, 12, 75);
		mapInsert2(0, 11, 76);

		mapInsert2(1, 0, 1);
		mapInsert2(2, 0, 2);
		mapInsert2(3, 0, 9);
		mapInsert2(4, 0, 10);

		mapInsert2(15, 1, 23);
		mapInsert2(15, 2, 24);
		mapInsert2(15, 3, 31);
		mapInsert2(15, 4, 32);

		mapInsert3(14, 15, 67);
		mapInsert3(13, 15, 68);
		mapInsert3(12, 15, 75);
		mapInsert3(11, 15, 76);

		mapInsert3(0, 14, 1);
		mapInsert3(0, 13, 2);
		mapInsert3(0, 12, 9);
		mapInsert3(0, 11, 10);

		mapInsert3(1, 0, 23);
		mapInsert3(2, 0, 24);
		mapInsert3(3, 0, 31);
		mapInsert3(4, 0, 32);

		mapInsert3(15, 1, 45);
		mapInsert3(15, 2, 46);
		mapInsert3(15, 3, 53);
		mapInsert3(15, 4, 54);

	}

	private static void mapInsert(int x, int y, int pos) {
		coordsMap.put(new SorryFrame.Coordinate(x, y), pos);
	}

	private static void mapInsert1(int x, int y, int pos) {
		coordsMap1.put(new SorryFrame.Coordinate(x, y), pos);
	}

	private static void mapInsert2(int x, int y, int pos) {
		coordsMap2.put(new SorryFrame.Coordinate(x, y), pos);
	}

	private static void mapInsert3(int x, int y, int pos) {
		coordsMap3.put(new SorryFrame.Coordinate(x, y), pos);
	}

	private static void populateSafeZones() {
		int redZone = 2;
		for (int i = 15; i >= 10; i--)
			coordsMap.put(new SorryFrame.Coordinate(13, i), redZone++);

		int blueZone = 24;
		for (int i = 0; i <= 5; i++)
			coordsMap.put(new SorryFrame.Coordinate(i, 13), blueZone++);

		int yellowZone = 46;
		for (int i = 0; i <= 5; i++)
			coordsMap.put(new SorryFrame.Coordinate(2, i), yellowZone++);

		int greenZone = 68;
		for (int i = 15; i >= 10; i--)
			coordsMap.put(new SorryFrame.Coordinate(i, 2), greenZone++);

		redZone = 2;
		blueZone = 24;
		yellowZone = 46;
		greenZone = 68;
		for (int i = 15; i >= 10; i--)
			coordsMap1.put(new SorryFrame.Coordinate(13, i), blueZone++);
		for (int i = 0; i <= 5; i++)
			coordsMap1.put(new SorryFrame.Coordinate(i, 13), yellowZone++);
		for (int i = 0; i <= 5; i++)
			coordsMap1.put(new SorryFrame.Coordinate(2, i), greenZone++);
		for (int i = 15; i >= 10; i--)
			coordsMap1.put(new SorryFrame.Coordinate(i, 2), redZone++);

		redZone = 2;
		blueZone = 24;
		yellowZone = 46;
		greenZone = 68;
		for (int i = 15; i >= 10; i--)
			coordsMap2.put(new SorryFrame.Coordinate(13, i), yellowZone++);
		for (int i = 0; i <= 5; i++)
			coordsMap2.put(new SorryFrame.Coordinate(i, 13), greenZone++);
		for (int i = 0; i <= 5; i++)
			coordsMap2.put(new SorryFrame.Coordinate(2, i), redZone++);
		for (int i = 15; i >= 10; i--)
			coordsMap2.put(new SorryFrame.Coordinate(i, 2), blueZone++);

		redZone = 2;
		blueZone = 24;
		yellowZone = 46;
		greenZone = 68;
		for (int i = 15; i >= 10; i--)
			coordsMap3.put(new SorryFrame.Coordinate(13, i), greenZone++);
		for (int i = 0; i <= 5; i++)
			coordsMap3.put(new SorryFrame.Coordinate(i, 13), redZone++);
		for (int i = 0; i <= 5; i++)
			coordsMap3.put(new SorryFrame.Coordinate(2, i), blueZone++);
		for (int i = 15; i >= 10; i--)
			coordsMap3.put(new SorryFrame.Coordinate(i, 2), yellowZone++);
	}

	private static void populateHomeZones() {
		int redHome = 8;
		for (int i = 14; i >= 12; i--)
			for (int j = 9; j >= 7; j--)
				coordsMap.put(new SorryFrame.Coordinate(i, j), redHome);

		int blueHome = 30;
		for (int i = 6; i <= 8; i++)
			for (int j = 14; j >= 12; j--)
				coordsMap.put(new SorryFrame.Coordinate(i, j), blueHome);

		int yellowHome = 52;
		for (int i = 1; i <= 3; i++)
			for (int j = 6; j <= 8; j++)
				coordsMap.put(new SorryFrame.Coordinate(i, j), yellowHome);

		int greenHome = 74;
		for (int i = 7; i <= 9; i++)
			for (int j = 1; j <= 3; j++)
				coordsMap.put(new SorryFrame.Coordinate(i, j), greenHome);

		redHome = 8;
		blueHome = 30;
		yellowHome = 52;
		greenHome = 74;
		for (int i = 14; i >= 12; i--)
			for (int j = 9; j >= 7; j--)
				coordsMap1.put(new SorryFrame.Coordinate(i, j), blueHome);
		for (int i = 6; i <= 8; i++)
			for (int j = 14; j >= 12; j--)
				coordsMap1.put(new SorryFrame.Coordinate(i, j), yellowHome);
		for (int i = 1; i <= 3; i++)
			for (int j = 6; j <= 8; j++)
				coordsMap1.put(new SorryFrame.Coordinate(i, j), greenHome);
		for (int i = 7; i <= 9; i++)
			for (int j = 1; j <= 3; j++)
				coordsMap1.put(new SorryFrame.Coordinate(i, j), redHome);

		redHome = 8;
		blueHome = 30;
		yellowHome = 52;
		greenHome = 74;
		for (int i = 14; i >= 12; i--)
			for (int j = 9; j >= 7; j--)
				coordsMap2.put(new SorryFrame.Coordinate(i, j), yellowHome);
		for (int i = 6; i <= 8; i++)
			for (int j = 14; j >= 12; j--)
				coordsMap2.put(new SorryFrame.Coordinate(i, j), greenHome);
		for (int i = 1; i <= 3; i++)
			for (int j = 6; j <= 8; j++)
				coordsMap2.put(new SorryFrame.Coordinate(i, j), redHome);
		for (int i = 7; i <= 9; i++)
			for (int j = 1; j <= 3; j++)
				coordsMap2.put(new SorryFrame.Coordinate(i, j), blueHome);

		redHome = 8;
		blueHome = 30;
		yellowHome = 52;
		greenHome = 74;
		for (int i = 14; i >= 12; i--)
			for (int j = 9; j >= 7; j--)
				coordsMap3.put(new SorryFrame.Coordinate(i, j), greenHome);
		for (int i = 6; i <= 8; i++)
			for (int j = 14; j >= 12; j--)
				coordsMap3.put(new SorryFrame.Coordinate(i, j), redHome);
		for (int i = 1; i <= 3; i++)
			for (int j = 6; j <= 8; j++)
				coordsMap3.put(new SorryFrame.Coordinate(i, j), blueHome);
		for (int i = 7; i <= 9; i++)
			for (int j = 1; j <= 3; j++)
				coordsMap3.put(new SorryFrame.Coordinate(i, j), yellowHome);
	}

	private static void populateStartZones() {
		int redStart = 11;
		for (int i = 10; i <= 12; i++) {
			for (int j = 12; j <= 14; j++) {
				coordsMap.put(new SorryFrame.Coordinate(i, j), redStart);
			}
		}

		int blueStart = 33;
		for (int i = 10; i <= 12; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap.put(new SorryFrame.Coordinate(j, i), blueStart);
			}
		}

		int yellowStart = 55;
		for (int i = 3; i <= 5; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap.put(new SorryFrame.Coordinate(i, j), yellowStart);
			}
		}

		int greenStart = 77;
		for (int i = 3; i <= 5; i++) {
			for (int j = 12; j <= 14; j++)
				coordsMap.put(new SorryFrame.Coordinate(j, i), greenStart);
		}

		redStart = 11;
		blueStart = 33;
		yellowStart = 55;
		greenStart = 77;
		for (int i = 10; i <= 12; i++) {
			for (int j = 12; j <= 14; j++) {
				coordsMap1.put(new SorryFrame.Coordinate(i, j), blueStart);
			}
		}
		for (int i = 10; i <= 12; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap1.put(new SorryFrame.Coordinate(j, i), yellowStart);
			}
		}
		for (int i = 3; i <= 5; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap1.put(new SorryFrame.Coordinate(i, j), greenStart);
			}
		}
		for (int i = 3; i <= 5; i++) {
			for (int j = 12; j <= 14; j++)
				coordsMap1.put(new SorryFrame.Coordinate(j, i), redStart);
		}

		redStart = 11;
		blueStart = 33;
		yellowStart = 55;
		greenStart = 77;
		for (int i = 10; i <= 12; i++) {
			for (int j = 12; j <= 14; j++) {
				coordsMap2.put(new SorryFrame.Coordinate(i, j), yellowStart);
			}
		}
		for (int i = 10; i <= 12; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap2.put(new SorryFrame.Coordinate(j, i), greenStart);
			}
		}
		for (int i = 3; i <= 5; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap2.put(new SorryFrame.Coordinate(i, j), redStart);
			}
		}
		for (int i = 3; i <= 5; i++) {
			for (int j = 12; j <= 14; j++)
				coordsMap2.put(new SorryFrame.Coordinate(j, i), blueStart);
		}

		redStart = 11;
		blueStart = 33;
		yellowStart = 55;
		greenStart = 77;
		for (int i = 10; i <= 12; i++) {
			for (int j = 12; j <= 14; j++) {
				coordsMap3.put(new SorryFrame.Coordinate(i, j), greenStart);
			}
		}
		for (int i = 10; i <= 12; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap3.put(new SorryFrame.Coordinate(j, i), redStart);
			}
		}
		for (int i = 3; i <= 5; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap3.put(new SorryFrame.Coordinate(i, j), blueStart);
			}
		}
		for (int i = 3; i <= 5; i++) {
			for (int j = 12; j <= 14; j++)
				coordsMap3.put(new SorryFrame.Coordinate(j, i), yellowStart);
		}
	}

	private static void populateCorners() {
		int redSide = 12;
		int blueSide = 34;

		for (int i = 10; i >= 0; i--) {
			coordsMap.put(new SorryFrame.Coordinate(i, 15), redSide++);
			coordsMap.put(new SorryFrame.Coordinate(0, i), blueSide++);
		}

		int greenSide = 78;
		int yellowSide = 56;
		for (int i = 5; i <= 15; i++) {
			coordsMap.put(new SorryFrame.Coordinate(i, 0), yellowSide++);
			coordsMap.put(new SorryFrame.Coordinate(15, i), greenSide++);
		}
		coordsMap.put(new SorryFrame.Coordinate(15, 15), 0);

		redSide = 12;
		blueSide = 34;
		greenSide = 78;
		yellowSide = 56;
		for (int i = 10; i >= 0; i--) {
			coordsMap1.put(new SorryFrame.Coordinate(i, 15), blueSide++);
			coordsMap1.put(new SorryFrame.Coordinate(0, i), yellowSide++);
		}
		for (int i = 5; i <= 15; i++) {
			coordsMap1.put(new SorryFrame.Coordinate(i, 0), greenSide++);
			coordsMap1.put(new SorryFrame.Coordinate(15, i), redSide++);
		}
		coordsMap1.put(new SorryFrame.Coordinate(15,0), 0);

		redSide = 12;
		blueSide = 34;
		greenSide = 78;
		yellowSide = 56;
		for (int i = 10; i >= 0; i--) {
			coordsMap2.put(new SorryFrame.Coordinate(i, 15), yellowSide++);
			coordsMap2.put(new SorryFrame.Coordinate(0, i), greenSide++);
		}
		for (int i = 5; i <= 15; i++) {
			coordsMap2.put(new SorryFrame.Coordinate(i, 0), redSide++);
			coordsMap2.put(new SorryFrame.Coordinate(15, i), blueSide++);
		}
		coordsMap2.put(new SorryFrame.Coordinate(0, 15), 0);

		redSide = 12;
		blueSide = 34;
		greenSide = 78;
		yellowSide = 56;
		for (int i = 10; i >= 0; i--) {
			coordsMap3.put(new SorryFrame.Coordinate(i, 15), greenSide++);
			coordsMap3.put(new SorryFrame.Coordinate(0, i), redSide++);
		}
		for (int i = 5; i <= 15; i++) {
			coordsMap3.put(new SorryFrame.Coordinate(i, 0), blueSide++);
			coordsMap3.put(new SorryFrame.Coordinate(15, i), yellowSide++);
		}
		coordsMap3.put(new SorryFrame.Coordinate(15, 15), 0);

	}

	/**
	 * Get the next player.
	 * 
	 */
	public void rotatePlayers() {
		if (this.activePlayer != null)
			this.activePlayer.setActive(false);
		this.players.goToNextElement();
		this.activePlayer = this.players.getActualElementData();
		this.activePlayer.setActive(true);

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
	public boolean finalizeTurn() {
		// TODO implement it, when the time comes
		this.backupBoard = this.board.clone();
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
		switch (this.activePlayer.getColor()) {
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
			this.activePlayer = this.players
					.getActualElementData();
			output.println(this.getActivePlayer().getName() + "|"
					+ this.getActivePlayer().getColor().toString());
		}
		this.players.goToNextElement();
		this.activePlayer = this.players
				.getActualElementData();
		output.println();
		output.println(this.board.toString());
		output.close();
	}

	@Override
	public void forfeit() {
		// TODO Auto-generated method stub
		revertBoard();
	}
	
	public void getUpdatedInfo(){
		return;
	}
}
