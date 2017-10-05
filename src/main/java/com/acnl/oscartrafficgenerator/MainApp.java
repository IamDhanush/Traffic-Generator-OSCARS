/**
 * 
 */
package com.acnl.oscartrafficgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.acnl.oscartrafficgenerator.application.config.MainAppConfig;
import com.acnl.oscartrafficgenerator.buisness.controller.TrafficControllerInterface;
import com.acnl.oscartrafficgenerator.buisness.service.GenericBuilderService;
import com.acnl.oscartrafficgenerator.buisness.service.RequestService;


/**
 * @author vsundarrajan
 *
 */
public class MainApp {
	
	@Autowired
	GenericBuilderService<RequestService> requestServiceBuilder;
	
	public static void main(String[] args) throws Exception{
		
		ApplicationContext appCtx = 
		         new AnnotationConfigApplicationContext(MainAppConfig.class);
		MainApp mainApp= appCtx.getBean(MainApp.class);
		mainApp.requestServiceBuilder.with(s->s.setSeed(args[0])).with(s->s.setTotalRequests(args[1]))
		.with(s->s.setPalindrome(args[2])).with(s->s.setNumDisjointPaths(args[3]))
		.with(s->s.setMinNumFlows(args[4])).with(s->s.setMaxNumFlows(args[5]))
		.with(s->s.setArrivalProperties(args[6])).with(s->s.setHoldingProperties(args[7]))
		.with(s->s.setDeviceProperties(args[8])).with(s->s.setBandwidthProperties(args[9], args[10]))
		.with(s->s.setDegree(args[11])).with(s->s.setFailureProperties(args[12]))
		.with(s->s.setSurviva(args[14])).get();
		
		TrafficControllerInterface trafficController=(TrafficControllerInterface)appCtx.getBean(args[13]);
	    trafficController.generateTraffic();
	    ((ConfigurableApplicationContext)appCtx).close();
	}

}
