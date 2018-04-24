/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pra.stripes;
import able.stripes.JSP;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.exception.DefaultExceptionHandler;
//import org.apache.log4j.Logger;

public class ExceptionHandler extends DefaultExceptionHandler {
//    private static final Logger logger = Logger.getLogger(ExceptionHandler.class);

    /**
     * Catch-All Exception, you should modify based on this
     * to handle different types of errors / exception.
     * 
     * You can also put description of the error into "mesej" request attribute
     * @param e
     * @param request
     * @param response
     * @return 
     */
    public Resolution catchAll(Exception e, HttpServletRequest request, HttpServletResponse response) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);        
        e.printStackTrace(pw);
//        e.printStackTrace();
//        logger.error(e.getMessage(), e);
//        Pengguna pengguna = (Pengguna) request.getSession().getAttribute(etanahActionBeanContext.KEY_USER);
//        if(pengguna == null) {
//            return new RedirectResolution(LoginActionBean.class);
//        }
        if (request.getAttribute("mesej") == null) {
            request.setAttribute("mesej", e.getMessage());
        }
        request.setAttribute("exception_msg", sw.toString());
        return new JSP("error.jsp").addParameter("popup", "true");
    }
    // methods can be of any name, with the following signature
    // public Resolution xyz(Throwable throwable, HttpServletRequest request, HttpServletResponse response)
}
