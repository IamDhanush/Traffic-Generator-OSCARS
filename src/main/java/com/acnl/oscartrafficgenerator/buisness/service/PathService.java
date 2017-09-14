/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;

import com.acnl.oscartrafficgenerator.model.Link;
import com.acnl.oscartrafficgenerator.model.Path;
/**
 * @author vsundarrajan
 *
 */
public class PathService {

	@Autowired
	private List<Link> links;
	
	@Autowired
	private Supplier<Path> pathSupplier;
	
	private double lastPathFailureTime;
	
	private Queue<Path> pathFailureQueue=new PriorityQueue<>(new Comparator<Path>(){
		@Override
		public int compare (Path PathOne,Path PathTwo){
			return (int) (PathOne.getPathFailTime()-PathTwo.getPathFailTime());
		}
	});
	
	//true if the path is failed
	public boolean isPathFailed(List<String> pathLinks, Number endTime){
		boolean failFlag=false;
		lastPathFailureTime=Double.POSITIVE_INFINITY;
		for(Link link:links){
			if((pathLinks.contains(link.getA())||pathLinks.contains(link.getZ())) && (link.getCurrentFailure()<endTime.doubleValue())){
				if(lastPathFailureTime>link.getCurrentFailure()) lastPathFailureTime=link.getCurrentFailure();
				failFlag=true;				
			}
		}
		return failFlag;
	}
	
	public void updateRecentlyFailedPath(int connectionId, int pathNum){
		Path path=pathSupplier.get();
		path.setConnectionId(1+pathNum+connectionId);//+1 to make path 0 to 1
		path.setPathFailTime(lastPathFailureTime);
		pathFailureQueue.add(path);
	}
		
	//return path Ids that are failed recently
	public Collection<Integer> getFailedPathIds(Number startTime){
		List<Integer> pathId= new LinkedList<Integer>();
			for(;;){
				if(!pathFailureQueue.isEmpty())
				{
					if(pathFailureQueue.peek().getPathFailTime()<startTime.intValue()){
						System.out.println("Path failed at: "+pathFailureQueue.peek().getPathFailTime());
						pathId.add(pathFailureQueue.peek().getConnectionId());
						pathFailureQueue.remove();
					}
					else{
						break;
					}
				}
				else break;
			}
		return pathId;	
	}
	
	//next path failure for Test
	public void nextPathInQueue(){
		System.out.println(pathFailureQueue.peek().getPathFailTime());
		pathFailureQueue.remove();
	}
		
	//true if two paths overlap
	public boolean isPathOverlapped(List<String> path_1, List<String> path_2, Number startTime, Number endTime){
		List<Link> pathObj_1=new LinkedList<Link>();
		List<Link> pathObj_2=new LinkedList<Link>();
		for(Link link:links){
			if((path_1.contains(link.getA())||path_1.contains(link.getZ())) && (link.getCurrentFailure()<endTime.doubleValue())){
				pathObj_1.add(link);
			}
			else if((path_2.contains(link.getA())||path_2.contains(link.getZ())) && (link.getCurrentFailure()<endTime.doubleValue())){
				pathObj_2.add(link);
			}
		}
		Iterator<Link> pathOneIterator = pathObj_1.iterator();
		double failure,repair;
		while(pathOneIterator.hasNext()){
			Link linkPath_1=pathOneIterator.next();
			failure=linkPath_1.getCurrentFailure();
			repair=linkPath_1.getCurrentRepair();
			for(Link linkPath_2:pathObj_2){
				if((linkPath_2.getCurrentFailure()>failure && linkPath_2.getCurrentFailure()<repair)||(linkPath_2.getCurrentRepair()>failure && linkPath_2.getCurrentRepair()<repair)){
					return true;
				}
			}
		}
		return false;
	}
}
