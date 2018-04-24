/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pra.service.session;
import java.io.Serializable;
import java.util.Date;

public interface LoginSession extends Serializable{

    public enum LOGIN_DIRECTIVE{
        NONE, 
        ADMIN_LOGOUT // admin request to kill the session
    }

    // PK
    String getSessionId();
    
    String getUserId();
    
    Date getDateCreated();
    
    String getUserIPAddr();
    
    String getComputerName();
    
    LOGIN_DIRECTIVE getDirective();

    void setDirective(LOGIN_DIRECTIVE d);
    
}