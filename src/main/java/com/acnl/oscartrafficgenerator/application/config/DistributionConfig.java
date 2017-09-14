/**
 * 
 */
package com.acnl.oscartrafficgenerator.application.config;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.acnl.oscartrafficgenerator.buisness.service.DistributionServiceInterface;
import com.acnl.oscartrafficgenerator.buisness.service.distributionserviceimpl.ExponentiallyDistribute;
import com.acnl.oscartrafficgenerator.buisness.service.distributionserviceimpl.NormallyDistribute;
import com.acnl.oscartrafficgenerator.buisness.service.distributionserviceimpl.UniformlyDistribute;

/**
 * @author vishal
 *
 */
@Component
@Configuration
public class DistributionConfig {

	@Qualifier("exponentialDistribution")
	@Bean
	@Scope("prototype")
	public DistributionServiceInterface exponentialDistribution() {
		return new ExponentiallyDistribute();
	}
	
	@Qualifier("normalDistribution")
	@Bean
	@Scope("prototype")
	public DistributionServiceInterface normalDistribution() {
		return new NormallyDistribute();
	}
	
	@Qualifier("uniformDistribution")
	@Bean
	@Scope("prototype")
	public DistributionServiceInterface uniformDistribution() {
		return new UniformlyDistribute();
	}
	
	@Bean
	  public Supplier<DistributionServiceInterface> exponentialDistributionSupplier() {
	      return this::exponentialDistribution;
	  }
	
	@Bean
	  public Supplier<DistributionServiceInterface> normalDistributionSupplier() {
	      return this::normalDistribution;
	  }
	
	@Bean
	  public Supplier<DistributionServiceInterface> uniformDistributionSupplier() {
	      return this::uniformDistribution;
	  }
	
	
}
