/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.controller.trafficcontrollerimpl;

import java.util.function.Supplier;

/**
 * @author vsundarrajan
 *
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.acnl.oscartrafficgenerator.buisness.controller.RestController;
import com.acnl.oscartrafficgenerator.buisness.controller.TrafficControllerInterface;
import com.acnl.oscartrafficgenerator.buisness.service.DistributionServiceInterface;
import com.acnl.oscartrafficgenerator.buisness.service.GenericBuilderService;
import com.acnl.oscartrafficgenerator.buisness.service.OscarResponseService;
import com.acnl.oscartrafficgenerator.buisness.service.RequestService;
import com.acnl.oscartrafficgenerator.model.request.AdvancedRequestApi;
import com.acnl.oscartrafficgenerator.model.request.AdvancedRequestFlowApi;

/**
 * @author vsundarrajan
 *
 */
public class OscarTrafficController implements TrafficControllerInterface {
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private GenericBuilderService<AdvancedRequestApi> advancedRequestBuilder;
	
	@Autowired
	private Supplier<GenericBuilderService<AdvancedRequestFlowApi>> advancedRequestFlowSupplier;
	
	@Autowired
	private RestController restController;
	
	@Autowired
	private OscarResponseService oscarResponseService;
	
	private AdvancedRequestFlowApi[] flowArr; 
	
	private GenericBuilderService<AdvancedRequestFlowApi>[] flowBuilder;
	
	private int connectionId=1000,startTime=0,flow;
	
	private void setInitialRequestProperties() throws Exception{//static request parameters
		advancedRequestBuilder.with(s->s.setDescription("Test"))
				.with(s->s.setUsername("username"))
				.with(s->s.setMinNumFlows(requestService.getMinNumFlows()))
				.with(s->s.setMaxNumFlows(requestService.getMaxNumFlows())).get();
		flowArr=new AdvancedRequestFlowApi[requestService.getDegree()];
		flowBuilder=new GenericBuilderService[requestService.getDegree()];
		for(int i=0;i<requestService.getDegree();i++){
			flowBuilder[i]=advancedRequestFlowSupplier.get();
			flowArr[i]=flowBuilder[i].with(s->s.setAzRoute(requestService.getAzRoute()))
					.with(s->s.setZaRoute(requestService.getZaRoute()))
					.with(s->s.setSurvivability(requestService.getSurviva()))
					.with(s->s.setDestVlan("any")).with(s->s.setSourceVlan("any"))
					.with(s->s.setPalindromic(requestService.getPalindrome()))
					.with(s->s.setNumPaths(requestService.getNumDisjointPaths())).get();		
		}
	}
	
	private AdvancedRequestFlowApi[] getFlow(int degree){
		for(flow=0;flow<degree;flow++){
			flowArr[flow]=flowBuilder[flow].with(s->s.setSourceDevice(requestService.getConnectionDevice(0)))
					.with(s->s.setSourcePorts(new String[]{requestService.getConnectionPort(0)}))
					.with(s->s.setDestDevice(requestService.getConnectionDevice(flow+1)))
					.with(s->s.setDestPorts(new String[]{requestService.getConnectionPort(flow+1)}))
					.with(s->s.setAzMbps(requestService.bandwidth()))
					.with(s->s.setZaMbps(requestService.getBandwidth()))
					.with(s->s.setBlacklist(requestService.getFailedLinks())).get();
		}
		return flowArr;
	}
	
	@Override
	public void generateTraffic() throws Exception{
		this.setInitialRequestProperties();
		oscarResponseService.setOscarResponseService(false);
		int currentRequest=0,totalRequest=requestService.getTotalRequests(),timeInterval=10000;
		long millis = System.currentTimeMillis();
		while(startTime<8760){
			startTime=(int)requestService.connectionStartTime();
			advancedRequestBuilder.with(s->s.setConnectionId(connectionId))
					.with(s->s.setStart(startTime))
					.with(s->s.setEnd(requestService.connectionEndTime()))
					.with(s->s.setFlows(getFlow(requestService.getDegree()))).get(); 
			oscarResponseService.updateResponse(restController.sendRequest("add"), connectionId);
			/*if(startTime>=timeInterval){
				oscarResponseService.writeNormalizedFailures(currentRequest);
				timeInterval=timeInterval+10000;
			}*/
			connectionId++;
			currentRequest++;
			requestService.clearRequest();
			oscarResponseService.clear();
		}
		//oscarResponseService.writeNormalizedFailures(currentRequest);
		oscarResponseService.writeToFile((((double)System.currentTimeMillis())-(double)millis)/60000,currentRequest);	
	}
}
	
	
		


