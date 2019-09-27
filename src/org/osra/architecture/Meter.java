package org.osra.architecture;

import java.util.List;
import java.util.Vector;

/**
 * Represents a meter
 * @author Alvaro Luis Martinez
 * @version 1.0
 */
public class Meter {
	private String id;
    private int life;
    private int packets;
    private int bytes;
    private int referenceCount;
    private String unit;
    private boolean burst;
    private String deviceId;
    private String appId;
    private String state;
    private List<Band> bands;

    public Meter(String id, int life, int packets, int bytes, int referenceCount, String unit, boolean burst, String deviceId, String appId, String state, List<Band> bands) {
        this.id = id;
        this.life = life;
        this.packets = packets;
        this.bytes = bytes;
        this.referenceCount = referenceCount;
        this.unit = unit;
        this.burst = burst;
        this.deviceId = deviceId;
        this.appId = appId;
        this.state = state;
        this.bands = bands;
    }

    public String getId() {
        return id;
    }

    public int getLife() {
        return life;
    }

    public int getPackets() {
        return packets;
    }

    public int getBytes() {
        return bytes;
    }

    public int getReferenceCount() {
        return referenceCount;
    }

    public String getUnit() {
        return unit;
    }

    public boolean isBurst() {
        return burst;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getAppId() {
        return appId;
    }

    public String getState() {
        return state;
    }

    public List<Band> getBands() {
        return bands;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setPackets(int packets) {
        this.packets = packets;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public void setReferenceCount(int referenceCount) {
        this.referenceCount = referenceCount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setBurst(boolean burst) {
        this.burst = burst;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setBands(List<Band> bands) {
        this.bands = bands;
    }
    
    
    
    public Object[] toTableArray(){
        Vector<Object> v = new Vector<Object>();
        v.add(this.deviceId); v.add(this.id); v.add(this.state);
        v.add(this.bands.get(0).getRate()); v.add(this.bands.get(0).getBurstSize()); v.add(this.bands.get(0).getBytes());
        Object[] array = v.toArray();
        return array;
    }
}
