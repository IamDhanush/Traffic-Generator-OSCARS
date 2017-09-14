/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.service.distributionserviceimpl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

import com.acnl.oscartrafficgenerator.buisness.service.DistributionServiceInterface;

/**
 * @author vishal
 *
 */

public class UniformlyDistribute implements DistributionServiceInterface {

	RandomGenerator rg;
    UniformIntegerDistribution ud;
	
	@Override
	public void setSeed(int seed){
		rg=new JDKRandomGenerator(seed);
	}
	
	public void setRange(int lower, int upper) {
	  	ud= new UniformIntegerDistribution(rg,lower,upper);
	}

	@Override
	public double getSample() {
		return ud.sample();
	}

	@Override 
    public List<Double> getUniqueSamples(int n){
     List<Double> list= new LinkedList<Double>();
     int i=0;
     double temp;
        while(i<n){
        	if (!list.contains(temp=ud.sample())){
        	  list.add(temp);
        	  i++;
        	}
        }
     return list; 	  
    }

}
