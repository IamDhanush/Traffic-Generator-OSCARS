/**
 * 
 */
package com.acnl.oscartrafficgenerator.model.request;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author vsundarrajan
 *
 */
@JsonIgnoreProperties(value={"dateFormatter","startDateTime","currentDateTime"})
public class AdvancedRequestApi {

	DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("MM dd yyyy HH:mm");
	LocalDateTime startDateTime,currentDateTime;
	private String start;
	private String end;
	private String connectionId;
	private String description;//InitialProperty
	private String username;//InitialProperty
    private String minNumFlows;//InitialProperty
    private String maxNumFlows;//InitialProperty
    private AdvancedRequestFlowApi[] flows;
    
    public AdvancedRequestApi(String startDate) {
    	startDateTime = LocalDateTime.of(2019, Month.APRIL, 8, 10, 20);
    }
    
	public String getStart() {
		return start;
	}
	
	public void setStart(Number start){
		this.start=startDateTime.plusHours(start.longValue()).format(dateFormatter);
	}
	
	public String getEnd() {
		return end;
	}

	public void setEnd(Number end) {
		this.end=startDateTime.plusHours(end.longValue()).format(dateFormatter);
	}

    public String getConnectionId() {
		return connectionId;
	}

	public <T>void setConnectionId(T connectionId) {
		this.connectionId = String.valueOf(connectionId);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMinNumFlows() {
		return minNumFlows;
	}

	public <T>void setMinNumFlows(T minNumFlows) {
		this.minNumFlows = String.valueOf(minNumFlows);
	}

	public String getMaxNumFlows() {
		return maxNumFlows;
	}

	public <T>void setMaxNumFlows(T maxNumFlows) {
		this.maxNumFlows = String.valueOf(maxNumFlows);
	}

	public AdvancedRequestFlowApi[] getFlows() {
		return flows;
	}

	public void setFlows(AdvancedRequestFlowApi[] flows) {
		this.flows = flows;
	}
	
}
