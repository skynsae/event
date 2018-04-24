package com.pra.stripes;

import able.stripes.JSP;
import com.google.inject.Inject;
import com.pra.dao.EventDAO;
import com.pra.manager.UserManager;
import com.pra.model.Event;
import com.pra.model.EventFile;
import com.pra.stripes.action.ViewEventfileAction;
import com.pra.stripes.util.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sourceforge.stripes.action.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

@UrlBinding("/main")
public class LoginActionBean extends BaseActionBean {

    @Inject
    Configuration conf;
    String lang = "";
    private Event event;
    @Inject
    protected com.google.inject.Provider<Session> sessionProvider;
    private static Logger LOG = Logger.getLogger(LoginActionBean.class);
    @Inject
    private UserManager userManager = new UserManager();
    private List<Event> listOfEvent;
    private List<EventFile> listEventfile;
    @Inject
    private EventDAO eventDAO;
    private FileBean document;
    private static final String FOLDERNAME = "EVENT_IMG";

    @DefaultHandler
    @DontValidate
    public Resolution showForm() {
        listEventfile = userManager.findAllEventFile();
        return new JSP("main.jsp");
    }

    @DontValidate
    public Resolution newEvent() {
        listOfEvent = userManager.findAll();
        return new JSP("newEvent.jsp");
    }

    @DontValidate
    public Resolution getEventDetail() {
        JSONObject obj = new JSONObject();
        String id = getContext().getRequest().getParameter("id");
        LOG.debug(id);
        Event e = eventDAO.findById(Integer.parseInt(id));
        if (e != null) {
            obj = new JSONObject(e);
        } else {

        }

        return new StreamingResolution("application/json", obj.toString());
    }

    @DontValidate
    public Resolution getEventImage() {

        String id = getContext().getRequest().getParameter("id");
        LOG.debug(id);
        Event e = eventDAO.findById(Integer.parseInt(id));

        EventFile file = userManager.findEventFile(String.valueOf(e.getEventId()));
        if (file == null) {
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
        try {
            fis = new FileInputStream(f);
        } catch (Exception ex) {
        }

        return new StreamingResolution(file.getContentType(), fis);
    }

    public Resolution submitEvent() {

        String msg = "";
        String error = "";
        Transaction tx = sessionProvider.get().beginTransaction();
        event.setEventName(event.getEventName());
        event.setEventPrice(event.getEventPrice());
        event.setEventType(event.getEventType());
        event.setEventDate(event.getEventDate());
        userManager.saveOrUpdateEvent(event);

        String documentPath = conf.getProperty("document.path");
        if (StringUtils.isBlank(documentPath)) {
            return new ErrorResolution(500,
                    "configuration of \"document.path\" not found!");
        }

        String path = documentPath + (documentPath.endsWith(File.separator) ? "" : File.separator) + FOLDERNAME + File.separator + event.getEventId() + File.separator;
        String docPath = FOLDERNAME + File.separator + event.getEventId() + File.separator;

        if (document != null) {
            FileUtil fileUtil = new FileUtil(path);
            try {
                fileUtil.saveFile(document.getFileName(), document.getInputStream(), document.getSize());
                EventFile doc = new EventFile();
                doc.setTitle(document.getFileName());
                doc.setDocumentPath(docPath + document.getFileName());
                doc.setEventId(event.getEventId());
                doc.setContentType(document.getContentType());
                userManager.saveOrUpdateEventFile(doc);
            } catch (Exception ex) {
                LOG.error("error while saving file {}");
                tx.rollback();
            }
        }
        addSimpleMessage("Event already save");
        tx.commit();
        return new RedirectResolution(LoginActionBean.class, "newEvent");
    }

    public Resolution deleteEvent() {
        Transaction tx = sessionProvider.get().beginTransaction();
        String eventId = getContext().getRequest().getParameter("eventId");
        if (eventId != null) {
            event = eventDAO.findById(Integer.parseInt(eventId));
            if (event != null) {
                EventFile eF = userManager.findEventFile(String.valueOf(event.getEventId()));
                userManager.deleteEvent(event);
                userManager.deleteEventFile(eF);
                addSimpleMessage("Success Delete");
                tx.commit();
            }

        }

        return new RedirectResolution(LoginActionBean.class, "newEvent");
    }

    public Resolution editMohon() {

        String eventId = getContext().getRequest().getParameter("event.eventId");
        event = eventDAO.findById(Integer.parseInt(eventId));

        return new JSP("editNewEvent.jsp");
    }

    public Resolution updateEvent() {

        String msg = "";
        String error = "";
        Transaction tx = sessionProvider.get().beginTransaction();
        event.setEventName(event.getEventName());
        event.setEventPrice(event.getEventPrice());
        event.setEventType(event.getEventType());
        event.setEventDate(event.getEventDate());
        userManager.saveOrUpdateEvent(event);
        
        String documentPath = conf.getProperty("document.path");
        if (StringUtils.isBlank(documentPath)) {
            return new ErrorResolution(500,
                    "configuration of \"document.path\" not found!");
        }

        String path = documentPath + (documentPath.endsWith(File.separator) ? "" : File.separator) + FOLDERNAME + File.separator + event.getEventId() + File.separator;
        String docPath = FOLDERNAME + File.separator + event.getEventId() + File.separator;

        if (document != null) {
            FileUtil fileUtil = new FileUtil(path);
            try {
                fileUtil.saveFile(document.getFileName(), document.getInputStream(), document.getSize());
                EventFile eF = userManager.findEventFile(String.valueOf(event.getEventId()));
                eF.setTitle(document.getFileName());
                eF.setDocumentPath(docPath + document.getFileName());
                eF.setEventId(event.getEventId());
                eF.setContentType(document.getContentType());
                userManager.saveOrUpdateEventFile(eF);
            } catch (Exception ex) {
                LOG.error("error while saving file {}");
                tx.rollback();
            }
        }
        addSimpleMessage("Event already update");
        tx.commit();
        return new RedirectResolution(LoginActionBean.class, "newEvent");
    }

    public Configuration getConf() {
        return conf;
    }

    public void setConf(Configuration conf) {
        this.conf = conf;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Event> getListOfEvent() {
        return listOfEvent;
    }

    public void setListOfEvent(List<Event> listOfEvent) {
        this.listOfEvent = listOfEvent;
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public FileBean getDocument() {
        return document;
    }

    public void setDocument(FileBean document) {
        this.document = document;
    }

    public List<EventFile> getListEventfile() {
        return listEventfile;
    }

    public void setListEventfile(List<EventFile> listEventfile) {
        this.listEventfile = listEventfile;
    }

}
