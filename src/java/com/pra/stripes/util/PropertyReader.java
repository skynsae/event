package com.pra.stripes.util;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class PropertyReader {

    private Properties properties;
    private Logger log = Logger.getLogger(this.getClass().getSimpleName());
    private static String myprop;
    public static String myLocale;
    
    public PropertyReader() {
        properties = new Properties();
        System.out.println("PropertyReader>>myLocale>>"+myLocale);

        if ("en".equalsIgnoreCase(myLocale))
            myprop = "StripesResources";
        else if ("ms".equalsIgnoreCase(myLocale))
            myprop = "StripesResources_fr";
        else
            myprop = "StripesResources_ms";

        try {
            System.out.println("[Reading StripesResources.properties]");
            ResourceBundle rb = ResourceBundle.getBundle(myprop);
            properties.putAll(copy(rb));
        } catch (MissingResourceException ex) {
            System.out.println("Missing StripesResources.properties, using default instead");
        }
    }
    
    private static Properties copy(ResourceBundle rb) {
        Properties _properties = new Properties();
        if(rb != null) {
            for (Enumeration<String> en = rb.getKeys();en.hasMoreElements();) {
                String key = en.nextElement();
                String value = rb.getString(key);
                _properties.put(key, value);
            }
        }
        return _properties;
    }

    public String getAnyProperty(String input) {
        return properties.getProperty(input);
    }

    public static String getProperty(String input) {
        PropertyReader prop = new PropertyReader();
        //PropertyReader.myprop = myprop;
        return prop.getAnyProperty(input);
    }

    public static void main(String[] args) {
        PropertyReader prop = new PropertyReader();
        PropertyReader.myLocale = "ms";
        //PropertyReader.myprop = "StripesResources";// =  "StripesResources_ms_MY";//"StripesResources";
        String s = getProperty("invalidId");
        System.out.println("s->"+s);
    }
}


