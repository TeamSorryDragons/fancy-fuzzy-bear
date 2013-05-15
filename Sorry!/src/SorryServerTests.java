import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

import org.junit.Test;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author sturgedl. Created Apr 22, 2013.
 */
public class SorryServerTests {

	@Test
	public void testServerInstantiates() {
		SorryServer target = new SorryServer(null);
		assertNotNull(target);
		assertFalse(target.closeServerConnection());
	}

	@Test
	public void testServerStarts() {
		SorryServer target = new SorryServer(null);
		startUpServerOnLocalHost(target);
		assertTrue(target.closeServerConnection());
	}
	
	@Test
	public void testCloseUnopenServer() {
		SorryServer target = new SorryServer(null);
		assertFalse(target.closeServerConnection());
		this.startUpServerOnLocalHost(target);
		assertTrue(target.closeServerConnection());
	}

	@Test
	public void testGetGameStatus() {
		Engine e = new Engine(new BoardList(), "english");
		e.insertPlayer(new Player(Piece.COLOR.red, "Dave"));
		e.insertPlayer(new Player(Piece.COLOR.blue, "Jim"));

		e.newGame();
		e.rotatePlayers();

		SorryServer target = new SorryServer(e);
		target.messages = new LinkedList<String>();
		target.messages.add("You failed");
		target.messages.add("You died");
		target.messages.add("You suck");
		target.messages.add("Puppies");
		int port = startUpServerOnLocalHost(target);

		String response = this.requestServerStatus("http://localhost:" + port
				+ "/game-status", "GET");

		String[] lines = response.split("\n");
		assertEquals(lines.length, 3);
		assertTrue(lines[0].startsWith("active-user="));
		assertTrue(lines[1].startsWith("messages="));
		assertTrue(lines[1].contains("You failed,You died,You suck,Puppies"));

		target.messages = new LinkedList<String>();
		String in = this.requestServerStatus("http://localhost:" + port
				+ "/game-status", "GET");
		lines = in.split("\n");
		assertEquals(lines.length, 3);
		assertTrue(lines[0].startsWith("active-user="));
		assertTrue(lines[1].startsWith("messages="));
		assertTrue(lines[1].endsWith("NONE"));

		assertTrue(target.closeServerConnection());

	}

	@Test
	public void testBadServerGetRequest() {
		Engine e = new Engine(new BoardList(), "english");
		e.insertPlayer(new Player(Piece.COLOR.red, "Dave"));
		e.insertPlayer(new Player(Piece.COLOR.blue, "Jim"));

		e.newGame();
		e.rotatePlayers();

		SorryServer target = new SorryServer(e);
		target.messages = new LinkedList<String>();
		int port = this.startUpServerOnLocalHost(target);
		String in = this.requestServerStatus("http://localhost:" + port + "/",
				"GET");

		String[] lines = in.split("\n");
		assertEquals(lines.length, 1);
		assertEquals(lines[0], "Unsupported server access.");

		assertTrue(target.closeServerConnection());

	}

	@Test
	public void testGetCardNumber() {
		Engine e = new Engine(new BoardList(), "english");
		e.underTest = true;
		e.insertPlayer(new Player(Piece.COLOR.red, "Dave"));
		e.insertPlayer(new Player(Piece.COLOR.blue, "Jim"));

		e.newGame();
		e.rotatePlayers();
		e.currentCard = new Card(2, "TEST");

		SorryServer target = new SorryServer(e);
		target.messages = new LinkedList<String>();
		int port = this.startUpServerOnLocalHost(target);
		String in = this.requestServerStatus("http://localhost:" + port
				+ "/game-status", "GET");

		String[] lines = in.split("\n");
		assertEquals(lines.length, 4);
		assertTrue(lines[0].contains("active-user="));
		assertTrue(lines[1].contains("messages="));
		assertTrue(lines[2].contains("current-card=2"));

		assertTrue(target.closeServerConnection());
	}

	@Test
	public void testFetchFullGameInfo() {
		Engine e = new Engine(new BoardList(), "english");
		e.insertPlayer(new Player(Piece.COLOR.red, "Dave"));
		e.insertPlayer(new Player(Piece.COLOR.blue, "Jim"));

		e.newGame();
		e.rotatePlayers();

		SorryServer target = new SorryServer(e);
		target.messages = new LinkedList<String>();
		int port = this.startUpServerOnLocalHost(target);
		String in = this.requestServerStatus("http://localhost:" + port
				+ "/game-info-full", "GET");

		String[] lines = in.split("\n");
		assertEquals(lines.length, 2);
		assertEquals(lines[0], "Dave:red");
		assertEquals(lines[1], "Jim:blue");

		assertTrue(target.closeServerConnection());

	}

	@Test
	public void testBadServerRequest() {
		Engine e = new Engine(new BoardList(), "english");
		e.insertPlayer(new Player(Piece.COLOR.red, "Dave"));
		e.insertPlayer(new Player(Piece.COLOR.blue, "Jim"));

		e.newGame();
		e.rotatePlayers();

		SorryServer target = new SorryServer(e);
		target.messages = new LinkedList<String>();
		int port = this.startUpServerOnLocalHost(target);

		String in = this.requestServerStatus("http://localhost:" + port + "/",
				"GET");

		String[] lines = in.split("\n");
		assertEquals(lines.length, 1);
		assertEquals(lines[0], "Unsupported server access.");

		in = this.requestServerStatus("http://localhost:" + port + "/",
				"DELETE");
		assertEquals(in.trim(), "Unsupported server access.  Bad Request Type.");

		assertTrue(target.closeServerConnection());
	}

	@Test
	public void testSendIncompleteData() {
		Engine e = new Engine(new BoardList(), "english");
		e.insertPlayer(new Player(Piece.COLOR.red, "Dave"));
		e.insertPlayer(new Player(Piece.COLOR.blue, "Jim"));

		e.newGame();
		e.rotatePlayers();

		SorryServer target = new SorryServer(e);
		target.messages = new LinkedList<String>();
		int port = this.startUpServerOnLocalHost(target);

		String in = this.sendDataToServer("user=James Dean",
				"http://localhost:" + port + "/", "POST");
		assertEquals(in.trim(), "result=InvalidData");
	}

	@Test
	public void testRequestPawnMovement() {
		Engine e = new Engine(new BoardList(), "english");
		e.insertPlayer(new Player(Piece.COLOR.red, "Dave"));
		e.insertPlayer(new Player(Piece.COLOR.blue, "Jim"));

		e.newGame();
		e.rotatePlayers();

		SorryServer target = new SorryServer(e);
		target.messages = new LinkedList<String>();
		int port = this.startUpServerOnLocalHost(target);

		String in = this.sendDataToServer(
				"user=James Bond\ncoord1=(0,0)\ncoord2=(1,1)",
				"http://localhost:" + port + "/", "POST");

		String[] lines = in.split("\n");
		assertEquals(lines.length, 1);
		assertEquals(lines[0], "result=InactivePlayer");

		in = this.sendDataToServer("user=Dave\ncoord1=(0,0)\ncoord2=(0,1)",
				"http://localhost:" + port + "/", "POST");

		lines = in.split("\n");
		assertEquals(lines.length, 1);
		assertEquals(lines[0], "result=" + Engine.INVALID_MOVE);

		in = this.sendDataToServer("user=Dave\ncoord1=(0,0)\ncoord2=(1,1)",
				"http://localhost:" + port + "/", "POST");

		lines = in.split("\n");
		assertEquals(lines.length, 1);
		assertEquals(lines[0], "result=" + Engine.NODE_NOT_FOUND);

		in = this.sendDataToServer("user=Dave\ncoord1=(0,0)\ncoord2=(0,0)",
				"http://localhost:" + port + "/", "POST");

		lines = in.split("\n");
		assertEquals(lines.length, 1);
		assertEquals(lines[0], "result=" + Engine.SAME_NODE_SELECTED);

		assertTrue(target.closeServerConnection());
	}

	@Test
	public void testRequestPawnMovementBadInput() {
		Engine e = new Engine(new BoardList(), "english");
		e.insertPlayer(new Player(Piece.COLOR.red, "Dave"));
		e.insertPlayer(new Player(Piece.COLOR.blue, "Jim"));

		e.newGame();
		e.rotatePlayers();

		SorryServer target = new SorryServer(e);
		target.messages = new LinkedList<String>();
		int port = this.startUpServerOnLocalHost(target);

		String in = this.sendDataToServer(
				"user=James Bond\ncoord1=(0,0\ncoord2=(1,1)",
				"http://localhost:" + port + "/", "POST");

		String[] lines = in.split("\n");
		assertEquals(lines.length, 1);
		assertEquals(lines[0], "Unsupported server access.");

		assertTrue(target.closeServerConnection());
	}

	@Test
	public void testPerformGameAction() {
		Engine e = new Engine(new BoardList(), "english");
		e.insertPlayer(new Player(Piece.COLOR.red, "Dave"));
		e.insertPlayer(new Player(Piece.COLOR.blue, "Jim"));

		e.newGame();
		e.rotatePlayers();

		SorryServer target = new SorryServer(e);
		int port = this.startUpServerOnLocalHost(target);

		String in = this.sendDataToServer(
				"user=James Bond\ndesired-action=forfeit", "http://localhost:"
						+ port + "/", "POST");

		String[] lines = in.split("\n");
		assertEquals(lines.length, 1);
		assertEquals(lines[0], "result=InactivePlayer");

		in = this.sendDataToServer("user=Dave\ndesired-action=forfeit",
				"http://localhost:" + port + "/", "POST");

		lines = in.split("\n");
		assertEquals(lines.length, 1);
		assertEquals(lines[0], "result=191");

		in = this.sendDataToServer("user=Dave\ndesired-action=finalize",
				"http://localhost:" + port + "/", "POST");
		lines = in.split("\n");
		assertEquals(lines.length, 1);
		assertEquals(lines[0], "result=InactivePlayer");

		in = this.sendDataToServer(
				"user=Dave\ndesired-action=finalize\ndesired-action=forfeit",
				"http://localhost:" + port + "/", "POST");
		lines = in.split("\n");
		assertEquals(lines.length, 1);
		assertEquals(lines[0], "Unsupported server access.");
		
		e.activePlayer = new Player(Piece.COLOR.green, "James");
		in = this.sendDataToServer(
				"user=James\ndesired-action=finalize\ndesired-action=forfeit",
				"http://localhost:" + port + "/", "POST");
		lines = in.split("\n");
		assertEquals(lines.length, 1);
		assertEquals(lines[0], ""+Engine.SUCCESSFUL_OPERATION);
		

		assertTrue(target.closeServerConnection());
	}

	@Test
	public void testPerformingUnspecifiedAction() {
		Engine e = new Engine(new BoardList(), "english");
		e.insertPlayer(new Player(Piece.COLOR.red, "Dave"));
		e.insertPlayer(new Player(Piece.COLOR.blue, "Jim"));

		e.newGame();
		e.rotatePlayers();

		int res = SorryServer.performActionOnEngine(e,
				SorryServer.PerformableAction.UNSPECIFIED);
		assertEquals(res, Engine.SUCCESSFUL_OPERATION);
	}

	private String sendDataToServer(String data, String addr, String reqType) {
		try {
			String in = "";
			URL requestUrl = new URL(addr);
			HttpURLConnection conn = (HttpURLConnection) requestUrl
					.openConnection();
			conn.setRequestMethod(reqType);

			conn.setDoOutput(true);

			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream());

			out.write(data);
			out.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			StringBuffer sb = new StringBuffer();
			while (in != null) {
				in = br.readLine();
				if (in != null)
					sb.append(in + '\n');
			}
			in = sb.toString();
			br.close();
			return in;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

	private String requestServerStatus(String request, String reqType) {
		try {
			String in = "";
			URL requestUrl = new URL(request);
			HttpURLConnection conn = (HttpURLConnection) requestUrl
					.openConnection();
			conn.setRequestMethod(reqType);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			StringBuffer sb = new StringBuffer();
			while (in != null) {
				in = br.readLine();
				if (in != null)
					sb.append(in + '\n');
			}
			in = sb.toString();
			br.close();
			return in;
		} catch (Exception exception) {
			System.err.println("Error in test set-up");
			exception.printStackTrace();
		}
		return null;
	}

	private int startUpServerOnLocalHost(SorryServer target) {
		int port = 8080;
		while (!target.attemptServerStartUp(port)) {
			port++;
			if (port > 10000)
				fail();
		}
		return port;
	}

}
