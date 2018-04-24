/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pra.stripes;

import com.google.inject.Singleton;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author wan.fairul
 */
@Singleton
public class Configuration implements Serializable{
    
    public static final String CONF_FILE =  "/able.properties";
	
    private static Properties props = new Properties();
    
    public Configuration() {
        try {
	        props.load(Configuration.class.getResourceAsStream(CONF_FILE));               
	    } catch (Exception e) {
	    	props = null;
	    	throw new RuntimeException("SEVERE: Could not load configuration properties at " + 
	    			CONF_FILE);
	    }
    } 
    
    
    public static String getProperty(String propName){
		if (props == null){
			throw new RuntimeException("SEVERE: Configuration was not successful loaded!");
		}
		
		return props.getProperty(propName);
	}
    

        public Map<String,String> getAllPropertiesWithValue() {            
            Map<String, String> map = new HashMap<String, String>();
            Set<String> s = props.stringPropertyNames();
            
            for (Iterator<String> it = s.iterator(); it.hasNext();) {
                String string = it.next();                
                String value = getProperty(string);               
                map.put(string, value);                
            }
            return map;
        }
    
}
