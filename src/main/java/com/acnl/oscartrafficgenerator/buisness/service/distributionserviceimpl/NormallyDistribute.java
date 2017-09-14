/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.service.distributionserviceimpl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

import com.acnl.oscartrafficgenerator.buisness.service.DistributionServiceInterface;

/**
 * @author vishal
 *
 */
public class NormallyDistribute implements DistributionServiceInterface {

	RandomGenerator rg;
    NormalDistribution nd;
	
	@Override
	public void setSeed(int seed){
		rg=new JDKRandomGenerator(seed);
	}

	@Override
	public void setMeanDeviation (double mean,double sd){
	   	nd= new NormalDistribution(rg,mean,sd);
	}
	
	@Override
	public double getSample() {
		return nd.sample();
	}

	@Override 
    public List<Double> getUniqueSamples(int n){
     List<Double> list= new LinkedList<Double>();
     int i=0;
     double temp;
        while(i<n){
        	if (!list.contains(temp=nd.sample())){
        	  list.add(temp);
        	  i++;
        	}
        }
     return list; 	  
    }


}
