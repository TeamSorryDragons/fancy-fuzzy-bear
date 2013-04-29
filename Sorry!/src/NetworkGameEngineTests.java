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
		try {
			target = new NetworkGameEngine("", 0, null, "english");
			assertNotNull(target);
		} catch (MalformedURLException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
			fail();
		}
	}

	@Test
	public void testNetworkGameEngineConstructorPopulatesFields() {
		String url = "http://localhost";
		Player testP = new Player(Piece.COLOR.yellow, "Harry Potter");
		int port = 8080;
		NetworkGameEngine target = null;
		try {
			target = new NetworkGameEngine(url, port, testP, "english");
		} catch (MalformedURLException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
			fail();
		}
		assertEquals(target.owner, testP);
		assertEquals(target.client.getServerURL(), url + ":" + port + "/");

		for (int i = 0; i < 5; i++)
			assertEquals(target.cards.get(i).cardNum, i + 1);
		assertEquals(target.cards.get(target.cards.size() - 1).cardNum, 13);
	}

	@Test
	public void testNetworkGameEngineGetsUpdatedInfo() {

	}

	static class MockClient implements IHTTPClient {
		private String get;
		private String post;
		private String url;

		public MockClient(String getResponse, String postResponse, String url) {
			this.get = getResponse;
			this.post = postResponse;
			this.url = url;
		}

		@Override
		public String getServerResponse(String request) {
			return this.get;
		}

		@Override
		public String sendServerData(String data) {
			return this.post;
		}

		@Override
		public String getServerURL() {
			return this.url;
		}

	}

}
