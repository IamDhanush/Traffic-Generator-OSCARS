package com.acnl.oscar.trafficgenerator.buisness;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.acnl.oscartrafficgenerator.application.config.RequestConfig;
import com.acnl.oscartrafficgenerator.application.config.TrafficControllerConfig;
import com.acnl.oscartrafficgenerator.buisness.controller.TrafficControllerInterface;
import com.acnl.oscartrafficgenerator.buisness.service.GenericBuilderService;
import com.acnl.oscartrafficgenerator.buisness.service.RequestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RequestConfig.class,TrafficControllerConfig.class}, loader=AnnotationConfigContextLoader.class)
public class TrafficControllerTest {

	
	@Autowired
	@Qualifier("bhandari")
	private TrafficControllerInterface trafficController;
	
	@Autowired
	GenericBuilderService<RequestService> requestServiceBuilder;
	
	@Test
	public void testGenerateTraffic() throws Exception {
		requestServiceBuilder.with(s->s.setSeed("1000")).with(s->s.setTotalRequests("1000"))
		.with(s->s.setPalindrome("PALINDROME")).with(s->s.setNumDisjointPaths("2"))
		.with(s->s.setMinNumFlows("1")).with(s->s.setMaxNumFlows("1"))
		.with(s->s.setArrivalProperties("2")).with(s->s.setHoldingProperties("1000"))
		.with(s->s.setDeviceProperties("1")).with(s->s.setBandwidthProperties("1000", "400"))
		.with(s->s.setFailureProperties("1")).get();
		trafficController.generateTraffic();
	}
	
	@Test
	public void reverseStreams() throws Exception{
		List<String> list = new LinkedList<String>(Arrays.asList("1", "2", "3", "4"));
		System.out.println(StreamSupport.stream(
	            Spliterators.spliteratorUnknownSize(list.stream()
	    	            .collect(Collectors.toCollection(LinkedList::new))
	    	            .descendingIterator(),Spliterator.ORDERED), false)
						.collect(Collectors.<String> toList()));
	   
	}
}
