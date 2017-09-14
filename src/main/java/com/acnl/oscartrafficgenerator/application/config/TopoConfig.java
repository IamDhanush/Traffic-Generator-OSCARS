/**
 * 
 */
package com.acnl.oscartrafficgenerator.application.config;

import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import com.acnl.oscartrafficgenerator.buisness.service.DevicesService;
import com.acnl.oscartrafficgenerator.buisness.service.LinksService;
import com.acnl.oscartrafficgenerator.model.Device;
import com.acnl.oscartrafficgenerator.model.Link;
/**
 * @author vishal
 *
 */
@Configuration
@Import(DistributionConfig.class)
@PropertySource("classpath:config.properties")
public class TopoConfig {
	
    @Value("${buried.link.fit}")
    private double buriedLinkFIT;
	@Value("${buried.amplifier.fit}")
    private double buriedAmplifierFIT;
    @Value("${overseas.link.fit}")
    private double overseasLinkFIT;
    @Value("${overseas.amplifier.fit}")
    private double overseasAmplifierFIT;
    @Value("${amplifier_placements.km}")
    private double amplifierPlacements;
    @Value("${buried.link.MTTR}")
    private double buriedMTTR;
    @Value("${overseas.link.MTTR}")
    private double overseasMTTR;
    
	@Autowired
	private DistributionConfig distributionConfig;
	
	@Bean
	public DevicesService devicesService() throws Exception {
		return new DevicesService(distributionConfig.uniformDistributionSupplier(),new ObjectMapper().readValue(new File(getClass().getClassLoader().getResource("Topo/esnet-devices.json").getFile()),Device[].class));
	}
	
	
	@Bean
	@Scope("singleton")
	public LinksService linksService() throws Exception {
		return new LinksService(distributionConfig.exponentialDistributionSupplier(),links(), buriedLinkFIT,overseasLinkFIT,buriedAmplifierFIT,overseasAmplifierFIT,amplifierPlacements,buriedMTTR,overseasMTTR);
	}
	
	@Bean
	@Scope("singleton")
	public List<Link> links() throws Exception{
		return Stream.of(new ObjectMapper().readValue(new File(getClass().getClassLoader().getResource("Topo/esnet-adjcies.json").getFile()), Link[].class)).collect(Collectors.toCollection(LinkedList::new)); 	
	}
	
}
