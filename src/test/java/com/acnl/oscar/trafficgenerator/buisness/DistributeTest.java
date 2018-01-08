/**
 * 
 */
package com.acnl.oscar.trafficgenerator.buisness;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import static org.hamcrest.CoreMatchers.instanceOf;

import com.acnl.oscartrafficgenerator.application.config.DistributionConfig;
import com.acnl.oscartrafficgenerator.buisness.service.DistributionServiceInterface;
import com.acnl.oscartrafficgenerator.buisness.service.distributionserviceimpl.UniformlyDistribute;

/**
 * @author vishal
 *
 */

/**
 * @author vishal
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DistributionConfig.class, loader=AnnotationConfigContextLoader.class)

public class DistributeTest  {
	
	@Autowired
    @Qualifier("exponentialDistribution")
    DistributionServiceInterface edistribution,edist1;
    @Autowired
    @Qualifier("uniformDistribution")
    DistributionServiceInterface udistribution;
    @Autowired
    @Qualifier("normalDistribution")
    DistributionServiceInterface ndistribution;
    
	@Test
	public void testExponentialDistribution()
	{
	  //edistribution.setSeed(1001);
	  //edistribution.setMean(20);
	  for(int i=0;i<100;i++){
		  edistribution.setSeed(3000+i);
		  edistribution.setMean(20);
		  System.out.println(edistribution.getSample());  
	  }
	}
	
	@Test
	public void testUniformDistribution()
	{
	  udistribution.setSeed(1002);
	  udistribution.setRange(0, 50);
	  //System.out.println(udistribution.getSample());
	  //assertThat((UniformlyDistribute)udistribution,instanceOf(UniformlyDistribute.class));
	  assertTrue(udistribution instanceof UniformlyDistribute);
	  UniformlyDistribute ud=(UniformlyDistribute)udistribution;
	  //ud.createDistribution(1001, 0, 5);
	  //System.out.println(ud.getUniqueSamples(5));
	}
	
	@Test
	public void testNormalDistribution()
	{
	  ndistribution.setSeed(1000);
	  ndistribution.setMeanDeviation(2000, 50);
	  //System.out.println(ndistribution.getSample());
	}

}
