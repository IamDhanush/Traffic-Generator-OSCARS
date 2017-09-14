/**
 * 
 */
package com.acnl.oscartrafficgenerator.application.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import com.acnl.oscartrafficgenerator.buisness.service.GenericBuilderService;
import com.acnl.oscartrafficgenerator.buisness.service.RequestService;
import com.acnl.oscartrafficgenerator.model.request.AdvancedRequestApi;
import com.acnl.oscartrafficgenerator.model.request.BandwidthRequestApi;
import com.acnl.oscartrafficgenerator.model.request.AdvancedRequestFlowApi;

/**
 * @author vsundarrajan
 *
 */
@Import({DistributionConfig.class,TopoConfig.class})
@Configuration
@PropertySource("classpath:config.properties")
public class RequestConfig {
	
	@Value("${request.startdate}")
    private String startDate;
	
	@Autowired
	private DistributionConfig distributionConfig;
	
	@Autowired
	private TopoConfig topoConfig;

	@Bean
	@Scope("singleton")
	public BandwidthRequestApi bandwidthRequestModel() throws Exception{
		return new BandwidthRequestApi();
	}
	
	@Bean
	@Scope("singleton")
	public AdvancedRequestApi advancedRequestApi() throws Exception{
		return new AdvancedRequestApi(startDate);
	}
	
	@Bean
	@Scope("singleton")
	public RequestService requestService() throws Exception{
		return new RequestService(distributionConfig.exponentialDistributionSupplier(),distributionConfig.exponentialDistributionSupplier(),distributionConfig.uniformDistributionSupplier(),distributionConfig.normalDistributionSupplier(),topoConfig.devicesService(),topoConfig.linksService());
	}
	
	@Bean
	public GenericBuilderService<AdvancedRequestApi> advancedRequestBuilder() throws Exception{
		return new GenericBuilderService<AdvancedRequestApi>(advancedRequestApi());
	}
	
	@Bean
	public GenericBuilderService<AdvancedRequestFlowApi> flowBuilder() throws Exception{
		return new GenericBuilderService<AdvancedRequestFlowApi>(new AdvancedRequestFlowApi());
	}
	
	
	@Bean
	public GenericBuilderService<RequestService> requestServiceBuilder()throws Exception{
		return new GenericBuilderService<RequestService>(requestService());
	}
}
