/**
 * 
 */
package com.acnl.oscar.trafficgenerator;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
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
import com.acnl.oscartrafficgenerator.buisness.service.LinksService;
import com.acnl.oscartrafficgenerator.buisness.service.PathService;
import com.acnl.oscartrafficgenerator.model.Path;

/**
 * @author vsundarrajan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={PathConfig.class,TopoConfig.class}, loader=AnnotationConfigContextLoader.class)
public class PathServiceTest {

	@Autowired
	private Supplier<Path> pathSupplier;
	
	@Autowired
	private LinksService linkService;
	
	@Autowired
	private PathService pathService;
	/**
	 * Test method for {@link com.acnl.oscartrafficgenerator.buisness.service.PathService#isPathFailed(java.util.List, java.lang.Number, java.lang.Number)}.
	 */
	@Test
	public void testIsPathFailed() {
		List<Integer> connIds=new LinkedList<Integer>();
		linkService.generateLinkFailures(2000);
		//linkService.printAllLinks();
		List<String> pathLinks0 = Stream.of("aofa-cr5:10/1/12","newy-cr5:4/1/1","aofa-cr5:5/2/1","pnwg-cr5:10/1/10").collect(Collectors.toList());
		pathService.isPathFailed(pathLinks0, 700);
		pathService.updateRecentlyFailedPath(1, 1);
		List<String> pathLinks1 = Stream.of("pnwg-cr5:10/1/6","aofa-cr5:10/1/12","newy-cr5:4/1/1","aofa-cr5:5/2/1","pnwg-cr5:10/1/10").collect(Collectors.toList());
		pathService.isPathFailed(pathLinks1, 700);
		pathService.updateRecentlyFailedPath(1, 2);
		List<String> pathLinks2 = Stream.of("wash-cr5:5/1/1","pnwg-cr5:10/1/6","aofa-cr5:10/1/12","newy-cr5:4/1/1","aofa-cr5:5/2/1","pnwg-cr5:10/1/10").collect(Collectors.toList());
		pathService.isPathFailed(pathLinks2, 900);
		pathService.updateRecentlyFailedPath(1, 3);
		System.out.println(pathService.getFailedPathIds(600));
		//pathService.nextPathInQueue();
		//pathService.nextPathInQueue();
		//pathService.nextPathInQueue();
	}
	
	
	
}
