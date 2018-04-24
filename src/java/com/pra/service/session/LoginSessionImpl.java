/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pra.service.session;

import java.util.Date;

public class LoginSessionImpl implements LoginSession {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    String userId;
    
    Date dateCreated;
    
    String sessionId;
    
    String IPAddr;
    
    String computerName;
    
    LOGIN_DIRECTIVE directive = LOGIN_DIRECTIVE.NONE;
    
    @Override
    public String getUserId() {
        return userId;
    }
    
    @Override
    public Date getDateCreated(){
        return dateCreated;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }
    
    @Override
    public String getUserIPAddr() {
        return IPAddr;
    }

    @Override
    public String getComputerName() {
        return computerName;
    }
    
    @Override
    public void setDirective(LOGIN_DIRECTIVE d){
        directive = d;
    }

    @Override
    public LOGIN_DIRECTIVE getDirective() {
        return directive;
    }

}

