package com.pra.dao;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;



public class HbnPagedListImpl<E> implements PagedList<E>{
	public final static int DEFAULT_PAGE_SIZE = 20;
	
	protected Query query;
	protected int pageNo = 0;
	protected int pageSize = DEFAULT_PAGE_SIZE;
	protected List<E> result;
	protected int size = -1;
	
	public HbnPagedListImpl(Query query){
		this.query = query;
	}

	@Override
	public boolean add(E e) {
		doQuery();
		return result.add(e);
	}

	@Override
	public void add(int index, E element) {
		doQuery();
		result.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		doQuery();
		return result.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		doQuery();
		return result.addAll(index, c);
	}

	@Override
	public void clear() {
		doQuery();
		result.clear();
	}

	@Override
	public boolean contains(Object o) {
		doQuery();
		return result.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		doQuery();
		return result.containsAll(c);
	}

	@Override
	public E get(int index) {
		doQuery();
		return result.get(index);
	}

	@Override
	public int indexOf(Object o) {
		doQuery();
		return result.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		doQuery();
		return result.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		doQuery();
		return result.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		doQuery();
		return result.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		doQuery();
		return result.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		doQuery();
		return result.listIterator(index);
	}

	@Override
	public boolean remove(Object o) {
		doQuery();
		return result.remove(o);
	}

	@Override
	public E remove(int index) {
		doQuery();
		return result.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		doQuery();
		return result.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		doQuery();
		return result.retainAll(c);
	}

	@Override
	public E set(int index, E element) {
		doQuery();
		return result.set(index, element);
	}

	@Override
	public int size() {
		doQuery();
		return (size == 0? 0 :result.size());
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		doQuery();
		return result.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		doQuery();
		return result.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		doQuery();
		return result.toArray(a);
	}

	protected List<E> getInternalList(){
		if (result == null){
			doQuery();
		}
		return result;
	}
	
	protected void doQuery(){
		if (size > -1) return;
		 try{
			 // getting full size
			 // TODO: using count(*)
			 ScrollableResults scrollableResults = query.scroll(ScrollMode.FORWARD_ONLY);
			 try{
				 // for Hibernate or SQL driver bug
				 scrollableResults.next();
			 } catch (HibernateException e){
				 System.err.println("Possible empty result exception:" + e.getMessage());
				 size = 0;
			 }
			 if (size != 0) { // make sure result not empty
				 scrollableResults.last();
				 size = scrollableResults.getRowNumber() + 1;
				 scrollableResults = null;

				 if (pageNo > 0){
					 query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize);
				 }
			 }
			 result = (List<E>) query.list();
		 } catch (HibernateException e){
			 e.printStackTrace();
			 
			 RuntimeException re = new RuntimeException(e.getMessage());
			 re.initCause(e);
			 throw re;
		 }
	}
	
	//@Override
	public int getFullListSize() {
		doQuery();
		return size;
	}

	//@Override
	public List<E> getList() {
		doQuery();
		return result;
	}

	//@Override
	public int getObjectsPerPage() {
		doQuery();
		if (pageNo == 0){ // paging disabled
			return getFullListSize();
		} else{
			return pageSize;
		}
	}
	
	public void setObjectsPerPage(int size){
		pageSize = size;
	}

	public void setPageNumber(int pageNo){
		this.pageNo = pageNo;
	}
	
	/**
	 * 
	 */
	//@Override
	public int getPageNumber() {
		doQuery();
		return pageNo;
	}

	/**
	 * Do not store in cache
	 */
	//@Override
	public String getSearchId() {
		return null;
	}

	/**
	 * Do not support sorting currently
	 */
	//@Override
	public String getSortCriterion() {
		return null;
	}

	/**
	 * Do not support sorting currently
	@Override
	public SortOrderEnum getSortDirection() {
		return null;
	}
	 */
	
	/**
	 * It's useless to disable paging after query has been executed.
	 */
	public void setEnablePaging(boolean enable){
		if (enable){
			if (pageNo < 1) pageNo = 1;
		} else{
			pageNo = 0;
		}
	}

}
