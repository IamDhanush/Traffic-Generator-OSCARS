/**
 * 
 */
package com.acnl.oscartrafficgenerator.buisness.service;


import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * @author vsundarrajan
 *
 */
public class GenericBuilderService<T> {
	
	 private T instance;
	 private boolean ifCond=true;
	 
     public GenericBuilderService(Class<T> thisClass){
    	 try{
    		 instance=thisClass.newInstance();
    	 }
    	 catch(InstantiationException | IllegalAccessException e){
    		 e.printStackTrace();
    	 }
     }
     
     public GenericBuilderService(T instance){
    	 this.instance=instance;
     }
     
     public static <T> GenericBuilderService<T> build(Class<T> thisClass){
    	 return new GenericBuilderService<>(thisClass);
     }
     
     public GenericBuilderService<T> with(Consumer<T> setter){
    	 if(ifCond)
    		 setter.accept(instance);
    	 return this;
     }
     
     public GenericBuilderService<T> If(BooleanSupplier condition){
    	 this.ifCond=condition.getAsBoolean();
    	 return this;
     }
     
     public GenericBuilderService<T> endIf(BooleanSupplier condition){
    	 this.ifCond=true;
    	 return this;
     } 
     
     public T get(){
    	 return instance;
     }
}
