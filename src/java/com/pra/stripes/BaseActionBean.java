package com.pra.stripes;

import able.stripes.AbleActionBean;
import com.pra.stripes.config.ableActionBeanContext;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javax.servlet.jsp.jstl.core.Config;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.util.Base64;
import net.sourceforge.stripes.validation.SimpleError;


public abstract class BaseActionBean extends AbleActionBean {

    //Default paging implementation - use in all pages
    Integer __pg_max_records = 10;
    Integer __pg_start = 0;
    Integer __pg_total_records = 0;    

    @Override
    public ableActionBeanContext getContext() {
        return (ableActionBeanContext) super.getContext();
    }   

 
    
    public String getCounter() {
        if (getContext() != null) {
            if (!"".equals(getContext().getCounter()) && getContext().getCounter()!= null) {
                 return getContext().getCounter();
            }
        }
        return "";
    }
    
    public synchronized String encrypt(String plainText) {
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            String msg = "SHA not found, encryption cannot continue, no recovery possible";
            throw new RuntimeException(msg, e);
        }

        try {
            md.update(plainText.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            String msg = "UTF-8 encoding not found, no recovery possible";
            throw new RuntimeException(msg, e);
        }

        byte[] raw = md.digest();

        //String pwd = new BASE64Encoder().encode(raw);
        String pwd = Base64.encodeBytes(raw);
        return pwd.length() > 50 ? pwd.substring(0, 50) : pwd;
    }
    
    public String generatePasswordAuto() {
        String dCase = "abcdefghijklmnopqrstuvwxyz";
        String uCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        String sChar = "!@#$^*";
        String intChar = "23456789";
        Random r = new Random();
        String pass = "";
        
        while (pass.length () != 10){
            int rPick = r.nextInt(3);
            if (rPick == 0){
                int spot = r.nextInt(25);
                pass += dCase.charAt(spot);
            } else if (rPick == 1) {
                int spot = r.nextInt (25);
                pass += uCase.charAt(spot);
            } 
//            else if (rPick == 2) {
//                int spot = r.nextInt (5);
//                pass += sChar.charAt(spot);
//            } 
            else if (rPick == 2){
                int spot = r.nextInt (7);
                pass += intChar.charAt (spot);
            }
        }
        
        return pass;

    }
    
     public ResourceBundle getResourceBundle() {
        String currLang = getLang();
        ResourceBundle resourceBundle = null;
            try {
                String sFilename = "StripesResources_ms";
                if(("en").equalsIgnoreCase(currLang)){
                    sFilename = "StripesResources";
                }
                resourceBundle = ResourceBundle.getBundle(sFilename);
            } catch (Exception e) {
            }
     return resourceBundle;
    }
     
      public String getLang() {
        if (context.getRequest().getParameter("__lang") != null) {
            return context.getRequest().getParameter("__lang");
        }
        return getContext().getCookies("lang");
    }
      
      public void setLang(Integer lang) {
        if (lang == null || lang == 0) {
            setLang("ms");
        } else {
            setLang("en");
    	}
    }

    public void setLang(String lang) {
        if ("ms".equals(lang)) {
            //we set to france because of problem with WAS in zLinux, 
            //it give problem with character set if we use ms_MY
            Config.set(getContext().getRequest().getSession(), Config.FMT_LOCALE, Locale.FRANCE);
        } else {
            lang = "en";
            Config.set(getContext().getRequest().getSession(), Config.FMT_LOCALE, Locale.ENGLISH);
        }
        getContext().setCookies("lang", lang);
        
    }


    public Integer get__pg_max_records() {
        return __pg_max_records;
    }

    public void set__pg_max_records(Integer __pg_max_records) {
        this.__pg_max_records = __pg_max_records;
    }

    public Integer get__pg_start() {
        return __pg_start;
    }

    public void set__pg_start(Integer __pg_start) {
        this.__pg_start = __pg_start;
    }

    public Integer get__pg_total_records() {
        return __pg_total_records;
    }

    public void set__pg_total_records(Integer __pg_total_records) {
        this.__pg_total_records = __pg_total_records;
    }  
    
    protected void addSimpleError(String msg) {
        getContext().getValidationErrors().addGlobalError(
                new SimpleError(msg));
    }

    protected void addSimpleMessage(String msg) {
        getContext().getMessages().add(new SimpleMessage(msg));
    }
    
}
