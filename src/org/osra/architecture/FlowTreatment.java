package org.osra.architecture;

import java.util.ArrayList;
import java.util.List;


public class FlowTreatment {
	private List<FlowInstruction> flowInstructions;

    /**
     *
     * @param listInstructions
     */
    public FlowTreatment(List<FlowInstruction> listInstructions) {
        this.flowInstructions = listInstructions;
    }

    public FlowTreatment() {
        this.flowInstructions = new ArrayList<FlowInstruction>();
    }

    public List<FlowInstruction> getListInstructions() {
        return flowInstructions;
    }

    public void setListInstructions(List<FlowInstruction> listInstructions) {
        this.flowInstructions = listInstructions;
    }
}
