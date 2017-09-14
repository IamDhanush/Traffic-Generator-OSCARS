/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.service;

import java.io.FileWriter;


/**
 * @author vsundarrajan
 *
 */
public class FileService {

	private String output,bandwidth;
	
	public FileService(String output,String bandwidth){
		this.output=output;
		this.bandwidth=bandwidth;
	}
	
	public FileWriter getOutputFile() throws Exception{
		return new FileWriter(output,true);
	}	
	
	public FileWriter getBandwidthFile() throws Exception{
		return new FileWriter(bandwidth,true);
	}
}
