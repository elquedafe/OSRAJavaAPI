package org.osra.architecture;

import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class FlowCriteria {
	private String type;
    private SimpleEntry<String,String> criteria;

    public FlowCriteria(String type, SimpleEntry<String,String> criteria) {
        this.type = type;
        this.criteria = criteria;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map.Entry<String,String> getCriteria() {
        return criteria;
    }

    public void setCriteria(SimpleEntry<String,String> criteria) {
        this.criteria = criteria;
    }

}
