/**
 * 
 */
package com.acnl.oscartrafficgenerator.model;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 * @author vishal
 *
 */
public class LinkDeserializer extends JsonDeserializer<Link> {

	public static int i=0;
	@Override
	public Link deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode links = jp.getCodec().readTree(jp);
		return new Link(links.path("a").asText(),links.path("length").asInt(),links.path("type").asText(),links.path("z").asText());
	}

}
