/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.acnl.oscartrafficgenerator.model.request.AdvancedRequestApi;
import com.acnl.oscartrafficgenerator.model.response.ResponseApi;

/**
 * @author vsundarrajan
 *
 */
public class RestController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AdvancedRequestApi advancedRequestApi;
	
	@Autowired
	private ResponseApi responseApi;
	
	int id=0;
	
	public void abortRequest(int id){
		for(;;){
			try{
				restTemplate.getForEntity("https://localhost:8000/resv_simple/abort/"+String.valueOf(id),ResponseApi.class);
		    	System.out.println("Aborting request: "+id);
				break;
			}
			catch (Exception e){
				e.printStackTrace();
				continue;
			}
		}
	}
	
	public ResponseApi sendRequest(String type){
		for(;;){
			try{
				ResponseEntity<ResponseApi> responseEntity=restTemplate.postForEntity("https://localhost:8000/resv_simple/connection/"+type,advancedRequestApi,ResponseApi.class);
				responseApi=responseEntity.getBody();
				int responseCode=responseEntity.getStatusCode().value();
				if((responseCode>199 && responseCode<209)||responseCode==226){//response is successfull
					if(responseApi.getStatus().equals("FAILED_TO_RETRIEVE")){
						System.out.println("Failed-To-Retrieve,Trying again");
					}
					break;
				}
				else{//something wrong with the response
					System.out.println("Reponse Code: "+responseCode);
					continue;
				}
			}
			catch (org.springframework.web.client.ResourceAccessException e){
				System.out.println("Server cannot be reached: " +e);
				continue;
			}
			catch (Exception e){
				System.out.println("Unexplored Exception: "+e);
				continue;
			}
		}
		return responseApi;
	}
}
