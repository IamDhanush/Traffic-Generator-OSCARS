/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.service;

import java.util.List;

/**
 * @author vishal
 *
 */
public interface DistributionServiceInterface  {
  
   public void setSeed(int seed);
   default public void setMean(double mean){ throw new UnsupportedOperationException("Invalid operation for this distribution");}
   default public void setMeanDeviation(double mean,double sd){ throw new UnsupportedOperationException("Invalid operation for this distribution");}
   default public void setRange(int lower,int uppar){ throw new UnsupportedOperationException("Invalid operation for this distribution");}
   public double getSample();
   public List<Double> getUniqueSamples(int n);
}

