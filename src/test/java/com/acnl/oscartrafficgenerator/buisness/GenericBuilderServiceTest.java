/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.acnl.oscartrafficgenerator.application.config.RequestConfig;
import com.acnl.oscartrafficgenerator.buisness.service.GenericBuilderService;
import com.acnl.oscartrafficgenerator.model.request.AdvancedRequestApi;
import com.acnl.oscartrafficgenerator.model.request.AdvancedRequestFlowApi;

/**
 * @author vsundarrajan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RequestConfig.class}, loader=AnnotationConfigContextLoader.class)
public class GenericBuilderServiceTest {

	@Autowired
	private GenericBuilderService<AdvancedRequestFlowApi> flowBuilder;
	
	@Test
	public void testForNullList(){
		List<String> pathLinks0 = new LinkedList<String>();
		/*if(pathLinks0.isEmpty()){
			System.out.println("Empty");
		}
		else{
			System.out.println("Not Empty");
		}*/
		
		pathLinks0.stream().filter(Objects::nonNull).forEach(item->System.out.println(item));
	}

}
