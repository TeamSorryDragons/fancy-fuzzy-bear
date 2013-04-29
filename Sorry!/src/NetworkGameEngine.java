import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
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
	public NetworkGameEngine(String loc, int port, Player own, String language)
			throws MalformedURLException {
		String serverURL = loc + ":" + port + "/";
		this.client = new HTTPClient(serverURL);
		this.owner = own;
		this.cards = new ArrayList<Card>();
		Deck deck = new Deck(language);
		for (int i = 0; i < 11; i++) {
			this.cards.add(deck.cards[i]);
		}

	}

	@Override
	public void getUpdatedInfo() {
		String status = this.client.getServerResponse("game-status");
		String[] results = status.split("\n");

		for (String msg : results) {
			if (!msg.contains("=")) {
				// have the game board
				this.board = new BoardList(msg);
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
		Card ret;
		switch (data) {
		case "1":
			ret = this.cards.get(0);
			break;
		case "2":
			ret = this.cards.get(1);
			break;
		case "3":
			ret = this.cards.get(2);
			break;
		case "4":
			ret = this.cards.get(3);
			break;
		case "5":
			ret = this.cards.get(4);
			break;
		case "7":
			ret = this.cards.get(5);
			break;
		case "8":
			ret = this.cards.get(6);
			break;
		case "10":
			ret = this.cards.get(7);
			break;
		case "11":
			ret = this.cards.get(8);
			break;
		case "12":
			ret = this.cards.get(9);
			break;
		case "13":
			ret = this.cards.get(10);
			break;
		default:
			ret = null;
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
		while (next != first) {
			if (next.equals(data)) {
				active = this.players.getActualElementData();
				break;
			}
			next = this.players.getActualElementData().getName();
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
	public void newGame() {
		// TODO Auto-generated method stub.

	}

	@Override
	public boolean isValidMove(Piece p, int i, Player pl) {
		// TODO Auto-generated method stub.
		return false;
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
		// TODO Auto-generated method stub.
	}

	@Override
	public boolean finalizeTurn() {
		String data = "user=" + this.owner.getName();
		data += "\n";
		data += "action=finalize";
		String resp = this.client.sendServerData(data);
		if (resp.contains("InvalidData")
				|| resp.contains("Unsupported server access")) {
			// server failed to use this request
			finalizeTurn(); // just try again, probably nothing
		}
		if (!resp.contains("result="))
			return false;
		try {
			int result = Integer.parseInt(resp.split("=")[1]);
			if (result == Engine.HAS_WON)
				return true;
		} catch (NumberFormatException e) {
			// did not get a valid result, should not happen
		}
		return false;
	}

	@Override
	public void revertBoard() {
		// TODO Auto-generated method stub.

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
	public Node convertCoordToNode(SorryFrame.Coordinate c) {
		// TODO Auto-generated method stub.
		return null;

	}

	@Override
	public boolean hasWon() {
		// TODO Auto-generated method stub.
		return false;
	}

	@Override
	public void move(int i, Piece p, Node n) throws InvalidMoveException {
		// TODO Auto-generated method stub.

	}

	@Override
	public Node findNode(Piece p) {
		// TODO Auto-generated method stub.
		return null;
	}

	@Override
	public Node findNodeByPosition(int i) {
		// TODO Auto-generated method stub.
		return null;
	}

	public static void main(String[] args) {
		try {
			NetworkGameEngine eng = new NetworkGameEngine(
					"http://137.112.113.203", 8080, new Player(
							Piece.COLOR.green, "bob barker"), "english");
			int resp = eng.pawnMove(new SorryFrame.Coordinate(0, 0),
					new SorryFrame.Coordinate(1, 1));
			System.out.println(resp);
		} catch (MalformedURLException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
	}

}
