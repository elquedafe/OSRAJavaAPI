package org.osra.architecture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a host
 * @author Alvaro Luis Martinez
 * @version 1.0
 */
public class Host {
	private String id;
	private String mac;
	private String vlan;
	private String innerVlan;
	private String outerTpid;
	private boolean configured;
	private List<String> ipList;
	private Map<String, String> locations;

	public Host() {
		this.id = "";
		this.mac = "";
		this.vlan = "";
		this.innerVlan = "";
		this.outerTpid = "";
		this.configured = false;
		this.ipList = new ArrayList<String>();
		this.locations = new HashMap<String, String>();
	}

	public Host(String nombre) {
		this.id = nombre;
		this.mac = "";
		this.vlan = "";
		this.innerVlan = "";
		this.outerTpid = "";
		this.configured = false;
		this.ipList = new ArrayList<String>();
		this.locations = new HashMap<String, String>();
	}

	public Host(String id, String mac, String vlan, String innerVlan, String outerTpid, boolean configured, List<String> ipList, Map<String, String> mapLocations) {
		this.id = id;
		this.mac = mac;
		this.vlan = vlan;
		this.innerVlan = innerVlan;
		this.outerTpid = outerTpid;
		this.configured = configured;
		this.ipList = ipList;
		this.locations = mapLocations;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getVlan() {
		return vlan;
	}

	public void setVlan(String vlan) {
		this.vlan = vlan;
	}

	public String getInnerVlan() {
		return innerVlan;
	}

	public void setInnerVlan(String innerVlan) {
		this.innerVlan = innerVlan;
	}

	public String getOuterTpid() {
		return outerTpid;
	}

	public void setOuterTpid(String outerTpid) {
		this.outerTpid = outerTpid;
	}

	public boolean isConfigured() {
		return configured;
	}

	public void setConfigured(boolean configured) {
		this.configured = configured;
	}

	public List<String> getIpList() {
		return ipList;
	}

	public void setIpList(List<String> ipList) {
		this.ipList = ipList;
	}

	public void addLocatoin(String sw, String port){
		this.getMapLocations().put(sw, port);
	}

	/**
	 * @return the mapLocations
	 */
	public Map<String, String> getMapLocations() {
		return locations;
	}

	/**
	 * @param mapLocations the mapLocations to set
	 */
	public void setMapLocations(Map<String, String> mapLocations) {
		this.locations = mapLocations;
	}

	@Override
	public String toString(){
		String str = "";
		for(String s : ipList){
			str += s + "/";
		}
		str += this.mac;
		return str;
	}
}
