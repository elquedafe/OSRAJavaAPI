package org.osra.architecture;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a flow instruction
 * @author Alvaro Luis Martinez
 * @version 1.0
 */
public class FlowInstruction {
	private String type;
    private Map<String,String> instructions;

    public FlowInstruction(String type, Map<String, String> instructions) {
        this.type = type;
        this.instructions = instructions;
    }

    public FlowInstruction() {
        this.type = "";
        this.instructions = new HashMap<String,String>();
    }
    
    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getInstructions() {
        return instructions;
    }

    public void setInstructions(Map<String, String> instructions) {
        this.instructions = instructions;
    }
}
