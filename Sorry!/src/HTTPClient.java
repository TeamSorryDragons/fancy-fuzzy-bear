import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Implements the HTTPClient interface to provide an actual HTTPClient for the
 * NetworkGameEngine.
 * 
 * @author sturgedl. Created Apr 29, 2013.
 */
public class HTTPClient implements IHTTPClient {
	protected String serverURL;

	/**
	 * Create a new HTTPClient, ready to send requests to the given URL.
	 * 
	 * @param serv
	 */
	public HTTPClient(String serv) {
		this.serverURL = serv;
	}

	@Override
	public String getServerURL() {
		return this.serverURL;
	}

	@Override
	public String getServerResponse(String request) {
		try {
			URL serv = new URL(this.serverURL + request);
			HttpURLConnection conn = (HttpURLConnection) serv.openConnection();
			conn.setRequestMethod("GET");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			StringBuffer sb = new StringBuffer();
			String in = "";
			while (in != null) {
				sb.append(in+"\n");
				in = br.readLine();
			}
			in = sb.toString();
			br.close();
			return in;
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	@Override
	public String sendServerData(String data) {
		try {
			URL serv = new URL(this.serverURL);
			HttpURLConnection conn = (HttpURLConnection) serv.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream());

			out.write(data);
			out.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			StringBuffer sb = new StringBuffer();
			String in = "";
			while (in != null) {
				sb.append(in);
				in = br.readLine();
			}
			in = sb.toString();
			br.close();
			return in;
		} catch (IOException e) {
			return null;
		}
	}

}
