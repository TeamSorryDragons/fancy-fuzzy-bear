import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
		int port = 8080;
		while (!target.attemptServerStartUp(port)) {
			port++;
			if (port > 10000)
				fail();
		}
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
		int port = 8080;
		while (!target.attemptServerStartUp(port)) {
			port++;
			if (port > 10000)
				fail();
		}

		// int port = 8080;

		try {
			String in = "";
			URL requestUrl = new URL("http://localhost:" + port
					+ "/game-status");
			HttpURLConnection conn = (HttpURLConnection) requestUrl
					.openConnection();
			conn.setRequestMethod("GET");
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

			String[] lines = in.split("\n");
			assertEquals(lines.length, 3);
			assertTrue(lines[0].startsWith("active-user="));
			assertTrue(lines[1].startsWith("messages="));
			assertTrue(lines[1]
					.endsWith("You failed,You died,You suck,Puppies"));

		} catch (Exception exception) {
			System.err.println("Error in test set-up");
			exception.printStackTrace();
		}

		target.messages = new LinkedList<String>();
		try {
			String in = "";
			URL requestUrl = new URL("http://localhost:" + port
					+ "/game-status");
			HttpURLConnection conn = (HttpURLConnection) requestUrl
					.openConnection();
			conn.setRequestMethod("GET");
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

			String[] lines = in.split("\n");
			assertEquals(lines.length, 3);
			assertTrue(lines[0].startsWith("active-user="));
			assertTrue(lines[1].startsWith("messages="));
			assertTrue(lines[1].endsWith("NONE"));

		} catch (Exception exception) {
			System.err.println("Error in test set-up");
			exception.printStackTrace();
		}

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
		int port = 8080;
		while (!target.attemptServerStartUp(port)) {
			port++;
			if (port > 10000)
				fail();
		}

		try {
			String in = "";
			URL requestUrl = new URL("http://localhost:" + port + "/");
			HttpURLConnection conn = (HttpURLConnection) requestUrl
					.openConnection();
			conn.setRequestMethod("GET");
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

			String[] lines = in.split("\n");
			assertEquals(lines.length, 1);
			assertEquals(lines[0], "Unsupported server access.");

		} catch (Exception exception) {
			System.err.println("Error in test set-up");
			exception.printStackTrace();
		}
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
		int port = 8080;
		while (!target.attemptServerStartUp(port)) {
			port++;
			if (port > 10000)
				fail();
		}

		try {
			String in = "";
			URL requestUrl = new URL("http://localhost:" + port + "/");
			HttpURLConnection conn = (HttpURLConnection) requestUrl
					.openConnection();
			conn.setRequestMethod("DELETE");
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
			
			String[] lines = in.split("\n");
			assertEquals(lines.length, 1);
			assertEquals(lines[0],
					"Unsupported server access.  Your computer will be destroyed in 5...");

		} catch (Exception exception) {
			System.err.println("Error in test set-up");
			exception.printStackTrace();
		}
		assertTrue(target.closeServerConnection());
	}

}
