import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.util.LinkedList;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

/**
 * Server for the Sorry! game, to be used by the person host for sending and
 * receiving game updates.
 * 
 * @author sturgedl. Created Apr 22, 2013.
 */
public class SorryServer implements Container {
	/**
	 * Enumeration of the actions which need to be performed by the server.
	 * 
	 * @author sturgedl. Created Apr 28, 2013.
	 */
	public enum PerformableAction {
		/**
		 * Forfeit the active player's turn.
		 */
		FORFEIT, /**
		 * Finalize the active player's turn.
		 */
		FINALIZE, /**
		 * No action to perform.
		 */
		UNSPECIFIED
	}

	private static final String USER_NAME_IDENTIFIER = "user";
	private static final String ACTION_IDENTIFIER = "desired-action";
	private static final String FIRST_COORD_IDENTIFIER = "coord1";
	private static final String SECOND_COORD_IDENTIFIER = "coord2";
	private static final String INVALID_PLAYER_MSG = "InactivePlayer";
	private static final String INVALID_DATA_MSG = "InvalidData";
	private Engine gameModule;
	LinkedList<String> messages;
	private Connection connector;

	/**
	 * Create a SorryServer to help play Sorry! over the inter-webz. Please do
	 * not confuse with a sorry server.
	 * 
	 * @param e
	 */
	public SorryServer(Engine e) {
		this.gameModule = e;
		this.messages = new LinkedList<String>();

	}

	/**
	 * Tries to start the server, listening on the given port.
	 * 
	 * @param port
	 * @return Start up success
	 */
	public boolean attemptServerStartUp(int port) {
		try {
			Server server = new ContainerServer(this);
			this.connector = new SocketConnection(server);
			SocketAddress address = new InetSocketAddress(port);

			this.connector.connect(address);
			return true;
		} catch (Exception e) {
			System.out
					.println("Error opening server, likely the port failed on port: "
							+ port);
			return false;
		}

	}

	/**
	 * Kill the server with fire and dragons. I personally like using a
	 * broadsword and smashing the server, but fire is also an acceptable
	 * alternative.
	 * 
	 * @return successful?
	 */
	public boolean closeServerConnection() {
		if (this.connector == null)
			return false;
		try {
			this.connector.close();
			return true;
		} catch (IOException exception) {
			// was not successful, bad news bears
			return false;
		}
	}

	@Override
	public void handle(Request request, Response response) {
		try {
			String method = request.getMethod();
			if (method.equalsIgnoreCase("GET")) {
				this.handleGetRequest(request, response);
			} else if (method.equalsIgnoreCase("POST")) {
				this.handlePostRequest(request, response);
			} else {
				PrintStream out = response.getPrintStream();
				out.println("Unsupported server access.  Bad Request Type.");
				out.flush();
				out.close();
				return;
			}

		} catch (Exception e) {
			// e.printStackTrace();
			PrintStream out = null;
			try {
				out = response.getPrintStream();
			} catch (IOException exception) {
				// exception.printStackTrace();
			}
			out.println("Unsupported server access.");
			out.flush();
			out.close();
		}

	}

	/**
	 * Handle GET requests by simply giving them the current game status.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleGetRequest(Request request, Response response)
			throws IOException {
		PrintStream out = response.getPrintStream();
		if (request.getAddress().toString().equalsIgnoreCase("/game-info-full")) {
			this.serveFullGameInformation(out);
			return;
		}

		if (!request.getAddress().toString().equalsIgnoreCase("/game-status")) {
			out.println("Unsupported server access.");
			out.flush();
			out.close();
			return;
		}

		response.setContentType("text/plain; charset=utf-8");

		String data = "";
		data += URLEncoder.encode("active-user", "UTF-8")
				+ "="
				+ URLEncoder.encode(this.gameModule.activePlayer.getName(),
						"UTF-8");
		out.println(data);
		data += '\n';
		data = URLEncoder.encode("messages", "UTF-8") + "=";
		for (int i = 0; i < this.messages.size(); i++) {
			data += this.messages.get(i);
			if (i != this.messages.size() - 1)
				data += ",";
		}
		if (this.messages.isEmpty())
			data += "NONE";
		out.println(data);

		data = "";

		if (!(this.gameModule == null)
				&& !(this.gameModule.currentCard == null)) {
			data += "current-card=";
			data += this.gameModule.currentCard.cardNum;
			out.println(data);
		}

		data = this.gameModule.getActualBoard().toString();

		out.println(data);
		out.flush();
		out.close();

	}

	/**
	 * Get the players in the current game, along with their colors.
	 * 
	 * @param out
	 */
	private void serveFullGameInformation(PrintStream out) {
		CircularLinkedList<Player> players = this.gameModule.players;
		Player first = players.getActualElementData();
		out.print(first.getName() + ":" + first.getColor());
		out.print("\n");
		players.goToNextElement();
		Player next = players.getActualElementData();
		while (next != first) {
			out.print(next.getName() + ":" + next.getColor());
			out.print("\n");
			players.goToNextElement();
			next = players.getActualElementData();
		}
		out.flush();
		out.close();

	}

	/**
	 * Manages post requests to the Sorry! server application. Should determine
	 * if the POST request was valid, and attempt to make the move, and output
	 * the result of doing so.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handlePostRequest(Request request, Response response)
			throws IOException {
		String input = request.getContent();

		String[] linesIn = input.split("\n");

		PrintStream out = response.getPrintStream();

		POSTDataContainer postData = parseServerInput(linesIn);
		if (!postData.isValidData) {
			out.println("result=" + INVALID_DATA_MSG);
			out.flush();
			out.close();
			return;
		}

		if (!postData.userName.equals(this.gameModule.activePlayer.getName())) {
			out.println("result=" + INVALID_PLAYER_MSG);
			out.flush();
			out.close();
			return;
		}

		out.print("result=");
		if (postData.action != PerformableAction.UNSPECIFIED) {
			int result = performActionOnEngine(this.gameModule, postData.action);
			out.print("" + result);
			out.flush();
			out.close();
			return;
		}

		int result = this.gameModule.attemptMovingPawn(postData.firstCoord,
				postData.secondCoord);
		out.print("" + result);

		out.flush();
		out.close();
	}

	/**
	 * Performs a given action on the current game engine.
	 * 
	 * @param sorryEngine
	 * @param action
	 * @return result
	 */
	protected static int performActionOnEngine(EngineInterface sorryEngine,
			PerformableAction action) {
		switch (action) {
		case FINALIZE:
			boolean result = sorryEngine.finalizeTurn();
			if (result)
				return Engine.HAS_WON;
			break;
		case FORFEIT:
			sorryEngine.forfeit();
			break;
		case UNSPECIFIED:
			break;
		default:
			break;
		}
		return Engine.SUCCESSFUL_OPERATION;
	}

	/**
	 * Takes the input the server has received, parses it, and stores the data
	 * in a container.
	 * 
	 * @param input
	 * @return parsed data
	 * @throws IllegalArgumentException
	 */
	protected static POSTDataContainer parseServerInput(String[] input)
			throws IllegalArgumentException {
		POSTDataContainer data = new POSTDataContainer();
		data.isValidData = false;
		for (String datum : input) {
			if (datum == null)
				continue;
			int breakIndex = datum.indexOf("=");
			if (breakIndex == -1)
				throw new IllegalArgumentException(
						"Invalid data line: no = for data entry, " + datum);
			String prefix = datum.substring(0, breakIndex);
			switch (prefix) {
			case USER_NAME_IDENTIFIER:
				if (data.userName == null) {
					String name = datum.substring(datum.indexOf("=") + 1)
							.trim();
					if (name.equals(""))
						throw new IllegalArgumentException(
								"Error in user name parse: no user name given");
					data.userName = name;
				} else
					throw new IllegalArgumentException(
							"Error in user name parse: Over defined");
				break;

			case FIRST_COORD_IDENTIFIER:
				if (data.firstCoord == null) {
					String coord = datum.substring(datum.indexOf("=") + 1);
					data.firstCoord = parseDatumToCoordinate(coord);
				} else
					throw new IllegalArgumentException(
							"First coordinate over-defined: " + datum);
				break;

			case SECOND_COORD_IDENTIFIER:
				if (data.secondCoord == null) {
					String coord = datum.substring(datum.indexOf("=") + 1);
					data.secondCoord = parseDatumToCoordinate(coord);
				} else
					throw new IllegalArgumentException(
							"Second coordinate over-defined: " + datum);
				break;

			case ACTION_IDENTIFIER:
				if (data.action == PerformableAction.UNSPECIFIED) {
					data.action = stringToPerformable(datum.substring(datum
							.indexOf("=") + 1));
				} else
					throw new IllegalArgumentException("Action over-defined: "
							+ datum);
				break;

			default:
				throw new IllegalArgumentException(
						"Invalid POST data, unknown identifier used for data entry: "
								+ datum);
			}
		}
		data.checkValidity();
		return data;
	}

	/**
	 * Convert a string to its corresponding action.
	 * 
	 * @param action
	 * @return
	 */
	protected static PerformableAction stringToPerformable(String action) {
		switch (action.trim()) {
		case "forfeit":
			return PerformableAction.FORFEIT;
		case "finalize":
			return PerformableAction.FINALIZE;
		default:
			return PerformableAction.UNSPECIFIED;
		}
	}

	/**
	 * Converts a coordinate, as a string of (x, y), to a SorryFrame Coordinate.
	 * 
	 * @param datum
	 * @return
	 */
	protected static SorryFrame.Coordinate parseDatumToCoordinate(String datum) {
		int x, y;

		if (!(datum.startsWith("(") && datum.endsWith(")")))
			throw new IllegalArgumentException("Malformed coordinate: " + datum);
		String trimmedData;
		trimmedData = datum.replaceAll(" ", "");
		trimmedData = trimmedData.replaceAll("\\(", "");
		trimmedData = trimmedData.replaceFirst("\\)", "");
		String[] nums = trimmedData.split(",");
		if (nums.length != 2)
			throw new IllegalArgumentException(
					"Malformed coordinate, too many numbers: " + datum);

		try {
			x = Integer.parseInt(nums[0]);
			y = Integer.parseInt(nums[1]);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Malformed coordinates, not given stricly numbers: "
							+ datum);
		}

		SorryFrame.Coordinate ret = new SorryFrame.Coordinate(x, y);
		return ret;
	}

	/**
	 * Class to contain data received by the server.
	 * 
	 * @author sturgedl. Created Apr 28, 2013.
	 */
	public static class POSTDataContainer {
		@SuppressWarnings("javadoc")
		boolean isValidData;
		@SuppressWarnings("javadoc")
		String userName;
		@SuppressWarnings("javadoc")
		SorryFrame.Coordinate firstCoord;
		@SuppressWarnings("javadoc")
		SorryFrame.Coordinate secondCoord;
		@SuppressWarnings("javadoc")
		PerformableAction action = PerformableAction.UNSPECIFIED;

		/**
		 * Check if the data contain has valid data for game interaction.
		 * 
		 * @return validity
		 */
		public boolean checkValidity() {
			this.isValidData = !(this.userName == null)
					&& !(this.firstCoord == null)
					&& !(this.secondCoord == null);

			if (this.isValidData)
				return this.isValidData;

			this.isValidData = !(this.userName == null)
					&& !(this.action == PerformableAction.UNSPECIFIED);
			return this.isValidData;
		}

	}

	/**
	 * 
	 * Main to allow the server to function as a stand alone entity, mainly for
	 * testing purposes. NOT used by the actual game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Engine eng = new Engine(new BoardList(), "english");
			eng.insertPlayer(new Player(Piece.COLOR.red, "James Bond"));
			eng.insertPlayer(new Player(Piece.COLOR.blue, "Harry Potter"));
			eng.newGame();
			eng.currentCard = new Card(2, "TEST CARD");
			eng.rotatePlayers();
			SorryServer cont = new SorryServer(eng);
			cont.attemptServerStartUp(8080);
			System.out.println("Server is up!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
