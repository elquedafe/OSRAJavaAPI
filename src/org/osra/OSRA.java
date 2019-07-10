package org.osra;
import java.io.IOException;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osra.architecture.Environment;
import org.osra.architecture.Flow;
import org.osra.architecture.Meter;
import org.osra.architecture.Switch;
import org.osra.architecture.Vpls;
import org.osra.tools.HttpTools;
import org.osra.tools.JsonParser;
import org.osra.tools.RestResponse;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

public class OSRA implements OSRAInterface{
	private String restHost;
	private String endpoint;
	private String metersEndpoint;
	private String vplsEndpoint;
	private String flowsEndpoint;
	private String authorizationEndpoint;
	private String environmentEndpoint;
	private String user;
	private String password;

	/**
	 * Create instance of OSRA
	 * @param user username to connect to ONOS Northbound API REST
	 * @param password password to connect to ONOS Northbound API REST
	 * @param restServerHost hostname or ip address where OSRA is running
	 * @param onosHost ONOS hostname or ip address where ONOS is running
	 * @throws IOException
	 */
	public OSRA(String user, String password, String restServerHost, String onosHost) throws IOException {
		this.user = user;
		this.password = password;
		this.restHost = restServerHost;
		this.endpoint = "http://"+restServerHost+":8080/onosapp-v1";
		this.authorizationEndpoint = endpoint+"/rest/authorization";

		String sufix = "";
		if(isAdmin()) sufix = "administration";
		else sufix = "users";

		this.metersEndpoint = endpoint+"/"+ sufix+"/meters";
		this.vplsEndpoint = endpoint+"/"+ sufix+"/vpls";
		this.flowsEndpoint = endpoint+"/"+ sufix+"/flows";
		this.environmentEndpoint = endpoint+"/"+ sufix+"/environment";

		String body = "{\n" + 
				"	\"userOnos\":\"onos\",\n" + 
				"	\"passwordOnos\":\"rocks\",\n" + 
				"	\"onosHost\": \""+onosHost+"\"\n" + 
				"}";
		try {
			HttpTools.doJSONPost(new URL(authorizationEndpoint), body, user, password);
		}catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * Open a TCP socket with given maximum bandwidth and burst
	 * @param ipAddress ipAddress to connect
	 * @param port port to connect
	 * @param rate maximum bandwidth for this socket
	 * @param burst maximum rate for this socket
	 * @return socket
	 * @throws IOException
	 */
	public Socket openTCPSocket(String ipVersion, String srcHost, String dstHost, int srcPort, int dstPort, int rate, int burst) throws IOException{
		Socket socket = null;
		SocketAddress srcAddr = new InetSocketAddress(srcHost, srcPort);
		SocketAddress dstAddr = new InetSocketAddress(dstHost, dstPort);
		int localPort = -1;
		String localAddress = "";

		// Open socket
		try {
			socket = new Socket();
			socket.bind(srcAddr);
			socket.connect(dstAddr);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

		localPort = socket.getLocalPort();
		localAddress = socket.getLocalAddress().getHostAddress();


		try {
			//TEST only
			//postMeter("10.0.0.1", localPort, "tcp", rate, burst);
			
			//TODO create flows INTENTS to host connectivity

			// TODO: implementacion
			postMeter(ipVersion, srcHost, dstHost, String.valueOf(localPort), String.valueOf(dstPort), "tcp", rate, burst);

		} catch (IOException e) {
			if (socket != null) {
				socket.close();
			}
			e.printStackTrace();
			throw e;
		}
		return socket;
	}

	public DatagramSocket openUDPSocket(String ipVersion, String dstHost, int dstPort, int rate, int burst) throws IOException {
		DatagramSocket socket;
//		ServerSocket socket;
		int srcPort = -1;
		String srcHost = "";

		try {
			socket = new DatagramSocket(dstPort, InetAddress.getByName(dstHost));
			//TODO create flows INTENTS to host connectivity
			
//			socket = new ServerSocket(dstPort, 10, InetAddress.getByName(dstHost));
		} catch (SocketException e) {
			e.printStackTrace();
			throw e;
		}


		srcHost = socket.getInetAddress().getHostAddress();
		srcPort = socket.getLocalPort();

		try {
			postMeter(ipVersion, srcHost, dstHost, String.valueOf(srcPort), String.valueOf(dstPort), "udp", rate, burst);
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
	private void postMeter(String ipVersion, String srcAdress, String dstAdress, String srcPort, String dstPort, String portType, int rate, int burst) throws MalformedURLException, IOException {
		String body = "{\n" +
				"	\"ipVersion\": "+ipVersion+",\n" + 
				"	\"srcHost\": \""+srcAdress+"\",\n" + 
				"	\"srcPort\": \""+srcPort+"\",\n" + 
				"	\"dstHost\": \""+dstAdress+"\",\n" + 
				"	\"dstPort\": \""+dstPort+"\",\n" +
				"	\"portType\": \""+portType+"\",\n"+
				"	\"rate\":"+rate+",\n" + 
				"	\"burst\":"+burst+"\n" +
				"}";

		// Request meter
		HttpTools.doJSONPost(new URL(metersEndpoint+"/"+srcAdress+"/"+dstAdress), body, user, password);
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

			//TEST only
			//HttpTools.doDelete(new URL(this.metersEndpoint+"/10.0.0.1"+"/port/"+socket.getLocalPort()));

			// TODO: Implementacion
			HttpTools.doDelete(new URL(this.metersEndpoint+"/"+socket.getLocalAddress().getHostAddress()+"/port/"+socket.getLocalPort()), user, password);

		}

	}



	/**
	 * Close UDP socket
	 * @param socket UDP socket to close
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public void closeUDPSocket(DatagramSocket socket) throws MalformedURLException, IOException {
		// If socket is not closed
		if( (socket != null) && (!socket.isClosed()) ) {
			socket.close();

			//TEST only
			//HttpTools.doDelete(new URL(this.metersEndpoint+"/10.0.0.1"+"/port/"+socket.getLocalPort()));

			// TODO: Implementacion
			HttpTools.doDelete(new URL(this.metersEndpoint+"/"+socket.getLocalAddress().getHostAddress()+"/port/"+socket.getLocalPort()), user, password);

		}
	}

	public static boolean register(String user, String password, String restServer) {
		String json = "{\n" +
				"   \"user\":\""+user+"\",\n" +
				"   \"password\":\""+password+"\"\n" +
				"}";
		try {
			HttpTools.doJSONPost(new URL("http://"+restServer+":8080/onosapp-v1/rest/register"), json, user, password);
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean isAdmin() {
		Gson gson = new Gson();
		RestResponse response;
		try {
			response = HttpTools.doJSONGet(new URL(authorizationEndpoint), user, password);
		} catch (MalformedURLException ex) {
			return false;
		} catch (IOException ex) {
			return false;
		}

		LinkedTreeMap jsonObject = gson.fromJson(response.getMessage(), LinkedTreeMap.class);
		return (boolean)jsonObject.get("isAdmin");
	}
	
	public Environment getEnvironment() throws IOException {
		RestResponse response;
		String json;
		response = HttpTools.doJSONGet(new URL(environmentEndpoint), user, password);
        return JsonParser.parseEnvironment(response.getMessage());
	}

	public List<Flow> getFlows() throws IOException{
		RestResponse response;
		response = HttpTools.doJSONGet(new URL(this.flowsEndpoint), user, password);
		return JsonParser.parseFlows(response.getMessage());
	}

	public void createFlows(String srcHost, String dstHost, String srcPort, String dstPort, String portType) throws IOException {
		String json = "{\n" +
				"\"ipVersion\": 4,\n" + 
				"	\"srcHost\": \""+srcHost+"\",\n" + 
				"	\"srcPort\": \""+srcPort+"\",\n" + 
				"	\"dstHost\": \""+dstHost+"\",\n" +
				"	\"dstPort\": \""+dstPort+"\",\n" +
				"	\"portType\": \""+portType+"\"\n" +
				"}";
		HttpTools.doJSONPost(new URL(flowsEndpoint+"/"+srcHost+"/"+dstHost), json, user, password);
	}
	
	public Map<String, String> createFlowsIpv6(String srcHost, String dstHost, String srcPort, String dstPort, String portType) throws IOException {
		RestResponse response;
		String json = "{\n" +
				"\"ipVersion\": 6,\n" + 
				"	\"srcHost\": \""+srcHost+"\",\n" + 
				"	\"srcPort\": \""+srcPort+"\",\n" + 
				"	\"dstHost\": \""+dstHost+"\",\n" + 
				"	\"dstPort\": \""+dstPort+"\",\n" +
				"	\"portType\": \""+portType+"\"\n" +
				"}";				;
		response = HttpTools.doJSONPost(new URL(flowsEndpoint+"/"+srcHost+"/"+dstHost+"/?element=host"), json, user, password);
		Map<String, String> switches = JsonParser.parseIngressEgressSwitches(response.getMessage());
		
		return switches;
	}

	public void deleteFlow(String srcHost, String dstHost, String srcPort, String dstPort) {
		RestResponse response;
	}

	public List<Meter> getMeters() throws IOException {
		RestResponse response;
        response = HttpTools.doJSONGet(new URL(metersEndpoint), user, password);
        if(response != null && (response.getMessage().startsWith("{") ||  response.getMessage().startsWith("[")) && !response.getMessage().isEmpty() && !response.getMessage().equals("null\n")) {
            return JsonParser.parseMeters(response.getMessage());
        }
        else return new ArrayList<Meter>();
	}

	public void createMeter(String srcHost, String dstHost, String srcPort, String dstPort, String portType, int rate, int burst) throws IOException {
		String json = "{\n" +
				"\"ipVersion\": 4,\n" + 
				"	\"srcHost\": \""+srcHost+"\",\n" + 
				"	\"srcPort\": \""+srcPort+"\",\n" + 
				"	\"dstHost\": \""+dstHost+"\",\n" + 
				"	\"dstPort\": \""+dstPort+"\",\n" +
				"	\"portType\": \""+portType+"\",\n"+
				"	\"rate\":"+rate+",\n" + 
				"	\"burst\":"+burst+"\n" +
				"}";				;
		HttpTools.doJSONPost(new URL(metersEndpoint+"/"+srcHost+"/"+dstHost), json, user, password);
	}
	
	public void createMeterIpv6(String srcHost, String dstHost, String srcPort, String dstPort, String portType, int rate, int burst) throws IOException {
		String json = "{\n" +
				"\"ipVersion\": 6,\n" + 
				"	\"srcHost\": \""+srcHost+"\",\n" + 
				"	\"srcPort\": \""+srcPort+"\",\n" + 
				"	\"dstHost\": \""+dstHost+"\",\n" + 
				"	\"dstPort\": \""+dstPort+"\",\n" +
				"	\"portType\": \""+portType+"\",\n"+
				"	\"rate\":"+rate+",\n" + 
				"	\"burst\":"+burst+"\n" +
				"}";				;
		HttpTools.doJSONPost(new URL(metersEndpoint+"/"+srcHost+"/"+dstHost), json, user, password);
	}

	public void deleteMeter(String srcHost, String dstHost, String srcPort, String dstPort) {
		RestResponse response;
	}

	public List<Vpls> getVpls() throws IOException{
		RestResponse response;
		return null;
	}

	public void createVpls(String vplsName, List<String> hosts) throws IOException{
		RestResponse response;
	}

	public void deleteVpls(String vplsName) throws IOException{
		RestResponse response;
	}

	@Override
	public void createFlowsIpv6(String ingress, String ingressPort, String egress, String egressPort, String srcHost, String dstHost, String srcPort,
			String dstPort, String portType) throws IOException {
		RestResponse response;
		String json = "{\n" +
				"\"ipVersion\": 6,\n" + 
				"	\"srcHost\": \""+srcHost+"\",\n" + 
				"	\"srcPort\": \""+srcPort+"\",\n" + 
				"	\"dstHost\": \""+dstHost+"\",\n" + 
				"	\"dstPort\": \""+dstPort+"\",\n" +
				"	\"portType\": \""+portType+"\"\n" +
				"}";				;
		response = HttpTools.doJSONPost(new URL(flowsEndpoint+"/"+ingress+"/"+egress+"/?element=switch"), json, user, password);
	}

	@Override
	public void createMeterIpv6(String ingress, String srcHost, String dstHost, String srcPort, String dstPort,
			String portType, int rate, int burst) throws IOException {
		String json = "{\n" +
				"\"ipVersion\": 6,\n" + 
				"	\"srcHost\": \""+srcHost+"\",\n" + 
				"	\"srcPort\": \""+srcPort+"\",\n" + 
				"	\"dstHost\": \""+dstHost+"\",\n" + 
				"	\"dstPort\": \""+dstPort+"\",\n" +
				"	\"portType\": \""+portType+"\",\n"+
				"	\"rate\":"+rate+",\n" + 
				"	\"burst\":"+burst+"\n" +
				"}";				;
		HttpTools.doJSONPost(new URL(metersEndpoint+"/"+ingress), json, user, password);
		
	}
	

}
