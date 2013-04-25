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
 * TODO Put here a description of what this class does.
 * 
 * @author sturgedl. Created Apr 22, 2013.
 */
public class SorryServer implements Container {
	public enum PerformableAction {
		FORFEIT, FINALIZE, UNSPECIFIED
	}

	private static final String USER_NAME_IDENTIFIER = "user";
	private static final String ACTION_IDENTIFIER = "desired-action";
	private static final String FIRST_COORD_IDENTIFIER = "coord1";
	private static final String SECOND_COORD_IDENTIFIER = "coord2";
	private static final String INVALID_PLAYER_MSG = "InactivePlayer";
	private static final String INVALID_DATA_MSG = "InvalidData";
	protected Engine gameModule;
	protected LinkedList<String> messages;
	protected Connection connector;

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
				out.println("Unsupported server access.  Your computer will be destroyed in 5...");
				out.flush();
				out.close();
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
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
			if (i != messages.size() - 1)
				data += ",";
		}
		if (this.messages.isEmpty())
			data += "NONE";
		out.println(data);

		data += '\n';
		data = this.gameModule.getActualBoard().toString();

		out.println(data);
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
		System.out.println("Server received input:");
		System.out.println(input);

		String[] linesIn = input.split("\n");

		System.out.println("Input lines: " + linesIn.length);

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
			performActionOnEngine(this.gameModule, postData.action);
			out.print("" + Engine.SUCCESSFUL_OPERATION);
			out.flush();
			out.close();
			return;
		}

		int result = this.gameModule.pawnMove(postData.firstCoord,
				postData.secondCoord);
		out.print("" + result);

		out.flush();
		out.close();
	}

	protected static void performActionOnEngine(EngineInterface sorryEngine,
			PerformableAction action) {
		switch (action) {
		case FINALIZE:
			sorryEngine.finalizeTurn();
			break;
		case FORFEIT:
			sorryEngine.forfeit();
			break;
		case UNSPECIFIED:
			break;
		default:
			break;
		}
	}

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

	public static PerformableAction stringToPerformable(String action) {
		switch (action.trim()) {
		case "forfeit":
			return PerformableAction.FORFEIT;
		case "finalize":
			return PerformableAction.FINALIZE;
		default:
			return PerformableAction.UNSPECIFIED;
		}
	}

	public static SorryFrame.Coordinate parseDatumToCoordinate(String datum) {
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

	public static class POSTDataContainer {
		boolean isValidData;
		String userName;
		SorryFrame.Coordinate firstCoord;
		SorryFrame.Coordinate secondCoord;
		PerformableAction action = PerformableAction.UNSPECIFIED;

		public boolean checkValidity() {
			this.isValidData = !(userName == null) && !(firstCoord == null)
					&& !(secondCoord == null);

			if (this.isValidData)
				return this.isValidData;

			this.isValidData = !(userName == null)
					&& !(action == PerformableAction.UNSPECIFIED);
			return this.isValidData;
		}

	}

	// Testing code, not actually useful code
	public static void main(String[] args) {
		try {
			Engine eng = new Engine(new BoardList(), "english");
			eng.insertPlayer(new Player(Piece.COLOR.red, "James Bond"));
			eng.insertPlayer(new Player(Piece.COLOR.blue, "Harry Potter"));
			eng.newGame();
			eng.rotatePlayers();
			SorryServer cont = new SorryServer(eng);
			cont.attemptServerStartUp(8080);
			System.out.println("Server is up!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
