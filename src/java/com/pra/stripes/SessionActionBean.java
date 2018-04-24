/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pra.stripes;

import able.stripes.JSP;
import com.pra.service.session.LoginSession;
import com.pra.service.session.SessionManager;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wan.fairul
 */

@UrlBinding("/sessionManager")
public class SessionActionBean extends BaseActionBean {
    
    private static Logger LOG = LoggerFactory.getLogger(SessionActionBean.class);
    
    private List<LoginSession> sessionActive;
    private String sessionKill;
    
    private static final String VIEW = "common/session.jsp";
    
    @DontValidate
    @DefaultHandler
    public Resolution viewList(){
        SessionManager sm = SessionManager.getInstance();
        sessionActive = sm.getActiveSessions();
        return new JSP(VIEW);
    }
    
    @DontValidate
    public Resolution killSession(){
        SessionManager sm = SessionManager.getInstance();
        try{
            LoginSession ls = sm.getSession(sessionKill);
            if(StringUtils.isNotBlank(ls.getUserId())){
                LOG.debug("(killSession) Session "+ls.getUserId()+" to be kill.");
                sm.killSession(sessionKill);
                sm.removeSession(sessionKill);
                addSimpleMessage("Session "+ls.getUserId()+" Has Been Killed.");
            }else{
                addSimpleError("Session "+ls.getUserId()+" Not Found.");
            }
        }catch(Exception ex){
            addSimpleError("Error detected. Please refer System Admin for more information.");
            LOG.error("(killSession) error :"+ex.getMessage());
            ex.printStackTrace();
        }
        sessionActive = sm.getActiveSessions();
        return new JSP(VIEW);
    }

    public List<LoginSession> getSessionActive() {
        return sessionActive;
    }

    public void setsessionActive(List<LoginSession> sessionActive) {
        this.sessionActive = sessionActive;
    }

    public String getSessionKill() {
        return sessionKill;
    }

    public void setSessionKill(String sessionKill) {
        this.sessionKill = sessionKill;
    }
    
    
}
