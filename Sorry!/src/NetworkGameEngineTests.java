import static org.junit.Assert.*;

import java.net.MalformedURLException;

import org.junit.Test;

/**
 * Test cases for the network game engine
 * 
 * @author sturgedl. Created Apr 29, 2013.
 */
public class NetworkGameEngineTests {

	@Test
	public void testNetworkGameEngineInstantiates() {
		NetworkGameEngine target;
		target = new NetworkGameEngine("", 0, null, "english");
		assertNotNull(target);
	}

	@Test
	public void testNetworkGameEngineConstructorPopulatesFields() {
		String url = "http://localhost";
		Player testP = new Player(Piece.COLOR.yellow, "Harry Potter");
		int port = 8080;
		NetworkGameEngine target = null;
		target = new NetworkGameEngine(url, port, testP, "english");
		assertEquals(target.owner, testP);
		assertEquals(target.client.getServerURL(), url + ":" + port + "/");

		for (int i = 0; i < 5; i++)
			assertEquals(target.cards.get(i).cardNum, i + 1);
		assertEquals(target.cards.get(target.cards.size() - 1).cardNum, 13);
	}

	@Test
	public void testNetworkGameEngineGetsActivePlayer() {
		Player whale = new Player(Piece.COLOR.blue, "Whale Rider");
		Player buff = new Player(Piece.COLOR.red, "Buffalo");
		String pre = "active-user=Buffalo\n";
		MockClient fakeServer = new MockClient(pre, "", "");

		NetworkGameEngine target = new NetworkGameEngine("", 0, whale,
				"english");
		target.insertPlayer(whale);
		target.insertPlayer(buff);
		target.client = fakeServer;
		target.getUpdatedInfo();
		assertEquals(buff, target.getActivePlayer());

		fakeServer = new MockClient("active-user=Whale Rider", "", "");
		target.client = fakeServer;
		target.getUpdatedInfo();
		assertEquals(whale, target.getActivePlayer());

		Player harry = new Player(Piece.COLOR.green, "Harry Potter");
		Player james = new Player(Piece.COLOR.blue, "James Bond");
		target.insertPlayer(harry);
		target.insertPlayer(james);

		fakeServer = new MockClient("active-user=James Bond", "", "");
		target.client = fakeServer;
		target.getUpdatedInfo();
		assertEquals(james, target.getActivePlayer());

		fakeServer = new MockClient("active-user=Buffalo", "", "");
		target.client = fakeServer;
		target.getUpdatedInfo();
		assertEquals(buff, target.getActivePlayer());
		
		fakeServer.get = "active-user=Micheal Jackson";
		target.getUpdatedInfo();
		assertNull(target.getActivePlayer());
	}

	@Test
	public void testNetworkGameEngineGetsCorrectCard() {
		Player owner = new Player(Piece.COLOR.yellow, "Captain Apollo");
		NetworkGameEngine target = new NetworkGameEngine("", 0, owner,
				"english");
		MockClient fakeServer = new MockClient("current-card=1", "", "");
		target.client = fakeServer;

		target.getUpdatedInfo();
		assertEquals(target.getCurrentCard().cardNum, 1);

		for (int i = 2; i < 14; i++) {
			fakeServer = new MockClient("current-card=" + i, "", "");
			target.client = fakeServer;
			target.getUpdatedInfo();
			if (i == 6 || i == 9)
				assertNull(target.getCurrentCard());
			else
				assertEquals(target.getCurrentCard().cardNum, i);
		}

		fakeServer.get = "current-card=15";
		target.getUpdatedInfo();
		assertNull(target.getNextCard());

		fakeServer.get = "current-card=BILBOBAGGINS";
		target.getUpdatedInfo();
		assertNull(target.getNextCard());
	}

	@Test
	public void testNetworkGameEngineUpdatesBoard() {
		Player owner = new Player(Piece.COLOR.green, "Lady Gaga");
		NetworkGameEngine target = new NetworkGameEngine("", 0, owner,
				"english");

		MockClient fakeServer = new MockClient("", "", "");
		target.client = fakeServer;
		target.getUpdatedInfo();
		assertEquals(target.getActualBoard().toString(),
				new BoardList().toString());

		String cleanBoard = new BoardList().toString();

		fakeServer = new MockClient(cleanBoard, "", "");
		target.client = fakeServer;
		target.getUpdatedInfo();
		assertEquals(target.getActualBoard().toString(), cleanBoard);

		String inUseBoard = "hrsn|rsn|rsf|rsf|rsf|rsf|rsf|rmn0|rsn|rsn|rmn3|nn|nn|nn|nn|hrsnr|rsn|rsn|rsn|rsn|nn|nn|hbsn|bsn|bsf|bsf|bsf|bsf|bsf|bmn0|bsn|bsn|bmn3|nn|nn|nn|nn|hbsn|bsn|bsn|bsn|bsn|nn|nn|hysn|ysn|ysf|ysf|ysf|ysf|ysf|ymn0|ysn|ysn|ymn4|nn|nn|nn|nnb|hysn|ysn|ysn|ysn|ysn|nn|nn|hgsn|gsn|gsf|gsf|gsf|gsf|gsf|gmn0|gsn|gsn|gmn3|nng|nn|nn|nn|hgsn|gsn|gsn|gsn|gsn|nn|nn|";

		fakeServer = new MockClient(inUseBoard, "", "");
		target.client = fakeServer;
		target.getUpdatedInfo();
		assertEquals(target.getActualBoard().toString(), inUseBoard);
	}

	@Test
	public void testNetworkGameEngineSendsFinalizeTurnRequest() {
		Player owner = new Player(Piece.COLOR.green, "Lady Gaga");
		NetworkGameEngine target = new NetworkGameEngine("", 0, owner,
				"english");

		MockClient fakeServer = new MockClient("", "result="
				+ Engine.SUCCESSFUL_OPERATION, "");
		target.client = fakeServer;
		assertFalse(target.finalizeTurn());
		assertEquals(fakeServer.received,
				"user=Lady Gaga\ndesired-action=finalize");

		Player jacque = new Player(Piece.COLOR.blue, "Jacque");
		target.owner = jacque;
		assertFalse(target.finalizeTurn());
		assertEquals(fakeServer.received,
				"user=Jacque\ndesired-action=finalize");

		fakeServer.post = "result=" + Engine.HAS_WON;
		assertTrue(target.finalizeTurn());
		assertEquals(fakeServer.received,
				"user=Jacque\ndesired-action=finalize");

		fakeServer.post = "result=" + Engine.SUCCESSFUL_OPERATION;
		assertFalse(target.finalizeTurn());
		assertEquals(fakeServer.received,
				"user=Jacque\ndesired-action=finalize");

		fakeServer.post = "result=" + Engine.SUCCESSFUL_OPERATION;
		assertFalse(target.finalizeTurn());
		assertEquals(fakeServer.received,
				"user=Jacque\ndesired-action=finalize");
	}

	@Test
	public void testNetworkGameEngineForfeits() {
		Player owner = new Player(Piece.COLOR.red, "Harry Potter");
		NetworkGameEngine target = new NetworkGameEngine("", 0, owner,
				"english");
		MockClient fakeServer = new MockClient("", "result="
				+ Engine.SUCCESSFUL_OPERATION, "");
		target.client = fakeServer;

		target.forfeit();
		assertEquals(fakeServer.received,
				"user=Harry Potter\ndesired-action=forfeit");

		owner = new Player(Piece.COLOR.green, "James Dean");
		target.owner = owner;

		target.forfeit();
		assertEquals(fakeServer.received,
				"user=James Dean\ndesired-action=forfeit");
	}

	static class MockClient implements IHTTPClient {
		private String get;
		private String post;
		private String url;
		private String received;

		public MockClient(String getResponse, String postResponse, String url) {
			this.get = getResponse;
			this.post = postResponse;
			this.url = url;
		}

		@Override
		public String getServerResponse(String request) {
			this.received = request;
			return this.get;
		}

		@Override
		public String sendServerData(String data) {
			this.received = data;
			return this.post;
		}

		@Override
		public String getServerURL() {
			return this.url;
		}

	}

}
