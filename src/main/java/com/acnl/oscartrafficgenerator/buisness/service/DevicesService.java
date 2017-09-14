/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.service;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;

import com.acnl.oscartrafficgenerator.model.Device;

/**
 * @author vishal
 *
 */
public class DevicesService {

	private Device[] devicesModels;
	
	@Autowired
	public DevicesService (Supplier<DistributionServiceInterface> distributionSupplier,Device[] devicesPojos){

		this.devicesModels=devicesPojos;
		for(Device dp:devicesPojos){
			DistributionServiceInterface distribution=distributionSupplier.get();
			distribution.setSeed(1000);
			distribution.setRange(0, dp.getTotalPortsForThisDevice()-1);
			dp.setDeviceDistribution(distribution);
		}
	}
	
	public String getDevice(int i){
		return devicesModels[i].getDevice();
	}
	
	public String getPort(int i){
		return devicesModels[i].getRandomPortFromThisDevice();
	}
	
	public int getTotalDevice(){
		return devicesModels.length;
	}
}
