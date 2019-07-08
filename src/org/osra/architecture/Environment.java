package org.osra.architecture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Environment {
    private Map<String, Switch> mapSwitches;
    private List<Cluster> listClusters;
    private Map<String, Host> mapHosts;
    private List<Vpls> vpls;
    
    public Environment() {
    	mapSwitches = new HashMap<String, Switch>();
    	listClusters = new ArrayList<Cluster>();
    	mapHosts = new HashMap<String, Host>();
    	vpls = new ArrayList<Vpls>();
    }

    public void addCluster(Cluster cluster){
        listClusters.add(cluster);
    }

    public void addSwitch(String nombre) {
        mapSwitches.put(nombre, new Switch(nombre));
            //this.nSwitches++;
    }

    public void addHost(String nombre) {
        mapHosts.put(nombre, new Host(nombre));
            //this.nSwitches++;
    }
    
    public void addHost(Host host){
        mapHosts.put(host.getId(), host);
    }
	
    public List<Meter> getAllMeters(){
        List<Meter> meters = new ArrayList<Meter>();
        List<Meter> swMeters = null;
        for(Switch s : mapSwitches.values()){
           swMeters = s.getMeters();
           for(Meter m : swMeters){
               meters.add(m);
           }
        }
        return meters;
    }
    
    public void addMeters(List<Meter> meters){
        cleanMeters();
        for(Meter m : meters){
            addMeter(m);
        }
    }
    
    private void addMeter(Meter meter){
        mapSwitches.get(meter.getDeviceId()).getMeters().add(meter);
    }
    
    private void cleanMeters(){
        for(Switch s : mapSwitches.values()){
            s.getMeters().clear();
        }
    }

	/**
	 * @return the mapSwitches
	 */
	public Map<String, Switch> getMapSwitches() {
		return mapSwitches;
	}

	/**
	 * @param mapSwitches the mapSwitches to set
	 */
	public void setMapSwitches(Map<String, Switch> mapSwitches) {
		this.mapSwitches = mapSwitches;
	}

	/**
	 * @return the listClusters
	 */
	public List<Cluster> getListClusters() {
		return listClusters;
	}

	/**
	 * @param listClusters the listClusters to set
	 */
	public void setListClusters(List<Cluster> listClusters) {
		this.listClusters = listClusters;
	}

	/**
	 * @return the mapHosts
	 */
	public Map<String, Host> getMapHosts() {
		return mapHosts;
	}

	/**
	 * @param mapHosts the mapHosts to set
	 */
	public void setMapHosts(Map<String, Host> mapHosts) {
		this.mapHosts = mapHosts;
	}

	/**
	 * @return the vpls
	 */
	public List<Vpls> getVpls() {
		return vpls;
	}

	/**
	 * @param vpls the vpls to set
	 */
	public void setVpls(List<Vpls> vpls) {
		this.vpls = vpls;
	}
    
    
}
