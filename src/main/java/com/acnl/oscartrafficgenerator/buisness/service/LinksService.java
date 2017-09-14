/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.service;


import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;

import com.acnl.oscartrafficgenerator.model.Link;

/**
 * @author vishal
 *
 */
public class LinksService {
	
	private List<Link> linkSet;
	private Queue<Link> linkFailureQueue;
	private Supplier<DistributionServiceInterface> distributionSupplier;
	private double buriedMTBF, overseasMTBF, buriedKmPerAmplifier, overseasKmPerAmplifier,buriedMTTR,overseasMTTR,amplifierPlacements;
	
	@Autowired
	public LinksService(Supplier<DistributionServiceInterface> distributionSupplier, List<Link> linksModels, double buriedLinkFIT, double overseasLinkFIT, double buriedAmplifierFIT,double overseasAmplifierFIT, double amplifierPlacements, double buriedMTTR, double overseasMTTR){
		this.distributionSupplier=distributionSupplier;
		this.linkSet=linksModels;
		this.buriedMTBF= Math.pow(10,9)/buriedLinkFIT;
		this.overseasMTBF= Math.pow(10,9)/overseasLinkFIT;
		this.buriedKmPerAmplifier=buriedAmplifierFIT/buriedLinkFIT;
		this.overseasKmPerAmplifier=overseasAmplifierFIT/overseasLinkFIT;
		this.amplifierPlacements=amplifierPlacements;
		this.buriedMTTR=buriedMTTR;
		this.overseasMTTR=overseasMTTR;
	}
	
	//This will generate failures for each links
	public void generateLinkFailures(int seed){
		int distance;
		linkFailureQueue=new PriorityQueue<>(linkSet.size(),new Comparator<Link>(){
			@Override
			public int compare (Link link_one,Link link_two){
				return (int) (link_one.getCurrentFailure()-link_two.getCurrentFailure());
			}
		});
		for(Link link:linkSet){
			DistributionServiceInterface failureDistribution=distributionSupplier.get();
			DistributionServiceInterface repairDistribution=distributionSupplier.get();
			distance=link.getDistance();
			if(link.getType().equals("buried")){
				failureDistribution.setSeed(seed);
				failureDistribution.setMean(buriedMTBF/((double)distance+(((double)distance/amplifierPlacements)*buriedKmPerAmplifier)));
			    repairDistribution.setSeed(seed);
			    repairDistribution.setMean(buriedMTTR);
			}
		    else
		    {
		    	failureDistribution.setSeed(seed);
		    	failureDistribution.setMean(overseasMTBF/((double)distance+(((double)distance/amplifierPlacements)*overseasKmPerAmplifier)));
		    	repairDistribution.setSeed(seed);
		    	repairDistribution.setMean(overseasMTTR);
		    }
			link.setFailureDistribution(failureDistribution);
			link.setCurrentFailure(link.getNextFailure());
			link.setRepairDistribution(repairDistribution);
			link.setCurrentRepair(link.getCurrentFailure()+link.getNextRepair());
			linkFailureQueue.add(link);
			seed++;
		}
	}
	
	//get all unavailable ports at the start of connection 
	public List<String> getFailedLinks(double startTime){
		double failureTime;
		List<Link> temp_linkPojo=new LinkedList<Link>();
		List<String> blacklists=new LinkedList<String>();
		Link link;
		for(;;){
			link=linkFailureQueue.peek();
			failureTime=link.getCurrentFailure();
			if(failureTime>startTime){
				break;
			}
			else if(link.getCurrentRepair()>=startTime || failureTime==startTime){
				    temp_linkPojo.add(link);
				    blacklists.addAll(link.getLink());
					linkFailureQueue.remove();
			}
			else{
					link.setCurrentFailure(failureTime+link.getNextFailure());
					link.setCurrentRepair(link.getCurrentFailure()+link.getNextRepair());
					linkFailureQueue.remove();
					linkFailureQueue.add(link);
			}
		}
		linkFailureQueue.addAll(temp_linkPojo);	
		return blacklists;
	}
	
	public void printAllLinks(){
		Iterator<Link> itr=linkFailureQueue.iterator();
		while(itr.hasNext()){
		   Link linkPojo=itr.next();
		   linkPojo.printObject();
		   System.out.println("FailureTime: "+linkPojo.getCurrentFailure()+" RepairTime: "+linkPojo.getCurrentRepair());
		}
	}
	
	public int totFailures(){
		return linkSet.stream().mapToInt(link->link.getNumFailures()).sum();
	}
	
	public int getTotalLinks(){
		return linkSet.size();
	}

	public void to_string(){
		linkSet.forEach(linksPojo->linksPojo.printObject());	
	}
	
}
