/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.controller.trafficcontrollerimpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.acnl.oscartrafficgenerator.buisness.controller.RestController;
import com.acnl.oscartrafficgenerator.buisness.controller.TrafficControllerInterface;
import com.acnl.oscartrafficgenerator.buisness.service.GenericBuilderService;
import com.acnl.oscartrafficgenerator.buisness.service.OscarResponseService;
import com.acnl.oscartrafficgenerator.buisness.service.RequestService;
import com.acnl.oscartrafficgenerator.model.request.AdvancedRequestApi;
import com.acnl.oscartrafficgenerator.model.request.AdvancedRequestFlowApi;

/**
 * @author vsundarrajan
 *
 */
public class UnicastController implements TrafficControllerInterface {

	@Autowired
	private RequestService requestService;
	
	@Autowired
	private GenericBuilderService<AdvancedRequestApi> advancedRequestBuilder;
	
	@Autowired
	private GenericBuilderService<AdvancedRequestFlowApi> flowBuilder;
	
	@Autowired
	private RestController restController;
	
	@Autowired
	private OscarResponseService oscarResponseService;
	
	private AdvancedRequestFlowApi[] flow; 
	
	private int connectionId=1000;
	
	private void setInitialRequestProperties() throws Exception{//static request parameters
		advancedRequestBuilder.with(s->s.setDescription("Test"))
				.with(s->s.setUsername("username"))
				.with(s->s.setMinNumFlows(requestService.getMinNumFlows()))
				.with(s->s.setMaxNumFlows(requestService.getMaxNumFlows())).get();
		flow=new AdvancedRequestFlowApi[1];//1-default value of flow for survivability
		flow[0]=flowBuilder.with(s->s.setAzRoute(requestService.getAzRoute()))
				.with(s->s.setZaRoute(requestService.getZaRoute()))
				.with(s->s.setSurvivability(requestService.getSurviva()))
				.with(s->s.setDestVlan("any")).with(s->s.setSourceVlan("any"))
				.with(s->s.setPalindromic(requestService.getPalindrome()))
				.with(s->s.setNumPaths(requestService.getNumDisjointPaths())).get();
	}
	
	@Override
	public void generateTraffic() throws Exception{
		this.setInitialRequestProperties();
		oscarResponseService.setOscarResponseService(false);
		int i=0,totalRequest=requestService.getTotalRequests();
		long millis = System.currentTimeMillis();
		while(i<totalRequest){
			flow[0]=flowBuilder.with(s->s.setSourceDevice(requestService.getConnectionDevice(0)))
					.with(s->s.setSourcePorts(requestService.getConnectionPort(0)))
					.with(s->s.setDestDevice(requestService.getConnectionDevice(1)))
					.with(s->s.setDestPorts(requestService.getConnectionPort(1)))
					.with(s->s.setAzMbps(requestService.bandwidth()))
					.with(s->s.setZaMbps(requestService.getBandwidth()))
					.with(s->s.setBlacklist(requestService.getFailedLinks())).get();
			advancedRequestBuilder.with(s->s.setConnectionId(connectionId))
					.with(s->s.setStart(requestService.connectionStartTime()))
					.with(s->s.setEnd(requestService.connectionEndTime()))
					.with(s->s.setFlows(flow)).get(); 
			oscarResponseService.updateResponse(restController.sendRequest("add"), connectionId);
			connectionId++;
			i++;
		 requestService.clearRequest();
		 oscarResponseService.clear();
		}
		requestService.setTotalRequests(String.valueOf(i));
		oscarResponseService.writeToFile((((double)System.currentTimeMillis())-(double)millis)/60000);	
	}
}
