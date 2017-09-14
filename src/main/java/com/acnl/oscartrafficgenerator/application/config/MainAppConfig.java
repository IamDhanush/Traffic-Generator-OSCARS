/**
 * 
 */
package com.acnl.oscartrafficgenerator.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.acnl.oscartrafficgenerator.MainApp;

/**
 * @author vsundarrajan
 *
 */
@Configuration
@Import({RequestConfig.class,TrafficControllerConfig.class})
public class MainAppConfig {
	
	@Bean
	public MainApp mainApp() throws Exception{
		return new MainApp();
	}

}
