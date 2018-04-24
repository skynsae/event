package com.pra.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import com.google.inject.Inject;
import java.lang.reflect.Type;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;




@SuppressWarnings("unchecked")
public abstract class HibernateDAO<T, ID extends Serializable> {
    
    @Inject
    protected com.google.inject.Provider<Session> sessionProvider;

    protected Class<T> persistentClass;
    
    private static final Logger LOG = Logger.getLogger(HibernateDAO.class);
    private static final boolean debug = LOG.isDebugEnabled();
    
    @Inject
    public HibernateDAO(com.google.inject.Provider<Session> sessionProvider){
        this();
        this.sessionProvider = sessionProvider;
    }
    
    public HibernateDAO(){
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType){
            persistentClass =
                   (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else{
            // bytecode generation has probably removed the parameter information
            // this probably has runtime error ClassCastException, but needed to be compatible with Guice
            Class clz = (Class) type;
            persistentClass =
                   (Class<T>) ((ParameterizedType) clz.getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }
    
    @SuppressWarnings("unchecked")
    public T findById(ID id) {
    	long t = 0L;
    	if (debug){ // checking performance
                String msg = String.format("finding by ID for %s [%s]",
                        persistentClass.getName(), id);
    		LOG.debug(msg);
    		t = System.currentTimeMillis();
    	}
    	
        try {
            Session s = sessionProvider.get();
            //Transaction txn = s.beginTransaction();
            T object = (T) s.get(persistentClass, id);
            //txn.commit();
            return object;
        } finally {
            if (debug) LOG.debug("took " + (System.currentTimeMillis() - t ) + "ms to find");
        }
    }
    
    @SuppressWarnings("unchecked")
    public boolean exists(ID id) {
        if (debug) LOG.debug("check exist");
    	try {
            Session s = sessionProvider.get();
            T object = (T) s.get(persistentClass, id);
            
            if(object == null) {
            	return false;
            }
            else {
            	return true;
            }
        } finally {
        }
    }

    public T saveOrUpdate(T object){
        if (debug) LOG.debug(String.format("saving/updating %s", persistentClass.getName()));
        try {
            Session s = sessionProvider.get();
            s.saveOrUpdate(object);
        } catch (HibernateException e) {
            e.printStackTrace();
        	throw e;
        } finally {
        }
        return object;
    }

    public void save(T object) {
        if (debug) LOG.debug("saving");
        try {
            Session s = sessionProvider.get();
            s.save(object);
        } catch (HibernateException e) {
            e.printStackTrace();
        	throw e;
        } finally {
        }
    }

    public void update(T object) {
        if (debug) LOG.debug("updating");
        try {
            Session s = sessionProvider.get();
            s.update(object);
        } catch (HibernateException e) {
            e.printStackTrace();
        	throw e;
        } finally {
        }
    }

    public void delete(T object) {
        if (debug) LOG.debug("deleting");
        try {
            Session s = sessionProvider.get();
            s.delete(object);
        } catch (HibernateException e) {
            e.printStackTrace();
        	throw e;
        } finally {
        }
    }

    /**
     * Subclass should override this method to make collection returned being sorted.
     * @return the property to be ordered with.
     */
    public String getDefaultOrderProperty() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        try {
            Session s = sessionProvider.get();
            Criteria criteria = s.createCriteria(persistentClass);
            if (getDefaultOrderProperty() != null){
            	criteria.addOrder(Order.asc(getDefaultOrderProperty()));
            }
            List<T> objects = criteria.list();
            return objects;
        } finally {
            //session.close();
        }
    }

    /**
     * Search T objects from persistent store using provided criteria names & values.
     * @param criteriaNames
     * @param criteriaValues
     * @return
     */
    public List<T> findByEqualCriterias(String[] criteriaNames,
                                        Object[] criteriaValues,
                                        String orderProperty) {
        return findByEqualCriterias(criteriaNames, criteriaValues,
                                    orderProperty, null);
    }

    /**
     * Search T objects from persistent store using provided criteria names & values.
     * @param criteriaNames
     * @param criteriaValues
     * @param prefetchedProperties optimizations to prefetch associated property using EAGER mode
     * @return
     */
    public List<T> findByEqualCriterias(String[] criteriaNames,
                                        Object[] criteriaValues,
                                        String orderProperty,
                                        String[] prefetchedProperties) {
        Session s = sessionProvider.get();
        Criteria c = s.createCriteria(persistentClass);
        for (int i = 0; i < criteriaNames.length; i++) {
            if (criteriaValues[i] != null && criteriaNames[i] != null) {
                Criteria c1 = c;
                String name = criteriaNames[i];
                // check for nested properties (NOTE: only support 1 level)
                String[] str = criteriaNames[i].split("\\.");
                if (str.length > 1) {
                    c1 = c.createCriteria(str[0]);
                    name = str[1];
                }
                if (criteriaValues[i] instanceof String) { // String
                    c1.add(Restrictions.like(name,
                                             criteriaValues[i]).ignoreCase());
                } else {
                    c1.add(Restrictions.eq(name, criteriaValues[i]));
                }
            }
        }

        if (prefetchedProperties != null && prefetchedProperties.length > 0) {
            for (String prop : prefetchedProperties) {
                c.setFetchMode(prop, FetchMode.JOIN);
            }
        }

        if (orderProperty != null) {
            c.addOrder(Order.asc(orderProperty));
        }

        return new HbnCriteriaPagedListImpl<T>(c);
    }


    @SuppressWarnings("unchecked")
    public List<T> findByExample(T obj) {
        Example eg = Example.create(obj);
        eg.enableLike();
        Session s = sessionProvider.get();
        return (List<T>) s.createCriteria(persistentClass).add(eg).list();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByExample(T obj, String[] excludeProperty) {
        Session s = sessionProvider.get();
        Criteria crit = s.createCriteria(persistentClass);
        Example example = Example.create(obj);
        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        return crit.list();
    }


}
