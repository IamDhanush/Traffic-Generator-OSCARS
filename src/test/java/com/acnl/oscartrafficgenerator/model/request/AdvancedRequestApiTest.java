/**
 * 
 */
package com.acnl.oscartrafficgenerator.model.request;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.acnl.oscartrafficgenerator.application.config.RequestConfig;

/**
 * @author vsundarrajan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RequestConfig.class}, loader=AnnotationConfigContextLoader.class)
public class AdvancedRequestApiTest {

	@Autowired
	private AdvancedRequestApi advancedRequest;
	
	/**
	 * Test method for {@link com.acnl.oscartrafficgenerator.model.request.AdvancedRequestApi#setStart(java.lang.Number)}.
	 */
	@Test
	public void testDate() {
		advancedRequest.setStart(100000);
		//System.out.println(advancedRequest.getStart());
		advancedRequest.setStart(100);
		//System.out.println(advancedRequest.getStart());
		advancedRequest.setStart(10);
		//System.out.println(advancedRequest.getStart());
	}
	
	

}
