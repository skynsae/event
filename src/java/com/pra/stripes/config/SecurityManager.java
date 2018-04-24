/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pra.stripes.config;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.security.controller.StripesSecurityManager;
import org.apache.log4j.Logger;

/**
 *
 * @author fikri
 */
public class SecurityManager implements StripesSecurityManager{
    
    private static final Logger LOG = Logger.getLogger(SecurityManager.class);
    private static boolean isDebug = LOG.isDebugEnabled();

    @Override
     public boolean isUserInRole(List<String> roles, ActionBeanContext context) {    
//        if (roles == null || roles.isEmpty()) {
//            return false;
//        }
//
////        String = (Pengguna) context.getRequest().getSession().getAttribute(ableActionBeanContext.USER_ID_KEY);
//        ableActionBeanContext ctx = (ableActionBeanContext)context;
//        Pengguna pengguna = ctx.getCurrentUser();
//         if (pengguna == null){
//             return false;
//         }
//
//        if (isDebug) {
//            LOG.debug("pengguna = " + pengguna.getIdPengguna());
//        }
//
//        String[] rolesList = (String[])roles.toArray();        
//
//        try{          
//            KodPeranan peranan = pengguna.getJenisPengguna();
//
//            if (ArrayUtils.contains(rolesList, peranan.getNama())) {
//                return true;
//            }
//            
//        }catch (Exception ex) {
//            LOG.error(ex);
//        } 
//        return false;
        return true;
    }

    @Override
    public boolean isUserInRole(List<String> list, HttpServletRequest hsr, HttpServletResponse hsr1) {
        return true;
    }
    
}
