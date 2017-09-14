/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.acnl.oscartrafficgenerator.model.response.AzPath;
import com.acnl.oscartrafficgenerator.model.response.ResponseApi;
import com.acnl.oscartrafficgenerator.model.response.ResponsePathsApi;
import com.acnl.oscartrafficgenerator.model.response.ZaPath;

/**
 * @author vsundarrajan
 *
 */
public class ResponseService {

	private ResponsePathsApi[] paths;
	List<String> portList;
	List<List<String>> listOfPortLists = new ArrayList<List<String>>();

	public void setResponse(ResponseApi response) {
		paths=response.getPaths();	
	}
    
    public void getHopCounts(String pathDirection, int pathNumber){
    	portList=new LinkedList<String>();
    	if(pathDirection.equals("AZ")){
    		AzPath[] azPath=paths[pathNumber].getAzPath();
    		for(int j=0;j<azPath.length;j++){
    			if(j==0 || j==azPath.length-2) portList.add(azPath[j].getTarget());
    			if(azPath[j].getOriginType().equals("PORT") && azPath[j].getTargetType().equals("PORT")){
    				portList.add(azPath[j].getOrigin()); 
					portList.add(azPath[j].getTarget());
    			}
    		}
    	}
    	else{
    		ZaPath[] zaPath=paths[pathNumber].getZaPath();
    		for(int j=0;j<zaPath.length;j++){
    			if(zaPath[j].getOriginType().equals("PORT") && zaPath[j].getTargetType().equals("PORT")){
    				portList.add(zaPath[j].getOrigin()); 
					portList.add(zaPath[j].getTarget());
    			}
    		}
    	}
    	listOfPortLists.add(portList);
    }	
    
    public void sortPaths() throws Exception{
    	sortByPathSize(listOfPortLists);
    }
    
    private static <T> List<List<T>> sortByPathSize(List<List<T>> listOfPortLists) {
        Collections.sort(listOfPortLists, new Comparator<List<T>>() {
            @Override
            public int compare(List<T> o1, List<T> o2) {
                return Integer.compare(o1.size(), o2.size());
            }
        });
        return listOfPortLists;
    }
    
    public int getHopCounts(int path){
    	return (listOfPortLists.get(path).size()-2)/2; //minus 2 to ignore source and destination URN
    }
  
    public List<String> getPathLinks(int path){
    	return listOfPortLists.get(path);
    }
    
    public void clearResponse(){
    	listOfPortLists.clear();
    }
}
