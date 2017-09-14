/**
 * 
 */
package com.acnl.oscartrafficgenerator.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.acnl.oscartrafficgenerator.buisness.service.OscarResponseService;

/**
 * @author vsundarrajan
 *
 */
@Configuration
@Import({RequestConfig.class,ResponseConfig.class,PathConfig.class, TopoConfig.class})
public class TrafficServiceConfig {

	@Bean
	public OscarResponseService oscarResponseService() throws Exception{
		return new OscarResponseService();
	}
}
