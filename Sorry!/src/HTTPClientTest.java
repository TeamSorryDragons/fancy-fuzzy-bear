import static org.junit.Assert.*;

import org.junit.Test;


/**
 * TODO Put here a description of what this class does.
 *
 * @author sturgedl.
 *         Created May 15, 2013.
 */
public class HTTPClientTest {

	@Test
	public void testCreateClient(){
		HTTPClient client = new HTTPClient("http://localhost");
		assertNotNull(client);
	}
	
	@Test
	public void testClientSavesURL(){
		HTTPClient target = new HTTPClient("http://localhost");
		assertEquals(target.serverURL, "http://localhost");
	}

}
