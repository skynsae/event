package com.pra.service.session;


import com.pra.service.session.LoginSession.LOGIN_DIRECTIVE;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Using memory (RAM) to store the sessions. WARNING: Not suitable for load-balanced app server!
 * @author wan.fairul
 *
 */
public class DefaultSessionManager extends SessionManager {

    protected static Map<String, LoginSession> listSessions = new HashMap<String, LoginSession>(100);
    
        
    @Override
    public LoginSession addSession(String sessionId, String userId, Date dateCreated,  String IPAddr, String computerName) {
        LoginSessionImpl l = new LoginSessionImpl();
        l.sessionId = sessionId;
        l.userId = userId;
        l.dateCreated = dateCreated;
        l.IPAddr = IPAddr;
        l.computerName = computerName;
        listSessions.put(sessionId, l);
        
        return l;
    }
    
    @Override
    public void killSession(String sessionId){
        LoginSession l = listSessions.get(sessionId);
        if (l != null){
            l.setDirective(LOGIN_DIRECTIVE.ADMIN_LOGOUT);
        }
    }

    @Override
    public void removeSession(String sessionId) {
        listSessions.remove(sessionId);
    }

    @Override
    public LoginSession getSession(String sessionId) {
        return listSessions.get(sessionId);
    }
    
    @Override
    public LoginSession getActiveSessionByUserId(String userId){
        for (Entry<String, LoginSession> e : listSessions.entrySet()){
            LoginSession s = e.getValue();
            if (s!= null && s.getDirective() == LOGIN_DIRECTIVE.NONE && userId.equalsIgnoreCase(s.getUserId())){
                return e.getValue();
            }
        }
        
        return null;
    }

    @Override
    public List<LoginSession> getSessions() {
        ArrayList l = new ArrayList<LoginSession>(listSessions.size());
        for (String sessionId : listSessions.keySet()){
            l.add(listSessions.get(sessionId));
        }
        
        Collections.sort(l, new Comparator<LoginSession>(){
            @Override
            public int compare(LoginSession a, LoginSession b) {
                return a.getUserId().compareTo(b.getUserId());
            }
        });

        return l;
    }
    
    @Override
    public List<LoginSession> getActiveSessions() {
        ArrayList l = new ArrayList<LoginSession>(listSessions.size());
        for (String sessionId : listSessions.keySet()){
            LoginSession s = listSessions.get(sessionId);
            if (s != null && s.getDirective() != LOGIN_DIRECTIVE.ADMIN_LOGOUT){
                LOG.warn("found null session entry for " + sessionId);
                l.add(s);
            }
        }
        
        Collections.sort(l, new Comparator<LoginSession>(){
            @Override
            public int compare(LoginSession a, LoginSession b) {
                return a.getUserId().compareTo(b.getUserId());
            }
        });

        return l;
    }

    @Override
    public void clear(){
        listSessions.clear();
    }

}
