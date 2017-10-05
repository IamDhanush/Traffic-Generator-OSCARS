/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.controller.trafficcontrollerimpl;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.acnl.oscartrafficgenerator.buisness.controller.RestController;
import com.acnl.oscartrafficgenerator.buisness.controller.TrafficControllerInterface;
import com.acnl.oscartrafficgenerator.buisness.service.GenericBuilderService;
import com.acnl.oscartrafficgenerator.buisness.service.PathService;
import com.acnl.oscartrafficgenerator.buisness.service.OscarResponseService;
import com.acnl.oscartrafficgenerator.buisness.service.RequestService;
import com.acnl.oscartrafficgenerator.model.request.AdvancedRequestApi;
import com.acnl.oscartrafficgenerator.model.request.AdvancedRequestFlowApi;

/**
 * @author vsundarrajan
 *
 */
public class OscarTrafficRestorationController implements TrafficControllerInterface {
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private GenericBuilderService<AdvancedRequestApi> advancedRequestBuilder;
	
	@Autowired
	private GenericBuilderService<AdvancedRequestFlowApi> flowBuilder;
	
	@Autowired
	private RestController restController;
	
	@Autowired
	private PathService pathService;
	
	@Autowired
	private OscarResponseService oscarResponseService;
	
	private AdvancedRequestFlowApi[] flow; 
	
	private int connectionId=1000,startTime=0,numDisJointPaths,path;
	
	public void setInitialRequestProperties() throws Exception{//static request parameters
		advancedRequestBuilder.with(s->s.setDescription("Test"))
				.with(s->s.setUsername("username"))
				.with(s->s.setMinNumFlows(requestService.getMinNumFlows()))
				.with(s->s.setMaxNumFlows(requestService.getMaxNumFlows())).get();
		flow=new AdvancedRequestFlowApi[1];//1-default value of flow for survivability
		flow[0]=flowBuilder.with(s->s.setDestVlan("any"))
				.with(s->s.setSourceVlan("any"))
				.with(s->s.setPalindromic(requestService.getPalindrome())).get();
	}
	
	@Override
	public void generateTraffic() throws Exception{
		this.setInitialRequestProperties();
		oscarResponseService.setOscarResponseService(true);
		int currentRequest=0,totalRequest=requestService.getTotalRequests();
	    numDisJointPaths=Integer.valueOf(requestService.getNumDisjointPaths());
		long millis = System.currentTimeMillis();
		while(startTime<50000){
			startTime=(int)requestService.connectionStartTime();
			pathService.getFailedPathIds(startTime).stream().filter(Objects::nonNull)
				.forEach(id->restController.abortRequest(id));
			flow[0]=flowBuilder.with(s->s.setSourceDevice(requestService.getConnectionDevice(0)))
					.with(s->s.setSourcePorts(new String[]{requestService.getConnectionPort(0)}))
					.with(s->s.setDestDevice(requestService.getConnectionDevice(1)))
					.with(s->s.setDestPorts(new String[]{requestService.getConnectionPort(1)}))
					.with(s->s.setAzMbps(requestService.bandwidth()))
					.with(s->s.setZaMbps(requestService.getBandwidth()))
					.with(s->s.setAzRoute(requestService.getAzRoute()))
					.with(s->s.setZaRoute(requestService.getZaRoute()))
					.with(s->s.setSurvivability("TOTAL"))
					.with(s->s.setBlacklist(requestService.getFailedLinks()))
					.with(s->s.setNumPaths(requestService.getNumDisjointPaths())).get();
			advancedRequestBuilder.with(s->s.setConnectionId(connectionId))
					.with(s->s.setStart(startTime))
					.with(s->s.setEnd(requestService.connectionEndTime()))
					.with(s->s.setFlows(flow)).get(); 
			oscarResponseService.updateResponse(restController.sendRequest("add"), connectionId);
			if((!oscarResponseService.getRestoreStatus())){
			}
			else{//if the path is committed but then failed
				restController.abortRequest(connectionId);
				submitEquivalentUnicast();
			}
			connectionId++;
			currentRequest++;
		 requestService.clearRequest();
		 oscarResponseService.clear();
		}
		oscarResponseService.writeToFile((((double)System.currentTimeMillis())-(double)millis)/60000,currentRequest);	
	}
	
	private void submitEquivalentUnicast() throws Exception{
		for(path=0;path<numDisJointPaths;path++){
			connectionId=connectionId+1;
			flow[0]=flowBuilder.with(s->s.setAzRoute(oscarResponseService.getPath(path)))
					.with(s->s.setZaRoute(StreamSupport.stream(Spliterators.spliteratorUnknownSize(oscarResponseService.getPath(path).stream()
				    	            .collect(Collectors.toCollection(LinkedList::new))
				    	            .descendingIterator(),Spliterator.ORDERED), false)
									.collect(Collectors.<String> toList())))
					.with(s->s.setSurvivability("NONE"))
					.with(s->s.setNumPaths("1")).get();
			advancedRequestBuilder.with(s->s.setConnectionId(connectionId))
				.with(s->s.setFlows(flow)).get();
			restController.sendRequest("add");
			//oscarResponseService.updateResponse(restController.sendRequest("add"),connectionId);
			//oscarResponseService.printAllPaths();
		}
	}	
}
	
	
		


