/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pra.manager;

import com.google.inject.Inject;
import com.pra.dao.EventDAO;
import com.pra.dao.EventFileDAO;
import com.pra.model.Event;
import com.pra.model.EventFile;
import com.wideplay.warp.persist.Transactional;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author wan.fairul
 */
public class UserManager {

    @Inject
    private EventDAO eventDAO;
    @Inject
    private EventFileDAO eventFileDAO;
    @Inject
    protected com.google.inject.Provider<Session> sessionProvider;

    public List<Event> findAll() {
        String query = "SELECT a FROM Event a WHERE a.eventId is not null";
        Query q = sessionProvider.get().createQuery(query);
        return q.list();
    }

    public List<EventFile> findAllEventFile() {
        String query = "SELECT a FROM EventFile a WHERE a.eventFileId is not null";
        Query q = sessionProvider.get().createQuery(query);
        return q.list();
    }

    public EventFile findEventFile(String eventId) {
        String query = "SELECT a FROM EventFile a WHERE a.eventId = :eventId ";
        Query q = sessionProvider.get().createQuery(query);
        q.setString("eventId", eventId);
        return (EventFile) q.uniqueResult();
    }

    @Transactional
    public void saveOrUpdateEvent(Event event) {
        eventDAO.saveOrUpdate(event);
    }

    @Transactional
    public void deleteEvent(Event event) {
        eventDAO.delete(event);
    }
    
    @Transactional
    public void deleteEventFile(EventFile eventFile) {
        eventFileDAO.delete(eventFile);
    }

    @Transactional
    public void saveOrUpdateEventFile(EventFile eventFile) {
        eventFileDAO.saveOrUpdate(eventFile);
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public EventFileDAO getEventFileDAO() {
        return eventFileDAO;
    }

    public void setEventFileDAO(EventFileDAO eventFileDAO) {
        this.eventFileDAO = eventFileDAO;
    }

}
