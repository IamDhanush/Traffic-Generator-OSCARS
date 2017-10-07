/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.service;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author vsundarrajan
 * @param <T>
 *
 */
public class RequestService {

	private DistributionServiceInterface startTimeDistribution,endTimeDistribution;
	private DistributionServiceInterface deviceDistribution;
	private DistributionServiceInterface bandwidthDistribution;
	private DevicesService deviceService;
	private LinksService linkService;
	private List<Double> devices=new LinkedList<Double>();
	private List<String> blacklist=new LinkedList<String>(),azRoute=new LinkedList<String>(),zaRoute=new LinkedList<String>();
	private int bandwidth,degree,seed,totalRequests;
	private byte failure;
	private long startTime=0,endTime=0;
	private String numDisjointPaths, minNumFlows, maxNumFlows,palindrome,surviva,ports[];

	@Autowired
	public RequestService(Supplier<DistributionServiceInterface> startTimeDistribution, Supplier<DistributionServiceInterface> endTimeDistribution, Supplier<DistributionServiceInterface> deviceDistribution,
 Supplier<DistributionServiceInterface> bandwidthDistribution, DevicesService deviceService,LinksService linkService){
        this.startTimeDistribution=startTimeDistribution.get();
		this.endTimeDistribution=endTimeDistribution.get();
		this.bandwidthDistribution=bandwidthDistribution.get();
		this.deviceDistribution=deviceDistribution.get();
		this.deviceService=deviceService;
		this.linkService=linkService;
	}
	
	public void setSeed(String seed){
		this.seed=Integer.valueOf(seed);	
	}
	
	public int getSeed(){
		return seed;
	}
	
	public int getTotalRequests() {
		return totalRequests;
	}

	public void setTotalRequests(String totalRequests) {
		this.totalRequests = Integer.valueOf(totalRequests);
	}

	public String getPalindrome() {
		return palindrome;
	}

	public void setPalindrome(String palindrome) {
		this.palindrome = palindrome;
	}

	public String getNumDisjointPaths() {
		return numDisjointPaths;
	}

	public void setNumDisjointPaths(String numDisjointPaths) {
		this.numDisjointPaths = numDisjointPaths;
	}

	public String getMinNumFlows() {
		return minNumFlows;
	}

	public void setMinNumFlows(String minNumFlows) {
		this.minNumFlows = minNumFlows;
	}

	public String getMaxNumFlows() {
		return maxNumFlows;
	}

	public void setMaxNumFlows(String maxNumFlows) {
		this.maxNumFlows = maxNumFlows;
	}
	
	public int getDegree(){
		return degree;
	}
	
	public void setDegree(String degree){
		this.degree=Integer.valueOf(degree);
		ports=new String[this.degree+1];//+1 for source
	}
	
	public void setArrivalProperties(String mean){
		startTimeDistribution.setSeed(seed);
	    startTimeDistribution.setMean(Double.valueOf(mean));
	}
	
	public void setHoldingProperties(String mean){
		endTimeDistribution.setSeed(seed);
		endTimeDistribution.setMean(Double.valueOf(mean));
	}
	
	public void setDeviceProperties(String degree){
	   this.degree=Integer.valueOf(degree);
	   deviceDistribution.setSeed(seed);
       deviceDistribution.setRange(0,deviceService.getTotalDevice()-1);
	}
	
	public void setBandwidthProperties(String mean, String sd){
		bandwidthDistribution.setSeed(seed);
		bandwidthDistribution.setMeanDeviation(Integer.valueOf(mean), Integer.valueOf(sd));
	}
	
	public void setFailureProperties(String failure){
		this.failure=Byte.valueOf(failure);
		if(this.failure==1)
			linkService.generateLinkFailures(seed);
	}
	
	public byte getFailure(){
		return failure;
	}

	public long connectionStartTime(){
    	startTime=startTime+(int)startTimeDistribution.getSample();
    	return startTime;
    }
    
    public long getConnectionStartTime() {
		return startTime;
	}
    
    public long connectionEndTime(){
    	endTime=startTime+(int)endTimeDistribution.getSample();
    	return endTime;
    }
    
    public long getConnectionEndTime() {
		return endTime;
	}
    
    public String getConnectionDevice(int device){
    	if(devices.isEmpty()){
    		 devices=deviceDistribution.getUniqueSamples(degree+1);//+1 for source
    	}
    	if(ports[device]==null){
    		ports[device]= deviceService.getPort(devices.get(device).intValue());  
    	}
        return deviceService.getDevice(devices.get(device).intValue());
    }
    
    public String getConnectionPort(int device){
        return ports[device];
    }
    
    public int bandwidth(){
    	bandwidth=(int)bandwidthDistribution.getSample();
    	return bandwidth;
    }
    
    public int getBandwidth(){
    	return bandwidth;
    }
    
    public List<String> getAzRoute() {
		return azRoute;
	}

	public void setAzRoute(List<String> azRoute) {
		this.azRoute = azRoute;
	}

	public List<String> getZaRoute() {
		return zaRoute;
	}

	public void setZaRoute(List<String> zaRoute) {
		this.zaRoute = zaRoute;
	}
	
	public String getSurviva() {
		return surviva;
	}

	public void setSurviva(String surviva) {
		this.surviva = surviva;
	}
    
    public List<String> getFailedLinks(){
    	if(failure==1)
    		return linkService.getFailedLinks(startTime);
    	else
    		return blacklist;
    }
    
    public void clearRequest(){
    	devices.clear();
    	ports=new String[degree+1];
    	blacklist.clear();
    	azRoute.clear();
    	zaRoute.clear();
    }
}
