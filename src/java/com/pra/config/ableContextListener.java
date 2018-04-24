package com.pra.config;

import able.guice.AbleContextListener;
import able.guice.AbleModule;
import com.google.inject.Injector;
import com.pra.stripes.config.ableActionBeanContext;
import javax.servlet.ServletContextEvent;
import net.sourceforge.stripes.action.ActionBeanContext;



public class ableContextListener extends AbleContextListener {        

     @Override
    public void contextInitialized(ServletContextEvent arg0) {
        super.contextInitialized(arg0);       
    }
    
    protected void afterGuiceInit(Injector injector) {        

    }

    protected AbleModule getAbleModule() {
        return new ableModule();
    }

    protected Class<? extends ActionBeanContext> getActionBeanContextType() {
        return ableActionBeanContext.class;
    }  
    
     public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
