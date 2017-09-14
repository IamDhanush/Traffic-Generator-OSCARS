/**
 * 
 */
package com.acnl.oscartrafficgenerator.model.request;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author vsundarrajan
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class BandwidthRequestApi {

	private String startDate;
	private String endDate;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
