import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Implements the client side of the SorryServer, allowing Sorry! games to
 * extend over network protocol.
 * 
 * @author sturgedl. Created Apr 28, 2013.
 */
public class NetworkGameEngine implements EngineInterface {
	protected BoardList board;
	protected Player activePlayer;
	protected CircularLinkedList<Player> players;
	protected Card currentCard;
	protected IHTTPClient client;
	protected ArrayList<Card> cards;
	protected Player owner;

	/**
	 * Construct a new network game engine, based on a given URL with a port
	 * number.
	 * 
	 * @throws MalformedURLException
	 * 
	 */
	public NetworkGameEngine(String loc, int port, Player own, String language) {
		String serverURL = loc + ":" + port + "/";
		this.client = new HTTPClient(serverURL);
		this.owner = own;
		this.cards = new ArrayList<Card>();
		Deck deck = new Deck(language);
		for (int i = 0; i < 11; i++) {
			this.cards.add(deck.cards[i]);
		}
		this.players = new CircularLinkedList<Player>();
		this.board = new BoardList();

	}

	/**
	 * 
	 * Get the players participating in the current game hosted at the current
	 * client.
	 * 
	 * @return all of the players
	 */
	public ArrayList<Player> fetchAllPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		String response = this.client.getServerResponse("game-info-full");
		String[] results = response.split("\n");
		for (String msg : results) {
			if (!msg.contains(":"))
				continue;
			String name = msg.split(":")[0];
			String color = msg.split(":")[1];
			Player created = new Player(stringColorToActualColor(color), name);
			players.add(created);
		}

		for (Player p : players) {
			this.insertPlayer(p);
		}

		return players;
	}

	private Piece.COLOR stringColorToActualColor(String col) {
		switch (col) {
		case "red":
			return Piece.COLOR.red;
		case "blue":
			return Piece.COLOR.blue;
		case "yellow":
			return Piece.COLOR.yellow;
		case "green":
			return Piece.COLOR.green;
		case "colorless":
			return Piece.COLOR.colorless;
		default:
			return Piece.COLOR.colorless;
		}
	}

	@Override
	public void getUpdatedInfo() {
		String status = this.client.getServerResponse("game-status");
		String[] results = status.split("\n");

		for (String msg : results) {
			if (!msg.contains("=")) {
				// have the game board
				try {
					this.board = new BoardList(msg);
				} catch (Exception e) {
					// building the board failed, probably bad input
					// make no changes, hopefully it's corrected the next
					// iteration
				}
				continue;
			}
			String prefix = msg.split("=")[0];
			String post = msg.split("=")[1];
			switch (prefix) {
			case "active-user":
				this.activePlayer = fetchPlayer(post);
				break;
			case "current-card":
				this.currentCard = fetchCard(post);
				break;
			case "messages":
				// TODO fetch messages
				break;
			}
		}

	}

	/**
	 * Get the correct card given the card's number.
	 * 
	 * @param card
	 *            num
	 * @return
	 */
	private Card fetchCard(String data) {
		Card ret = null;
		try {
			int cardNum = Integer.parseInt(data);
			if (cardNum < 6)
				ret = this.cards.get(cardNum - 1);
			else if (cardNum < 9 && cardNum != 6)
				ret = this.cards.get(cardNum - 2);
			else if (cardNum < 14 && cardNum != 9 && cardNum != 6)
				ret = this.cards.get(cardNum - 3);
		} catch (NumberFormatException e) {
		}
		return ret;
	}

	/**
	 * Get the player based on the user name.
	 * 
	 * @param player
	 *            name
	 * @return
	 */
	private Player fetchPlayer(String data) {
		Player active = null;
		String first = this.players.getActualElementData().getName();
		if (first.equals(data)) {
			active = this.players.getActualElementData();
			return active;
		}
		this.players.goToNextElement();
		String next = "";
		Player test = this.players.getActualElementData();
		while (next != first) {
			if (next.equals(data)) {
				active = test;
				break;
			}
			test = this.players.getActualElementData();
			next = test.getName();

			this.players.goToNextElement();
		}
		return active;
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
	public int pawnMove(SorryFrame.Coordinate c, SorryFrame.Coordinate c2) {
		String firstCoord = "coord1=";
		firstCoord += "(" + c.getX() + "," + c.getY() + ")";
		String secondCoord = "coord2=";
		secondCoord += "(" + c2.getX() + "," + c2.getY() + ")";
		String sendData = "user=" + this.owner.getName();
		sendData += "\n";
		sendData += firstCoord;
		sendData += "\n";
		sendData += secondCoord;

		String servResp = this.client.sendServerData(sendData);
		if (!servResp.contains("result="))
			return Engine.INVALID_MOVE;
		servResp = servResp.split("=")[1];
		try {
			int resp = Integer.parseInt(servResp);
			return resp;
		} catch (NumberFormatException e) {
			return Engine.INVALID_MOVE;
		}
	}

	@Override
	public Card getNextCard() {
		return this.currentCard;
	}

	/**
	 * Fetch the Sorry! playing card which is currently active for the currently
	 * active player who needs to play according to the rules as determined by
	 * this card which is now in play for that player.
	 * 
	 * @return Card
	 */
	public Card getCurrentCard() {
		return this.currentCard;
	}

	@Override
	public BoardList getActualBoard() {
		// TODO Auto-generated method stub.
		return this.board;
	}

	@Override
	public Player getActivePlayer() {
		// TODO Auto-generated method stub.
		return this.activePlayer;
	}

	@Override
	public void forfeit() {
		int result = sendServerAction("forfeit");
		if (result != Engine.SUCCESSFUL_OPERATION) {
			// failed
			this.forfeit();
		}
	}

	private int sendServerAction(String action) {
		String data = "user=" + this.owner.getName();
		data += "\n";
		data += "desired-action=" + action;
		String resp = this.client.sendServerData(data);
		if (!resp.contains("result="))
			return -1;
		try {
			int result = Integer.parseInt(resp.split("=")[1]);
			return result;
		} catch (NumberFormatException e) {
			// did not get a valid result, should not happen
		}
		return -1;
	}

	@Override
	public boolean finalizeTurn() {
		int result = sendServerAction("finalize");
		if (result == Engine.HAS_WON)
			return true;
		if (result != Engine.SUCCESSFUL_OPERATION) {
			// Houston, we have a problem
			this.finalizeTurn();
		}
		return false;
	}

	@Override
	public void load(BoardList board, BoardList clone, Piece[] pieceList) {
		// TODO Auto-generated method stub.

	}

	@Override
	public void rotatePlayers() {
		// TODO Auto-generated method stub.
	}

	/**
	 * Following methods unused by interface, eventually deleted (most likely).
	 */

	@Override
	public void save(File save) throws IOException {
		// TODO Auto-generated method stub.

	}

	@Override
	public void newGame() {
		// TODO Auto-generated method stub.

	}

	public static void main(String[] args) {
		BoardList bs = new BoardList("hello world");
		System.out.println(bs.toString());

		NetworkGameEngine eng = new NetworkGameEngine("http://137.112.113.203",
				8080, new Player(Piece.COLOR.green, "bob barker"), "english");
		int resp = eng.pawnMove(new SorryFrame.Coordinate(0, 0),
				new SorryFrame.Coordinate(1, 1));
		System.out.println(resp);

	}

}
