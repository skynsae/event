/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pra.stripes.filter;

import com.google.inject.Inject;
import com.pra.stripes.Configuration;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wan.fairul
 */
public class SecurityFilter implements Filter {

    public static final String DEFAULT_LOGIN_PATH = "/main";

    @Inject
    protected com.google.inject.Provider<Session> sessionProvider;

    private static String loginPath;

    private static String publicPath;

    private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);
    private static boolean info = LOG.isInfoEnabled();
    private static boolean debug = LOG.isDebugEnabled();

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain fc) throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        String reqPath = r.getRequestURI();

        // ignore if going to login page
        if (loginPath.equals(reqPath) 
                || reqPath.startsWith(publicPath)
                || reqPath.equals("/server_status")) {
            fc.doFilter(req, res);
            return;
        }

        if (debug) {
            LOG.debug("REQ:" + reqPath + " from " + r.getRemoteAddr() + " begin");
        }

        // check if user is authenticated  or not
        HttpSession session = r.getSession();
//        Pengguna p = (Pengguna) session.getAttribute("_KEY_USER");
//        if (p == null) {
//            // redirect to login page
//            LOG.info("Session Terminated / No Session Access from " + r.getRemoteAddr());
//            HttpServletResponse rs = (HttpServletResponse) res;
//            rs.sendRedirect(loginPath);
//			// TODO: save form's parameters in session
//
//            return;
//        }

        com.pra.service.session.SessionManager sm = com.pra.service.session.SessionManager.getInstance();
       com.pra.service.session.LoginSession ls = sm.getSession(session.getId());
        if (ls == null) {            
            HttpServletResponse rs = (HttpServletResponse) res;            
            rs.sendRedirect(loginPath);
            return;
        }

        fc.doFilter(req, res);

        if (debug) {
            LOG.debug("REQ:" + r.getRequestURI() + " from " + r.getRemoteAddr() + " ended");
        }
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
        String ctxPath = fc.getServletContext().getContextPath();

        Configuration conf = new Configuration();
        loginPath = conf.getProperty("security.login.uri");
        if (loginPath == null || loginPath.length() == 0) {
            loginPath = DEFAULT_LOGIN_PATH;
        }
        if (!loginPath.startsWith("/")) {
            loginPath = "/" + loginPath;
        }
        loginPath = ctxPath + loginPath;

        if (info) {
            LOG.info("Login path = " + loginPath);
        }

        publicPath = ctxPath + "/js";
    }

}
