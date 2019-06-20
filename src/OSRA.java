import java.io.IOException;

import java.net.DatagramSocket;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import org.osra.tools.HttpTools;

public class OSRA {
	private String restHost;
	private String endpoint;
	private String metersEndpoint;
	private String vplsEndpoint;
	private String flowsEndpoint;
	private String authorizationEndpoint;
	private String environmentEndpoint;
	/**
	 * Create instance of OSRA
	 * @param user username to connect to ONOS Northbound API REST
	 * @param password password to connect to ONOS Northbound API REST
	 * @param restHost hostname or ip address where OSRA is running
	 * @param onosHost ONOS hostname or ip address where OSRA is running
	 * @throws IOException
	 */
	public OSRA(String user, String password, String restHost, String onosHost) throws IOException {
		this.restHost = restHost;
		this.endpoint = "http://"+restHost+":8080/onosapp-v1/rest";
		this.metersEndpoint = endpoint+"/meters";
		this.vplsEndpoint = endpoint+"/vpls";
		this.flowsEndpoint = endpoint+"/flows";
		this.authorizationEndpoint = endpoint+"/authorization";
		this.environmentEndpoint = endpoint+"/environment";

		String body = "{\n" + 
				"	\"user\":\""+user+"\",\n" + 
				"	\"password\":\""+password+"\",\n" + 
				"	\"onosHost\": \""+onosHost+"\"\n" + 
				"}";
		try {
			HttpTools.doJSONPost(new URL(authorizationEndpoint), body);
		}catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * Open a TCP socket with maximum bandwidth and burst
	 * @param ipAddress ipAddress to connect
	 * @param port port to connect
	 * @param rate maximum bandwidth for this socket
	 * @param burst maximum rate for this socket
	 * @return socket
	 * @throws IOException
	 */
	public Socket openTCPSocket(String ipAddress, int port, int rate, int burst) throws IOException{
		Socket socket = null;
		int localPort = -1;
		String localAddress = "";

		// Open socket
		try {
			socket = new Socket(ipAddress, port);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

		localPort = socket.getLocalPort();
		localAddress = socket.getLocalAddress().getHostAddress();

		
		try {
			// Request meter
			postMeter("10.0.0.1", localPort, "tcp", rate, burst);
			//postMeter(localAddress, localPort, "tcp", rate, burst);

		} catch (IOException e) {
			if (socket != null) {
				socket.close();
			}
			e.printStackTrace();
			throw e;
		}
		return socket;

	}

	public DatagramSocket openUDPSocket(int rate, int burst) throws IOException {
		DatagramSocket socket;
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			throw e;
		}
		
		String localAddress = socket.getLocalAddress().getHostAddress();
		int localPort = socket.getLocalPort();

		try {
			postMeter(localAddress, localPort, "upd", rate, burst);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

		return socket;

	}

	/**
	 * Post to request generation of a meter
	 * @param localAddress
	 * @param rate
	 * @param burst
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private void postMeter(String localAddress, int port, String portType, int rate, int burst) throws MalformedURLException, IOException {
		String body = "{\n" + 
				"	\"host\": \""+localAddress+"\",\n" + 
				"	\"port\": \""+port+"\",\n" + 
				"	\"portType\": \""+portType+"\",\n" + 
				"	\"rate\": "+rate+",\n" + 
				"	\"burst\": "+burst+"\n" + 
				"}";

		// Request meter
		HttpTools.doJSONPost(new URL(metersEndpoint+"/"+localAddress+"/"+port), body);
	}



	/**
	 * Close TCP socket
	 * @param socket TCP socket to close
	 * @throws IOException
	 */
	public void closeTCPSocket(Socket socket) throws IOException {
		// If socket is not closed
		if( (socket != null) && (!socket.isClosed()) ) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
			
			HttpTools.doDelete(new URL(this.metersEndpoint+"/10.0.0.1"+"/port/"+socket.getLocalPort()));
			//HttpTools.doDelete(new URL(this.metersEndpoint+"/"+socket.getLocalAddress().getHostAddress()+"/port/"+socket.getLocalPort()));
			
		}

	}



	/**
	 * Close UDP socket
	 * @param socket UDP socket to close
	 */
	public void closeUDPSocket(DatagramSocket socket) {
		if( (socket != null) && (!socket.isClosed()) ) {
			socket.close();
			
			// TODO: DELETE meter for ip/port
		}
	}
}
