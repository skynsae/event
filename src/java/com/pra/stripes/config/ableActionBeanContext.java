package com.pra.stripes.config;

import com.google.inject.Inject;
import com.wideplay.warp.persist.Transactional;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import net.sourceforge.stripes.action.ActionBeanContext;

public class ableActionBeanContext extends ActionBeanContext {

    public static final String USER_ID_KEY = "_KEY_USER";
    public static final String COUNTER_ID_KEY = "_COUNTER_ID";
    public static final String TRH_LOGIN = "_TRH_LOGIN";
    private String counterID;


    protected com.google.inject.Provider<Session> sessionProvider;

    @Inject
    public ableActionBeanContext(com.google.inject.Provider<Session> sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

 

    public void setCounter(String counter) {
        HttpSession session = getRequest().getSession(true);
        session.setAttribute(COUNTER_ID_KEY, counter);
        counterID = counter;
    }

    public String getCounter() {
        if ("".equals(counterID) || counterID == null) {
            HttpSession session = getRequest().getSession();
            if (session != null) {
                counterID = (String) session.getAttribute(COUNTER_ID_KEY);
            }
        }
        return counterID;
    }


    
     public void setCookies(String key, String value) {
        Cookie cookie = new Cookie(key, value);
//        cookie.setMaxAge((3 * 60 * 60)); //3hours
        cookie.setMaxAge(-1); // not stored, live until browser exit
        getResponse().addCookie(cookie);
    }
    
     public String getCookies(String key) {
        if (getRequest().getCookies() != null) {
            for (Cookie cookie : getRequest().getCookies()) {
                if (cookie.getName().equals(key))
                    return cookie.getValue();
            }
        }
        return "";
    }

    
}
