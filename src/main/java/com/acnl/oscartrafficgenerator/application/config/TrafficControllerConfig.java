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
import com.acnl.oscartrafficgenerator.buisness.controller.trafficcontrollerimpl.OscarTrafficController;
import com.acnl.oscartrafficgenerator.buisness.controller.trafficcontrollerimpl.OscarTrafficRestorationController;
/**
 * @author vsundarrajan
 *
 */
@Configuration
@Import({RequestConfig.class, RestControllerConfig.class,PathConfig.class,TrafficServiceConfig.class})
public class TrafficControllerConfig {

	@Bean
	@Scope("singleton")
	@Qualifier("oscarTrafficRestorationController")
	public TrafficControllerInterface oscarTrafficRestorationController() throws Exception{
		return new OscarTrafficRestorationController();
	}
	
	@Bean
	@Scope("singleton")
	@Qualifier("oscarTrafficController")
	public TrafficControllerInterface oscarTrafficController() throws Exception{
		return new OscarTrafficController();
	}
}
