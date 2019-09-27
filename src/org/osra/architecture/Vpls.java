package org.osra.architecture;

import java.util.List;
import java.util.Vector;

/**
 * Represents a VPLS
 * @author Alvaro Luis Martinez
 * @version 1.0
 */
public class Vpls {
	private String name;
    private List<String> interfaces;
    private String encapsulation;

    public Vpls(String name, List<String> interfaces, String encapsulation) {
        this.name = name;
        this.interfaces = interfaces;
        this.encapsulation = encapsulation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<String> interfaces) {
        this.interfaces = interfaces;
    }

    public String getEncapsulation() {
        return encapsulation;
    }

    public void setEncapsulation(String encapsulation) {
        this.encapsulation = encapsulation;
    }
    
    
    
    public Object[] toTableArray(){
        Vector<Object> v = new Vector<Object>();
        String ifaces = "";
        v.add(this.name); 
        
        for(String i : this.interfaces){
            ifaces += i + "; ";
        }
        
        v.add(ifaces); 
        v.add(this.encapsulation);
        
        Object[] array = v.toArray();
        return array;
    }

}
