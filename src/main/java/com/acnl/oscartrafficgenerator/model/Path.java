/**
 * 
 */
package com.acnl.oscartrafficgenerator.model;
/**
 * @author vsundarrajan
 *
 */
public class Path {
	
	private int connectionId;
	private double pathFailTime;
	/**
	 * @return the connectionId
	 */
	public int getConnectionId() {
		return connectionId;
	}
	/**
	 * @param connectionId the connectionId to set
	 */
	public void setConnectionId(int connectionId) {
		this.connectionId = connectionId;
	}
	/**
	 * @return the pathFailTime
	 */
	public double getPathFailTime() {
		return pathFailTime;
	}
	/**
	 * @param pathFailTime the pathFailTime to set
	 */
	public void setPathFailTime(double pathFailTime) {
		this.pathFailTime = pathFailTime;
	}
	
	@Override
	public String toString(){
		return "Connection Id: "+String.valueOf(connectionId)+", Path Failed Time: "+String.valueOf(pathFailTime);
	}
}
