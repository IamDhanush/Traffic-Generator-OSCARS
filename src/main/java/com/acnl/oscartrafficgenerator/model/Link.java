/**
 * 
 */
package com.acnl.oscartrafficgenerator.model;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.acnl.oscartrafficgenerator.buisness.service.DistributionServiceInterface;

/**
 * @author vishal
 *
 */
@JsonDeserialize(using = LinkDeserializer.class)
public class Link {

	private int distance, num_failures=0;
	private String type;
	private List<String> linkPair=new LinkedList<String>();
	private DistributionServiceInterface failureDistribution,repairDistribution;
	private double currentFailure,currentRepair,updatedFailureDifference=0;
	
    public Link(String a,int distance,String type,String z){
		this.linkPair.add(a);
		this.linkPair.add(z);
		this.distance=distance;
		this.type=type;
	}
    
    public void setFailureDistribution(DistributionServiceInterface distribution){
    	this.failureDistribution=distribution;
    }
    
    public void setRepairDistribution(DistributionServiceInterface distribution){
    	this.repairDistribution=distribution;
    }
    
    public void setCurrentFailure(double failureTime){
    	this.currentFailure = failureTime;
    }
    
    public void setCurrentRepair(double repairTime){
    	this.currentRepair = repairTime;
    }
    
    public void setUpdatedFailureDifference(double timeDifference){
    	this.updatedFailureDifference=timeDifference;
    }
    
    public double getUpdatedFailureDifference(){
    	return updatedFailureDifference;
    }
    
    public double getCurrentFailure(){
    	return currentFailure;
    }
    
    public double getCurrentRepair(){
    	return currentRepair;
    }
    
    public double getNextFailure(){
    	num_failures++;
    	return failureDistribution.getSample();
    }
    
    public double getNextRepair(){
    	return repairDistribution.getSample();
    }
    
    public String getA(){
    	return linkPair.get(0);
    }
    
    public String getZ(){
    	return linkPair.get(1);
    }
    
    public List<String> getLink(){
    	return linkPair;
    }
    
    public int getDistance(){
    	return distance;
    }
    
    public String getType(){
    	return type;
    }
    
    public int getNumFailures(){
    	return num_failures;
    }
    
    public String toString(){
    	linkPair.stream().forEach(link->System.out.print(link+"->"));
    	return "Length: "+distance+", Installation Method: "+type;
    }
    
}
