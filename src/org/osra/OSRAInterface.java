package org.osra;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.osra.architecture.Environment;
import org.osra.architecture.Flow;
import org.osra.architecture.Host;
import org.osra.architecture.Meter;
import org.osra.architecture.Queue;
import org.osra.architecture.Vpls;
import org.osra.tools.HttpTools;

public interface OSRAInterface {
	/**
	 * Open a TCP QoS Socket for srcHost:srcPort and dstHost:dstPort using OpenFlow meters 
	 * @param ipVersion hosts address IP version, 4 or 6
	 * @param srcHost source host IP
	 * @param dstHost destination host IP
	 * @param srcPort source port
	 * @param dstPort destination port
	 * @param rate maximum rate for the socket
	 * @param burst burst allowed for the socket
	 * @return Socket opened socket
	 * @throws IOException API REST connection exception 
	 */
	public Socket openTCPMeterSocket(String ipVersion, String srcHost, String dstHost, int srcPort, int dstPort, int rate, int burst) throws IOException;
	
	/**
	 * Open a TCP QoS Socket for srcHost:srcPort and dstHost:dstPort using OpenFlow meters 
	 * @param ipVersion hosts address IP version, 4 or 6
	 * @param srcHost source host IP
	 * @param dstHost destination host IP
	 * @param srcPort source port
	 * @param dstPort destination port
	 * @param rate maximum rate for the socket
	 * @param burst burst allowed for the socket
	 * @return Socket opened socket
	 * @throws IOException API REST connection exception 
	 */
	public Socket openTCPQueueSocket(String ipVersion, String srcHost, String dstHost, int srcPort, int dstPort, int minRate, int maxRate, int burst) throws IOException;
	
	/**
	 * Open an UDP QoS Socket for dstHost:dstPort using OpenFlow meters
	 * @param ipVersion hosts address IP version, 4 or 6
	 * @param dstHost destination host IP
	 * @param dstPort destination port
	 * @param rate maximum rate for the socket
	 * @param burst burst allowed for the socket
	 * @return DatagramSocket opened socket
	 * @throws IOException API REST connection exception
	 */
	public DatagramSocket openUDPMeterSocket(String ipVersion, String dstHost, int dstPort, int rate, int burst) throws IOException;
	
	/**
	 * Open an UDP QoS Socket for dstHost:dstPort using OpenFlow meters
	 * @param ipVersion hosts address IP version, 4 or 6
	 * @param dstHost destination host IP
	 * @param dstPort destination port
	 * @param rate maximum rate for the socket
	 * @param burst burst allowed for the socket
	 * @return DatagramSocket opened socket
	 * @throws IOException API REST connection exception
	 */
	public DatagramSocket openUDPQueueSocket(String ipVersion, String dstHost, int dstPort, int minRate, int maxRate, int burst) throws IOException;
	
	/**
	 * Close TCP socket deleting OpenFlow meter associated to connection
	 * @param socket
	 * @throws IOException API REST connection exception
	 */
	public void closeTCPMeterSocket(Socket socket) throws IOException;
	
	/**
	 * Close TCP socket deleting OpenFlow meter associated to connection
	 * @param socket
	 * @throws IOException API REST connection exception
	 */
	public void closeTCPQueueSocket(Socket socket) throws IOException;
	
	/**
	 * Close UDP socket deleting OpenFlow meter associated to connection
	 * @param socket
	 * @throws IOException API REST connection exception
	 */
	public void closeUDPQueueSocket(DatagramSocket socket) throws IOException;
	
	/**
	 * Close UDP socket deleting OpenFlow meter associated to connection
	 * @param socket
	 * @throws IOException API REST connection exception
	 */
	public void closeUDPMeterSocket(DatagramSocket socket) throws IOException;
	
	/**
	 * Register user in the database
	 * @param user user name
	 * @param password user password
	 * @param restServer REST API location
	 * @return true if register was successful, false if not
	 */
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
	
	/**
	 * Retrieve all info about hosts, meters, flows and links in the SDN network
	 * @return host, meters, flows and links information
	 * @throws IOException API REST connection exception
	 */
	public Environment getEnvironment() throws IOException;
	
	/**
	 * Get the list of the flows in the network
	 * @return list of flows
	 * @throws IOException API REST connection exception
	 */
	public List<Flow> getFlows() throws IOException;
	
	/**
	 * Create bidirectional flows in the network to allow connection between two hosts
	 * @param srcHost first host IP
	 * @param dstHost second host IP
	 * @param srcPort first host port
	 * @param dstPort second host port
	 * @param portType TCP or UDP
	 * @throws IOException API REST connection exception
	 */
	public void createFlows(String srcHost, String dstHost, String srcPort, String dstPort, String portType) throws IOException;
	
	/**
	 * Create bidirectional flows in the network to allow connection between two hosts with IPv6 addresses
	 * @param srcHost first host IP
	 * @param dstHost second host IP
	 * @param srcPort first host port
	 * @param dstPort second host port
	 * @param portType TCP or UDP
	 * @return Map with info ingress and egress switch id
	 * @throws IOException API REST connection exception
	 */
	public Map<String, String> createFlowsIpv6(String srcHost, String dstHost, String srcPort, String dstPort, String portType) throws IOException;
	
	/**
	 * Create bidirectional flows in the network to allow connection between two hosts
	 * @param ingress ingress switch id
	 * @param ingressPort ingress switch port
	 * @param egress egress switch id
	 * @param egressPort egress switch port
	 * @param srcHost first host IP
	 * @param dstHost second host IP
	 * @param srcPort first host port
	 * @param dstPort second host port
	 * @param portType TCP or UDP
	 * @throws IOException API REST connection exception
	 */
	public void createFlowsIpv6(String ingress, String ingressPort, String egress, String egressPort, String srcHost, String dstHost, String srcPort, String dstPort, String portType) throws IOException;
	
	/**
	 * Delete flow for a connection
	 * @param srcHost first host IP
	 * @param dstHost second host OP
	 * @param srcPort first host port
	 * @param dstPort second host port
	 * @param IOException API REST connection exception
	 */
	public void deleteFlow(String srcHost, String dstHost, String srcPort, String dstPort) throws IOException;
	
	/**
	 * Get the list of the meters in the network
	 * @return list of meters
	 * @throws IOException API REST connection exception
	 */
	public List<Meter> getMeters() throws IOException;
	
	/**
	 * Create a meter for a connection
	 * @param srcHost source host IP
	 * @param dstHost destination host OP
	 * @param srcPort source port
	 * @param dstPort destination port
	 * @param portType UDP or TCP
	 * @param rate maximum rate for the connection
	 * @param burst burst allowed for the connection
	 * @throws IOException API REST connection exception
	 */
	public void createMeter(String srcHost, String dstHost, String srcPort, String dstPort, String portType, int rate, int burst) throws IOException;
	
	/**
	 * Create a meter for a IPv6 connection
	 * @param srcHost source host IP
	 * @param dstHost destination host OP
	 * @param srcPort source port
	 * @param dstPort destination port
	 * @param portType UDP or TCP
	 * @param rate maximum rate for the connection
	 * @param burst burst allowed for the connection
	 * @throws IOException API REST connection exception
	 */
	public void createMeterIpv6(String srcHost, String dstHost, String srcPort, String dstPort, String portType, int rate, int burst) throws IOException;
	
	/**
	 * Create a meter for a IPv6 connection
	 * @param ingress ingress switch
	 * @param srcHost source host IP
	 * @param dstHost destination host OP
	 * @param srcPort source port
	 * @param dstPort destination port
	 * @param portType UDP or TCP
	 * @param rate maximum rate for the connection
	 * @param burst burst allowed for the connection
	 * @throws IOException API REST connection exception
	 */
	public void createMeterIpv6(String ingress, String srcHost, String dstHost, String srcPort, String dstPort, String portType, int rate, int burst) throws IOException;
	
	/**
	 * Delete a meter for a connection
	 * @param srcHost source host IP
	 * @param dstHost destination host IP
	 * @param srcPort source port
	 * @param dstPort destination port
	 * @throws IOException API REST connection exception
	 */
	public void deleteMeter(String srcHost, String dstHost, String srcPort, String dstPort) throws IOException;
	
	/**
	 * Delete meter given switchId and meterId
	 * @param switchId
	 * @param meterId
	 * @throws IOException
	 */
	public void deleteMeter(String switchId, String meterId) throws IOException;
	
	/**
	 * Get the list of vpls in the network
	 * @return list of Vpls
	 * @throws IOException API REST connection exception
	 */
	public List<Vpls> getVpls() throws IOException;
	
	/**
	 * Create a Vpls with the given name
	 * @param vplsName new Vpls name
	 * @param hosts list of hosts in the Vpls
	 * @throws IOException API REST connection exception
	 */
	public void createVpls(String vplsName, List<Host> hosts, String maxRate, String minRate, String burst) throws IOException;
	
	/**
	 * Delete the Vpls with the given name
	 * @param vplsName vpls name
	 * @throws IOException API REST connection exception
	 */
	public void deleteVpls(String vplsName) throws IOException;
	
	/**
	 * Create a QoS from src and dst
	 * @param ipVersion
	 * @param srcHost
	 * @param srcPort
	 * @param dstHost
	 * @param dstPort
	 * @param portType
	 * @param minRate
	 * @param maxRate
	 * @param burst
	 * @throws IOException
	 */
	public List<Queue> createQueue(String ipVersion, String srcHost, String srcPort, 
			String dstHost, String dstPort, String portType, 
			int minRate, int maxRate, int burst) throws IOException;
		
	/**
	 * Delete queue given its queueId
	 * @param queueId
	 * @throws IOException
	 */
	public void deleteQueue(String queueId) throws IOException;
	
	/**
	 * Delete queue and its attachment to the switch port. Just useful when there is no more queues for that port
	 * @param queueId
	 * @throws IOException
	 */
	public void deleteQueuePort(String queueId) throws IOException;
	
	/**
	 * Get the list of the queues in the network
	 * @return
	 * @throws IOException
	 */
	public List<Queue> getQueues() throws IOException;
	
}
