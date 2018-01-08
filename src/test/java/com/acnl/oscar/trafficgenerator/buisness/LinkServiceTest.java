/**
 * 
 */
package com.acnl.oscar.trafficgenerator.buisness;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.acnl.oscartrafficgenerator.application.config.DistributionConfig;
import com.acnl.oscartrafficgenerator.application.config.PathConfig;
import com.acnl.oscartrafficgenerator.application.config.ResponseConfig;
import com.acnl.oscartrafficgenerator.application.config.TopoConfig;
import com.acnl.oscartrafficgenerator.buisness.service.DevicesService;
import com.acnl.oscartrafficgenerator.buisness.service.DistributionServiceInterface;
import com.acnl.oscartrafficgenerator.buisness.service.FileService;
import com.acnl.oscartrafficgenerator.buisness.service.LinksService;
import com.acnl.oscartrafficgenerator.buisness.service.PathService;
import com.acnl.oscartrafficgenerator.model.Link;

/**
 * @author vishal
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DistributionConfig.class,TopoConfig.class,PathConfig.class, ResponseConfig.class}, loader=AnnotationConfigContextLoader.class)
public class LinkServiceTest {

	@Autowired
	private DevicesService devicesService;
	
	@Autowired
	private LinksService linksService;
	
	@Autowired
	private List<Link> links;
	
	@Autowired
	private PathService pathService;
	
	@Autowired
	private FileService fileService;
	
	@Ignore
	@Test
	public void testDevicePojoBo() throws Exception {
		 System.out.println(devicesService.getDevice(5));
	     System.out.println(devicesService.getPort(5));
	     System.out.println(devicesService.getTotalDevice());
		 assertEquals("pppl-rt5",devicesService.getDevice(0));
	}
	
	@Test
	public void testLinks() throws Exception{
		for(Link link:links){
		   System.out.println(link);
		}	 
	}
	
	@Ignore
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
		   //linksService.printAllLinks();
		   System.out.println("***********************************"+i+"********************************");
		 }
	}
	
	@Ignore
	@Test
	public void testPathOverlap() throws Exception {
		linksService.generateLinkFailures(1000);
		//linksService.printAllLinks();
		linksService.getFailedLinks(2000);
		//linksService.printAllLinks();
		List<String> path1 = Stream.of("newy-cr5:4/1/1").collect(Collectors.toList());
		List<String> path2 = Stream.of("lond-cr5:4/1/1").collect(Collectors.toList());
		System.out.println(pathService.isPathFailed(path1,2600));
		System.out.println(pathService.isPathOverlapped(path1,path2,3500,3600));
	}
	
	@Ignore
	@Test
	public void testTotalFailures(){
		linksService.generateLinkFailures(101);
		int previousFailures=0,runningTime=10000;
			/*while(runningTime<100001)
			{
				linksService.getFailedLinks(runningTime);
				System.out.println(runningTime+"\t"+(linksService.totFailures(runningTime)-previousFailures));
				previousFailures=linksService.totFailures(runningTime);
				runningTime=runningTime+10000;
			}*/
		linksService.getFailedLinks(runningTime);
		System.out.println(linksService.totFailures(5000));
	}
	
	@Ignore
	@Test 
	public void getCurrentFailedLinks() throws Exception{
		FileWriter fileWriter=fileService.getOutputFile();
		linksService.generateLinkFailures(101);
		List<String> links=new LinkedList<String>();
		for(int i=800;i<100000;i=i+5){
			links=linksService.getFailedLinks(i);
			fileWriter.write("\n"+i+"\t"+links.size()/2);
		}
		fileWriter.close();
	}
	
	@Ignore
	@Test
	public void getFailureTime() throws Exception{
		FileWriter fileWriter=fileService.getOutputFile();
		linksService.generateLinkFailures(105);
		List<Integer> allFailures=new LinkedList<Integer>();
		for(Link link:links){
			int failureTime=(int)link.getCurrentFailure();
			while(failureTime<10000){
				//if(failureTime>20000)
					allFailures.add(failureTime);
				failureTime=failureTime+(int)link.getNextFailure();
			}
		}
		allFailures=allFailures.stream().sorted((o1,o2)->o1.compareTo(o2)).collect(Collectors.toList());
	    for(int i=0;i<allFailures.size();i++){
		 fileWriter.write("\n"+(i+1)+"\t"+allFailures.get(i)); 
	    }
	    fileWriter.close();
	}
}
