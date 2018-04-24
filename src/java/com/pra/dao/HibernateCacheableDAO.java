package com.pra.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;



/**
 * DAO for Kods that do caching.
 * @author hishammk
 *
 * @param <T>
 * @param <ID>
 */

@SuppressWarnings("unchecked")
public abstract class HibernateCacheableDAO<T, ID extends Serializable> extends
		HibernateDAO<T, ID> {

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        try {
            Session s = (Session) sessionProvider.get();
            Criteria criteria = s.createCriteria(persistentClass);
            if (getDefaultOrderProperty() != null){
            	criteria.addOrder(Order.asc(getDefaultOrderProperty()));
            }
            List<T> objects = criteria.setCacheable(true).list();
            return objects;
        } finally {
            //session.close();
        }
    }
	
	
}
