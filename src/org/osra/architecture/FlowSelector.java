package org.osra.architecture;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a flow selector
 * @author Alvaro Luis Martinez
 * @version 1.0
 */
public class FlowSelector {
	private List<FlowCriteria> flowCriterias;

    public FlowSelector(List<FlowCriteria> listFlowCriteria) {
        this.flowCriterias = listFlowCriteria;
    }

    public FlowSelector() {
        this.flowCriterias = new ArrayList<FlowCriteria>();
    }
    
    

    public List<FlowCriteria> getListFlowCriteria() {
        return flowCriterias;
    }

    public void setListFlowCriteria(List<FlowCriteria> listFlowCriteria) {
        this.flowCriterias = listFlowCriteria;
    }
}
