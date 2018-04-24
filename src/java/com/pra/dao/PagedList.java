package com.pra.dao;


import java.util.List;

/**
 * PagedList is a List that implements pagination based on Hibernate Query implementation.
 * To set the page, at least method <code>PagedList.setPageNumber()</code> must be invoked, otherwise
 * this object is just normal List implementation.
 * @author hishammk
 *
 * @param <E>
 */
public interface PagedList<E> extends List<E> {
	
	public void setEnablePaging(boolean enable);
		
	public void setPageNumber(int pageNo);
	
	public void setObjectsPerPage(int size);
	
}
