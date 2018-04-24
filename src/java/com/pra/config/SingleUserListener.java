/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pra.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import com.pra.stripes.config.ableActionBeanContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web application lifecycle listener.
 * @author cairo
 */
public class SingleUserListener implements HttpSessionListener {
    private Logger logger = LoggerFactory.getLogger(SingleUserListener.class.getName());

    public static final Map<String,Object> USER_LOGGED_IN = new ConcurrentHashMap<String,Object>();

    @Override
    public void sessionCreated(HttpSessionEvent hse) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
      
    }

   

}
