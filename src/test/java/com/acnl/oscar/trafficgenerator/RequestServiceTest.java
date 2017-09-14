/**
 * 
 */
package com.acnl.oscar.trafficgenerator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.acnl.oscartrafficgenerator.application.config.RequestConfig;
import com.acnl.oscartrafficgenerator.buisness.service.GenericBuilderService;
import com.acnl.oscartrafficgenerator.buisness.service.RequestService;;

/**
 * @author vsundarrajan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RequestConfig.class}, loader=AnnotationConfigContextLoader.class)
public class RequestServiceTest {
	
	
	@Autowired
	private GenericBuilderService<RequestService> requestServiceBuilder;
	
	@Autowired
	private RequestService requestService;
	
	@Test
	public void testRequestServiceBuilder() {
	    RequestService requestService=requestServiceBuilder.with(s->s.setSeed("1000")).with(s->s.setArrivalProperties("1000")).with(s->s.setHoldingProperties("1000")).with(s->s.setDeviceProperties("3")).with(s->s.setBandwidthProperties("500","20")).with(s->s.setFailureProperties("1")).get();
	    /*System.out.println(requestService.connectionStartTime());
	    System.out.println(requestService.connectionEndTime());
	    System.out.println(requestService.getBandwidth());
	    System.out.println(requestService.getConnectionDevice(3));
	    System.out.println(new String[]{requestService.getConnectionPort(3)});
	    Collection<String> blacklist=requestService.addFailedLinks(new LinkedList<String>(),1000);
	    for(String s:blacklist){
	    	System.out.println(s);*/
	    }
	    
	@Test
	public void testGetConnectionDevice(){
		RequestService requestService=requestServiceBuilder.with(s->s.setSeed("1000")).with(s->s.setDeviceProperties("1")).get();
	    System.out.println(requestService.getConnectionDevice(0)+" "+requestService.getConnectionDevice(1));
	    Arrays.stream(requestService.getConnectionPort(1)).forEach(i->System.out.println(i));
	}
	
	@Test
	public void testStartEndTime(){
		RequestService requestService=requestServiceBuilder.with(s->s.setSeed("1000")).with(s->s.setArrivalProperties("1000")).with(s->s.setHoldingProperties("1000")).with(s->s.setDeviceProperties("3")).with(s->s.setBandwidthProperties("500","20")).with(s->s.setFailureProperties("1")).get();
	    /*System.out.println(requestService.connectionStartTime());
		System.out.println(requestService.connectionEndTime());
		System.out.println(requestService.connectionStartTime());
		System.out.println(requestService.connectionEndTime());
		System.out.println(requestService.connectionStartTime());
		System.out.println(requestService.connectionEndTime());*/
	}
	
	@Test
	public void testBlackList() throws Exception{
		RequestService requestService=requestServiceBuilder.with(s->s.setSeed("1000")).with(s->s.setArrivalProperties("1000")).with(s->s.setHoldingProperties("1000")).with(s->s.setDeviceProperties("3")).with(s->s.setBandwidthProperties("500","20")).with(s->s.setFailureProperties("1")).get();
		requestService.connectionStartTime();
		//requestService.addBlacklists(new LinkedList<String>(Arrays.asList("hello")));
		System.out.println(requestService.getFailedLinks());
	}
}
