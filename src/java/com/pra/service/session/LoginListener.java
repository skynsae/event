package com.pra.service.session;


import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;

public class LoginListener implements HttpSessionListener {
    
    private static Logger LOG = Logger.getLogger(LoginListener.class);   
    
    
    @Override
    public void sessionCreated(HttpSessionEvent e) {
        LOG.debug("session created with ID=" + e.getSession().getId());
//        e.getSession().setMaxInactiveInterval(10);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent e) {
        HttpSession s = e.getSession();
       
    }

}
