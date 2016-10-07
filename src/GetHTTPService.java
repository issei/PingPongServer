import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;

public class GetHTTPService {
	private static final String USER_AGENT2 = "User-Agent";
	private static final String GET = "GET";
	private static final String USER_AGENT = "Mozilla/5.0";

	public static String sendGET(String url, String proxyhost,int proxyport, String user, String pass) throws MalformedURLException {
		URL obj = new URL(url);
		HttpURLConnection con = null;
		if (proxyhost != null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyhost, proxyport));
			if (user != null) {
				Authenticator authenticator = new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return (new PasswordAuthentication(user, pass.toCharArray()));
					}
				};
				Authenticator.setDefault(authenticator);
			}
			try {
				con = (HttpURLConnection) obj.openConnection(proxy);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				con = (HttpURLConnection) obj.openConnection();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		// optional default is GET
		try {
			con.setRequestMethod(GET);
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
		// add request header
		con.setRequestProperty(USER_AGENT2, USER_AGENT);
		int responseCode = -1;
		try {
			responseCode = con.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("\nSending 'GET' request to URL : " + url);
		// System.out.println("Response Code : " + responseCode);
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// System.out.println(response.toString());
			return response.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
