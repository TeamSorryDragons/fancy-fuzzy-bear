/**
 * Interface for an HTTP Client, to be used for communicating with the
 * SorryServer. Allows for sending POST and GET requests of various forms with
 * various data.
 * 
 * @author sturgedl. Created Apr 29, 2013.
 */
public interface IHTTPClient {

	/**
	 * Send a request to the server, appending the given request to the URl.
	 * Return the server's response.
	 * 
	 * @param request
	 * @return what does it think of you?
	 */
	String getServerResponse(String request);

	/**
	 * Send a packet of data to the server, and return its response.
	 * 
	 * @param data
	 * @return server's response... did you make it angry?
	 */
	String sendServerData(String data);

	/**
	 * Get the server url which this client will interact with.
	 * 
	 * @return
	 */
	String getServerURL();
}
