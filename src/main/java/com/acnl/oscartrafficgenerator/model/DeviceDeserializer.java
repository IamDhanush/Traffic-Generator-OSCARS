/**
 * 
 */
package com.acnl.oscartrafficgenerator.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 * @author vishal
 *
 */

public class DeviceDeserializer extends JsonDeserializer<Device> {

	
	@Override
	public Device deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode devices = jp.getCodec().readTree(jp);
		JsonNode ifces = devices.path("ifces");
		List<String> port=new ArrayList<String>();
		List<Integer> bandwidth=new ArrayList<Integer>();
	    for(JsonNode ifce:ifces){
	    	JsonNode capabilities=ifce.path("capabilities");
	    	 for(JsonNode capability:capabilities){
	    		if(capability.asText().equals("ETHERNET"))
	    		 {
	    		  port.add(ifce.path("urn").asText());
	    		  bandwidth.add(ifce.path("reservableBw").asInt());
	    		 }
	    	 }
	    }
       return new Device(devices.get("urn").asText(),port,bandwidth);
	}
}
