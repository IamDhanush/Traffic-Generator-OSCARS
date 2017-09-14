/**
 * 
 */
package com.acnl.oscartrafficgenerator.model.response;

/**
 * @author vsundarrajan
 *
 */
public class ZaPath {
  
	private String origin;
	private String originType;
	private String target;
	private String targetType;
	
	public String getOrigin() {
		return origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public String getOriginType() {
		return originType;
	}
	
	public void setOriginType(String originType) {
		this.originType = originType;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getTargetType() {
		return targetType;
	}
	
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
}
