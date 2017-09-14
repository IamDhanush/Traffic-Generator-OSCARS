/**
 * 
 */
package com.acnl.oscartrafficgenerator.model.response;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author vsundarrajan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseApi {

	private int connectionId;
	private String status;
	private String start;
	private String end;
	private ResponsePathsApi[] paths;
	
	public int getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(int connectionId) {
		this.connectionId = connectionId;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public ResponsePathsApi[] getPaths() {
		return paths;
	}
	
	public ResponsePathsApi getPath(int path){
		return paths[path];
	}

	public void setPaths(ResponsePathsApi[] paths) {
		this.paths = paths;
	}
}
