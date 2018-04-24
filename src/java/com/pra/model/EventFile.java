/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pra.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author skynsae
 */
@Entity
@Table(name = "event_file")
@NamedQueries({
    @NamedQuery(name = "EventFile.findAll", query = "SELECT t FROM EventFile t")})
public class EventFile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "event_file_id")
    private Integer eventFileId;
    @Column(name = "event_id")
    private Integer eventId;
    @Column(name = "document_path")
    private String documentPath;
    @Column(name = "content_type")
    private String contentType;
    @Column(name = "title")
    private String title;

    public Integer getEventFileId() {
        return eventFileId;
    }

    public void setEventFileId(Integer eventFileId) {
        this.eventFileId = eventFileId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }   

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


   
}
