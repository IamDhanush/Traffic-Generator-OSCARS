/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.service.distributionserviceimpl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

import com.acnl.oscartrafficgenerator.buisness.service.DistributionServiceInterface;
/**
 * @author vishal
 *
 */

public class ExponentiallyDistribute implements DistributionServiceInterface {
	
	RandomGenerator rg;
	ExponentialDistribution ed;
	
	@Override
	public void setSeed(int seed){
		rg=new JDKRandomGenerator(seed);
	}
		
	@Override
	public void setMean (double mean){
	   	ed= new ExponentialDistribution(rg,mean);
	}
	
	@Override 
	public double getSample(){
		return ed.sample();
	}
	
	@Override 
    public List<Double> getUniqueSamples(int n){
     List<Double> list= new LinkedList<Double>();
     int i=0;
     double temp;
        while(i<n){
        	if (!list.contains(temp=ed.sample())){
        	  list.add(temp);
        	  i++;
        	}
        }
     return list; 	  
    }
    
    

}
