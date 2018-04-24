/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pra.stripes.util;

import com.google.inject.Inject;
import com.pra.manager.UserManager;
import com.pra.stripes.BaseActionBean;
import java.util.List;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.hibernate.Session;
import org.json.JSONArray;

/**
 *
 * @author wan.fairul
 */
@UrlBinding("/auto_complete")
public class AjaxUtil extends BaseActionBean {

    @Inject
    private UserManager um;
    @Inject
    protected com.google.inject.Provider<Session> sessionProvider;

    public Resolution viewCity() {
        String code = getContext().getRequest().getParameter("code");
        

        JSONArray array = new JSONArray("");

        return new StreamingResolution("application/json", array.toString());
    }

    public UserManager getUm() {
        return um;
    }

    public void setUm(UserManager um) {
        this.um = um;
    }
    
    

}
