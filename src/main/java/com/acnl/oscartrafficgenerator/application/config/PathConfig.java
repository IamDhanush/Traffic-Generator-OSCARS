/**
 * 
 */
package com.acnl.oscartrafficgenerator.application.config;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import com.acnl.oscartrafficgenerator.buisness.service.PathService;
import com.acnl.oscartrafficgenerator.model.Path;


/**
 * @author vsundarrajan
 *
 */
@Configuration
@Import(TopoConfig.class)
public class PathConfig {
	
	@Bean
	@Scope("singleton")
	public PathService pathService() throws Exception{
		return new PathService();
	}

	@Bean
	@Scope("prototype")
	public Path path(){
		return new Path();
	}
	
	@Bean
	public Supplier<Path> pathSupplier(){
    	return this::path;
    }
}
