/**
 * 
 */
package com.acnl.oscartrafficgenerator.model.request;

import java.util.List;

/**
 * @author vsundarrajan
 *
 */
public class AdvancedRequestFlowApi {
	
	private String sourceDevice;
	private String sourcePorts[];
	private String sourceVlan;//InitialProperty
	private String destDevice;
	private String destPorts[];
	private String destVlan;//InitialProperty
	private String azMbps;
	private List<String> azRoute;
	private String zaMbps;
	private List<String> zaRoute;
	private String palindromic;//InitialProperty
	private String survivability;
	private String numPaths;//InitialProperty
	private List<String> blacklist;
	
	public String getSourceDevice() {
		return sourceDevice;
	}
	
	public void setSourceDevice(String sourceDevice) {
		this.sourceDevice = sourceDevice;
	}

	public String[] getSourcePorts() {
		return sourcePorts;
	}

	public void setSourcePorts(String[] sourcePorts) {
		this.sourcePorts = sourcePorts;
	}

	public String getSourceVlan() {
		return sourceVlan;
	}

	public void setSourceVlan(String sourceVlan) {
		this.sourceVlan = sourceVlan;
	}

	public String getDestDevice() {
		return destDevice;
	}

	public void setDestDevice(String destDevice) {
		this.destDevice = destDevice;
	}

	public String[] getDestPorts() {
		return destPorts;
	}

	public void setDestPorts(String destPorts[]) {
		this.destPorts = destPorts;
	}

	public String getDestVlan() {
		return destVlan;
	}

	public void setDestVlan(String destVlan) {
		this.destVlan = destVlan;
	}

	public String getAzMbps() {
		return azMbps;
	}

	public <T>void setAzMbps(T azMbps) {
		this.azMbps = String.valueOf(azMbps);
	}

	public List<String> getAzRoute() {
		return azRoute;
	}

	public void setAzRoute(List<String> azRoute) {
		this.azRoute = azRoute;
	}

	public String getZaMbps() {
		return zaMbps;
	}

	public <T>void setZaMbps(T zaMbps) {
		this.zaMbps = String.valueOf(zaMbps);
	}

	public List<String> getZaRoute() {
		return zaRoute;
	}

	public void setZaRoute(List<String> zaRoute) {
		this.zaRoute = zaRoute;
	}

	public String getPalindromic() {
		return palindromic;
	}

	public void setPalindromic(String palindromic) {
		this.palindromic = palindromic;
	}

	public String getSurvivability() {
		return survivability;
	}

	public void setSurvivability(String survivability) {
		this.survivability = survivability;
	}

	public String getNumPaths() {
		return numPaths;
	}

	public void setNumPaths(String numPaths) {
		this.numPaths = numPaths;
	}

	public List<String> getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(List<String> blacklist) {
		this.blacklist = blacklist;
	}
	
}
