package com.pra.service.session;

import com.pra.stripes.Configuration;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class SessionManager {

    private static SessionManager sessionManager;

    protected static final Logger LOG = Logger.getLogger(SessionManager.class);

    public static String PROP_SESSION_MANAGER = "uam.sessionManager.class";

    static {
        Configuration conf = new Configuration();
        String prop = conf.getProperty(PROP_SESSION_MANAGER);
        LOG.info("SessionManager implementor is " + prop);
        if (prop == null || prop.length() == 0) {
            sessionManager = new DefaultSessionManager();
        } else {
            try {
                sessionManager = (SessionManager) Class.forName(prop).newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                sessionManager = new DefaultSessionManager();
            } catch (InstantiationException e) {
                e.printStackTrace();
                sessionManager = new DefaultSessionManager();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                sessionManager = new DefaultSessionManager();
            }
        }
        LOG.info("Using " + sessionManager.getClass().getName() + " for SessionManager");
    }

    public static SessionManager getInstance() {
        return sessionManager;
    }

    public static String getConfState() {
        Configuration conf = new Configuration();
        return conf.getProperty("state");
    }

    public abstract void clear();

    public abstract LoginSession addSession(String sessionId, String userId, Date dateCreated,
            String IPAddr, String computerName);

    /**
     * Request to kill the session.
     *
     * @param userId
     */
    public abstract void killSession(String sessionId);

    public abstract void removeSession(String sessionId);

    public abstract LoginSession getSession(String sessionId);

    public abstract LoginSession getActiveSessionByUserId(String userId);

    public abstract List<LoginSession> getActiveSessions();

    public abstract List<LoginSession> getSessions();

}
