/**
 * 
 */
package com.acnl.oscar.trafficgenerator;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.acnl.oscartrafficgenerator.application.config.PathConfig;
import com.acnl.oscartrafficgenerator.application.config.TopoConfig;
import com.acnl.oscartrafficgenerator.buisness.service.DevicesService;
import com.acnl.oscartrafficgenerator.buisness.service.LinksService;
import com.acnl.oscartrafficgenerator.buisness.service.PathService;

/**
 * @author vishal
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TopoConfig.class,PathConfig.class}, loader=AnnotationConfigContextLoader.class)
public class LinkServiceTest {

	@Autowired
	private DevicesService devicesService;
	
	@Autowired
	private LinksService linksService;
	
	@Autowired
	private PathService pathService;
	
	@Test
	public void testDevicePojoBo() throws Exception {
		
	     System.out.println(devicesService.getDevice(5));
	     System.out.println(devicesService.getPort(5));
	     System.out.println(devicesService.getPort(5));
	     System.out.println(devicesService.getPort(5));
	     System.out.println(devicesService.getPort(5));
	     System.out.println(devicesService.getTotalDevice());
		 assertEquals("pppl-rt5",devicesService.getDevice(0));
	}
	
	@Test
	public void testBlackLists() throws Exception {
		 linksService.generateLinkFailures(1000);
		 linksService.printAllLinks();
		 List<String> blacklists=new LinkedList<String>();
		 for(int i=1000;i<1000;i+=70)
		 {
	         blacklists=linksService.getFailedLinks(i);
		     if(!blacklists.isEmpty()){
			   blacklists.forEach(blacklist-> System.out.println("************************"+blacklist+"****************")); 
		     }
		     else
		    	 System.out.println("Blacklist is empty");
		   linksService.printAllLinks();
		   System.out.println("***********************************"+i+"********************************");
		 }
	}
	
	@Test
	public void testPathOverlap() throws Exception {
		linksService.generateLinkFailures(1000);
		linksService.printAllLinks();
		linksService.getFailedLinks(2000);
		linksService.printAllLinks();
		List<String> path1 = Stream.of("newy-cr5:4/1/1").collect(Collectors.toList());
		List<String> path2 = Stream.of("lond-cr5:4/1/1").collect(Collectors.toList());
		System.out.println(pathService.isPathFailed(path1,2600));
		System.out.println(pathService.isPathOverlapped(path1,path2,3500,3600));
	}
	
	@Test
	public void testTotalFailures(){
		linksService.generateLinkFailures(1000);
		linksService.getFailedLinks(1000);
		System.out.println("Total Failures: "+linksService.totFailures());
	}

}
