/**
 * 
 */
package com.acnl.oscartrafficgenerator.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import com.acnl.oscartrafficgenerator.buisness.service.FileService;
import com.acnl.oscartrafficgenerator.buisness.service.ResponseService;
import com.acnl.oscartrafficgenerator.model.response.ResponseApi;

/**
 * @author vsundarrajan
 *
 */
@Configuration
@PropertySource("classpath:config.properties")
public class ResponseConfig {
	
	@Value("${fileservice.output}")
	private String outputFile;
	
	@Value("${filservice.bandwidth}")
	private String bandwidthFile; 
	
	@Bean
	@Scope("singleton")
	public ResponseApi responseApi() throws Exception{
		return new ResponseApi();
	}
	
	@Bean
	@Scope("singleton")
	public ResponseService responseService() throws Exception{
		return new ResponseService();
	}
	
	@Bean
	@Scope("singleton")
	public FileService fileService() throws Exception{
		return new FileService(outputFile,bandwidthFile);
	}
}
