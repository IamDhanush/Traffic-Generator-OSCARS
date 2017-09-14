/**
 * 
 */
package com.acnl.oscartrafficgenerator.model.response;

/**
 * @author vsundarrajan
 *
 */
public class ResponsePathsApi {

	private AzPath[] azPath;
	private ZaPath[] zaPath;
	
	public AzPath[] getAzPath() {
		return azPath;
	}
	
	public void setAzPath(AzPath[] azPath) {
		this.azPath = azPath;
	}
	
	public ZaPath[] getZaPath() {
		return zaPath;
	}
	
	public void setZaPath(ZaPath[] zaPath) {
		this.zaPath = zaPath;
	}
	
	
}
