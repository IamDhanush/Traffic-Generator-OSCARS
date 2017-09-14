/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.service;

import java.io.FileWriter;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.acnl.oscartrafficgenerator.model.response.ResponseApi;

/**
 * @author vsundarrajan
 *
 */
public class OscarResponseService {

	/* (non-Javadoc)
	 * @see com.acnl.oscartrafficgenerator.buisness.TrafficServiceInterface#submitRequest()
	 */
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private PathService pathService;
	
	@Autowired
	private LinksService linkService;
	
	@Autowired
	private ResponseService responseService;
	
	@Autowired
	private FileService fileService;

	
	private boolean palindrome=false,failure=false,restoration=false,restoreStatus;
	private int numberOfPaths, oscarAborts=0, failedRequests=0,connectionId;
	private Boolean[] pathStatus;
	private int[] failureCountsPath,successPathHops,failedPathHops;
	
	public void  setOscarResponseService(boolean restore) throws Exception{
		if(requestService.getFailure()==1){
			this.failure=true;
		}
		if(requestService.getPalindrome().equals("PALINDROME")){
			this.palindrome=true;
		}
		this.restoration=restore;
		this.numberOfPaths=Integer.valueOf(requestService.getNumDisjointPaths());
		pathStatus=new Boolean[numberOfPaths];
		failureCountsPath=new int[numberOfPaths];
		successPathHops=new int[numberOfPaths];
		failedPathHops=new int[numberOfPaths];
	}
	
	public void updateResponse(ResponseApi response, int connId) throws Exception {
		responseService.setResponse(response);
		this.connectionId=connId;
		if(response.getStatus().equals("HELD") || response.getStatus().equals("COMMITTED")){
	    	this.processPaths();
	    	if(failure){
	    		this.updatePathStatus();
		    	if(Stream.of(pathStatus).allMatch((i)->i==false)){//all paths failed
		    		failedRequests++;
		    		restoreStatus=true;
		    	}
		    	else if(Stream.of(pathStatus).anyMatch((i)->i==false)){
		    		restoreStatus=true;
		    	}
		    	else{
		    		restoreStatus=false;
		    	}
	    	}
	    	else{
	    		//No failure
	    	}
	    }
	    else if(response.getStatus().equals("ABORTED")){
	    	oscarAborts++;
	    	restoreStatus=false;
	    }
	}
	
	public boolean getRestoreStatus(){
		return restoreStatus;
	}
	
	public List<String> getPath(int path){
		return responseService.getPathLinks(path);
	}
	
	private void processPaths() throws Exception {
    	for(int path=0;path<numberOfPaths;path++){
    		responseService.getHopCounts("AZ",path);
    		if(!palindrome)	
    			responseService.getHopCounts("ZA",path);
    	} 
    	responseService.sortPaths();
	}
	
	private void updatePathStatus() throws Exception{
		for(int i=0;i<numberOfPaths;i++){
			if(pathFail(i)){//path fails
				pathStatus[i]=false;
				if(restoration)
					pathService.updateRecentlyFailedPath(connectionId, i);
				failureCountsPath[i]=failureCountsPath[i]+1;
				failedPathHops[i]=failedPathHops[i]+responseService.getHopCounts(i);	
			}
			else{//path succeeds
				pathStatus[i]=true;
				successPathHops[i]=successPathHops[i]+responseService.getHopCounts(i);
			}
		}
	}
	
	private boolean pathFail(int path) throws Exception{
		return pathService.isPathFailed(responseService.getPathLinks(path), requestService.getConnectionEndTime());
	}
	
	/*private boolean pathOverlap() throws Exception{
		if(palindrome)
			return pathService.isPathOverlapped(responseService.getPathLinks(0),responseService.getPathLinks(1) ,requestService.getConnectionStartTime(), requestService.getConnectionEndTime());
		else // non-palindrome
			return false;
	}*/
	
	public void writeToFile(double simTime) throws Exception {
		FileWriter fileWriter=fileService.getOutputFile();
		fileWriter.write("\nSeed No: "+requestService.getSeed()+"\nBlocking Probability: "+((float)oscarAborts/(float)requestService.getTotalRequests())+
				"\nFailure Probability: "+((float)failedRequests/(float)(requestService.getTotalRequests()-oscarAborts))
				+"\nAborted by Oscars: "+oscarAborts+"\nCommitted, then failed: "+failedRequests);
		if(palindrome){
	    	 for(int i=0;i<numberOfPaths;i++){
	    		 fileWriter.write("\nOnly Path "+(i+1)+" fails: "+(failureCountsPath[i]-failedRequests)
	    				 +"\nHop counts when path "+(i+1)+" fails: "+(float)failedPathHops[i]/(float)failureCountsPath[i]
	    				 +"\nHop counts when path "+(i+1)+" succeeds: "+(float)successPathHops[i]/(float)(requestService.getTotalRequests()-failureCountsPath[i]));
	    	 }
	    }	
		fileWriter.write("\nTotal Failures: "+linkService.totFailures()+"\nStartTime: "+requestService.getConnectionStartTime()
		        +"\nEndTime: "+requestService.getConnectionEndTime()
		        +"\nSimulation Time: "+simTime+" Minutes"
		        +"\nTotalRequests: "+ requestService.getTotalRequests());
		fileWriter.close();
	}
	
	public void printAllPaths(){
		for(int path=0;path<numberOfPaths;path++){
			System.out.println(responseService.getPathLinks(path));
		}
	}
	
	public void clear(){
		responseService.clearResponse();
	}
}

