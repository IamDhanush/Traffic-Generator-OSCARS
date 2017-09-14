/**
 * 
 */
package com.acnl.oscartrafficgenerator.application.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import com.acnl.oscartrafficgenerator.buisness.controller.TrafficControllerInterface;
import com.acnl.oscartrafficgenerator.buisness.controller.trafficcontrollerimpl.SurvivaController;
import com.acnl.oscartrafficgenerator.buisness.controller.trafficcontrollerimpl.SurvivaRestorationController;
/**
 * @author vsundarrajan
 *
 */
@Configuration
@Import({RequestConfig.class, RestControllerConfig.class,PathConfig.class,TrafficServiceConfig.class})
public class TrafficControllerConfig {

	@Bean
	@Scope("singleton")
	@Qualifier("survivaRestore")
	public TrafficControllerInterface survivaRestore() throws Exception{
		return new SurvivaRestorationController();
	}
	
	@Bean
	@Scope("singleton")
	@Qualifier("surviva")
	public TrafficControllerInterface surviva() throws Exception{
		return new SurvivaController();
	}
}
