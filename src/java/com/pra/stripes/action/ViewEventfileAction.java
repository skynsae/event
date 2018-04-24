/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pra.stripes.action;

import able.stripes.AbleActionBean;
import com.google.inject.Inject;
import com.pra.dao.EventFileDAO;
import com.pra.model.EventFile;
import com.pra.stripes.Configuration;
import java.io.File;
import java.io.FileInputStream;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 *
 * @author wan.fairul
 */
@HttpCache(allow = true)
@UrlBinding("/dokumen/view/eventfile/{idFile}")
public class ViewEventfileAction extends AbleActionBean{
    
    private Integer idFile;
    
    @Inject
    private Configuration conf;
    @Inject
    private EventFileDAO eventFileDAO;

    public Integer getIdFile() {
        return idFile;
    }

    public void setIdFile(Integer idFile) {
        this.idFile = idFile;
    }
    
    @DefaultHandler
    public Resolution view() {
        
        if(idFile == 0){
            return new ErrorResolution(500, "No Document provided!");
        }
        EventFile file = eventFileDAO.findById(idFile);
        if(file == null){
            return new ErrorResolution(500, "Document no found!!");
        }
        
        String docPath = conf.getProperty("document.path");
        if (docPath == null) {
            return new ErrorResolution(500,
                    "Configuration of \"document.path\" cant be found!!");
        }
        
        String path = docPath + (docPath.endsWith(File.separator) ? "" : File.separator) 
                + file.getDocumentPath();
        
        File f = new File(path);   
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(f);
        } catch (Exception ex) {
        }
        
//        getContext().getResponse().setContentType("application/octet-stream" );
        getContext().getResponse().setContentType(file.getContentType());

//        return new StreamingResolution("", fis).setFilename("tmp");
        return new StreamingResolution(file.getContentType(), fis).setFilename(file.getTitle());
        
    }

    public Configuration getConf() {
        return conf;
    }

    public void setConf(Configuration conf) {
        this.conf = conf;
    }

    public EventFileDAO getEventFileDAO() {
        return eventFileDAO;
    }

    public void setEventFileDAO(EventFileDAO eventFileDAO) {
        this.eventFileDAO = eventFileDAO;
    }
    
    
}
