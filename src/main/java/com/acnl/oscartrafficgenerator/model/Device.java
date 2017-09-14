/**
 * 
 */
package com.acnl.oscartrafficgenerator.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.acnl.oscartrafficgenerator.buisness.service.DistributionServiceInterface;
/**
 * @author vishal
 *
 */

@JsonDeserialize(using = DeviceDeserializer.class)
public class Device {

	private String device;
	private List<String> ports;
	private List<Integer> bandwidth;
	private DistributionServiceInterface distribution;

    public Device(String device, List<String> port, List<Integer> bandwidth) {
        this.device = device;
        this.ports= port;
        this.bandwidth=bandwidth;
    }
       
    public void setDeviceDistribution(DistributionServiceInterface distribution){
    	this.distribution=distribution;
    }
    
    public String getDevice(){
    	return device;
    }
    
    public String getRandomPortFromThisDevice(){
        return ports.get((int)distribution.getSample());
    }
    
    public int getTotalPortsForThisDevice(){
        return ports.size();
    }

    public List<String> getAllPorts(){
       return ports;
    }
    
    public int getBandwidthForPort(String port){	
       return bandwidth.get(ports.indexOf(port));
    }
    
    public List<Integer> getAllPortBandwidths(){
    	 return bandwidth;
    }
}
