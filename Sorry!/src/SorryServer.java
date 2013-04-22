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
			System.out.println("Error opening server, likely the port failed.");
			return false;
		}

	}

	public boolean closeServerConnection() {
		if (this.connector == null)
			return false;
		try {
			this.connector.close();
			return true;
		} catch (IOException exception) {
			// TODO Auto-generated catch-block stub.
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

		PrintStream out = response.getPrintStream();
		out.print("result=");
		out.print(Engine.INVALID_MOVE);
		out.flush();
		out.close();
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
