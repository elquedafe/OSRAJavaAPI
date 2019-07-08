package org.osra.tools;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osra.architecture.Environment;
import org.osra.architecture.Flow;
import org.osra.architecture.FlowCriteria;
import org.osra.architecture.FlowInstruction;
import org.osra.architecture.FlowSelector;
import org.osra.architecture.FlowTreatment;
import org.osra.architecture.Host;
import org.osra.architecture.Meter;
import org.osra.architecture.Switch;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

public class JsonParser {
	public static List<Flow> parseFlows(String json) {
		Gson gson = new Gson();
		List<Flow> listFlows = new ArrayList<Flow>();
		try {
			JsonObject jsonObj = (JsonObject)gson.fromJson(json, JsonObject.class);
			for(Object switches : jsonObj.entrySet()) {
				for(Object flows : ((Map.Entry<String, JsonArray>)switches).getValue()) {
					Flow f = gson.fromJson(gson.toJson(flows), Flow.class);
					listFlows.add(f);
				}
			}
		}
		catch(NullPointerException e) {
			e.printStackTrace();

		}
		return listFlows;
	}

	public static Environment parseEnvironment(String json) {
		Gson gson = new Gson();
		Environment environment = new Environment();
		//Entorno entorno = gson.fromJson(json, Entorno.class);
		LinkedTreeMap jsonObject = gson.fromJson(json, LinkedTreeMap.class);

		// SWITCHES
		Switch sw = null;
		LinkedTreeMap mapSw = (LinkedTreeMap)jsonObject.get("switches");
		for(Object o : mapSw.entrySet()){
			Map.Entry<String, LinkedTreeMap> entry = (Map.Entry<String, LinkedTreeMap>)o;
			sw = gson.fromJson(gson.toJson(entry.getValue()), Switch.class);
			environment.getMapSwitches().put(sw.getId(), sw);
		}

		//CLUSTERS
		List mapCluster = (ArrayList)jsonObject.get("clusters");
		environment.setListClusters(gson.fromJson(gson.toJson(mapCluster), ArrayList.class));

		//HOSTS
		Host h = null;
		LinkedTreeMap mapH = (LinkedTreeMap)jsonObject.get("hosts");
		for(Object o : mapH.entrySet()){
			Map.Entry<String, LinkedTreeMap> entry = (Map.Entry<String, LinkedTreeMap>)o;
			h = gson.fromJson(gson.toJson(entry.getValue()), Host.class);
			environment.getMapHosts().put(h.getId(), h);
		}
		return environment;
	}

	public static List<Meter> parseMeters(String json) {
		Gson gson = new Gson();
        List<Meter> listMeters = new ArrayList<Meter>();
        Meter m = null;
        List auxList = (ArrayList)gson.fromJson(json, ArrayList.class);
        for(Object o : auxList){
            m = gson.fromJson(gson.toJson(o), Meter.class);
            listMeters.add(m);
        }
        return listMeters;
	}

}
