/**
 * 
 */
package com.acnl.oscar.trafficgenerator.buisness;

/*import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

import com.acnl.oscartrafficgenerator.application.config.RequestConfig;
import com.acnl.oscartrafficgenerator.application.config.RestConfig;
import com.acnl.oscartrafficgenerator.buisness.service.GenericBuilderService;
import com.acnl.oscartrafficgenerator.buisness.service.RequestService;
import com.acnl.oscartrafficgenerator.model.request.AdvancedRequestApi;
import com.acnl.oscartrafficgenerator.model.request.BandwidthRequestApi;
import com.acnl.oscartrafficgenerator.model.request.AdvancedRequestFlowApi;

/**
 * @author vishal
 *
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RestConfig.class,RequestConfig.class}, loader=AnnotationConfigContextLoader.class)
public class TestRestTemplate  {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private BandwidthRequest bandwidthRequest;
	
	@Autowired
	private AdvancedRequest advancedRequest;
	
	@Autowired
	GenericBuilderService<RequestService> requestServiceBuilder;
	
	@Autowired
	private Flow flow;
	
	@Test
	public void testRestTemplateForConnectionGet() throws Exception{
		    ResponseEntity<String> entity = restTemplate.getForEntity("https://localhost:8000/resv_simple/get/20001", String.class);
		    System.out.println(entity.getStatusCode());
		    System.out.println(entity);
	}
	
	@Test
	public void testRestTemplateForBandwidthRequestPost() throws Exception{
		    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date startDate = dateFormat.parse("2019-08-04");
		    Date endDate =  dateFormat.parse("2019-08-04");
			bandwidthRequest.setStartDate(dateFormat.format(startDate));
			bandwidthRequest.setEndDate(dateFormat.format(endDate));
		    ResponseEntity<String> entity = restTemplate.postForEntity("https://localhost:8000/bwavail/ports", bandwidthRequest, String.class);
		    System.out.println(entity.getStatusCode());
		    System.out.println(entity);
	}
	
	@Test
	public void testRestTemplateForAdvancedRequestPost() throws Exception{
		AdvancedRequestApi advancedRequest=new AdvancedRequestApi("12 12 2020 10:20");
		advancedRequest.setStart(new Integer(12));
		advancedRequest.setEnd(new Float(12+10));
		advancedRequest.setConnectionId("20001");
		advancedRequest.setMinNumFlows("1");
		advancedRequest.setMaxNumFlows("1");
		AdvancedRequestFlowApi flow= new AdvancedRequestFlowApi();
		flow.setAzMbps("10");
		flow.setZaMbps("10");
		flow.setAzRoute(new LinkedList<String>());
		flow.setZaRoute(new LinkedList<String>());
		flow.setBlacklist(new LinkedList<String>());
		flow.setSourceDevice("snll-mr2");
	    flow.setSourcePorts(new String[]{"snll-mr2:xe-0/0/0"});
	    flow.setDestDevice("sunn-cr5");
	    flow.setDestPorts(new String[]{"sunn-cr5:10/1/6"});
	    //advancedRequest.setFlows(new LinkedList<AdvancedRequestFlowApi>(Arrays.asList(flow)));
	    ResponseEntity<String> entity = restTemplate.postForEntity("https://localhost:8000/resv_simple/connection/add_commit", advancedRequest, String.class);
	    System.out.println(entity);
	}
}*/
