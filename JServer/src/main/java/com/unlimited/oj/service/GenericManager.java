package com.unlimited.oj.service;

import java.io.Serializable;
import java.util.List;

import com.unlimited.oj.dao.support.Page;

/**
 * Generic Manager that talks to GenericDao to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) managers
 * for your domain objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public interface GenericManager<T, PK extends Serializable> {

    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
    T get(PK id);
    T get(PK id, String sign);

    List<T> getAll();
    List<T> getAll(String sign);

    Page getPage(int pageNo, int pageSize);
    Page getPage(int pageNo, int pageSize, String sign);

    Page getPage(int pageNo, int pageSize, String orderBy, boolean sort);
    Page getPage(int pageNo, int pageSize, String orderBy, boolean sort, String sign);

    Page getPageBySearch(int pageNo, int pageSize, String key, Object value);
    Page getPageBySearch(int pageNo, int pageSize, String key, Object value, String orderBy, boolean sort);

    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id the identifier (primary key) of the object to get
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return the updated object
     */
    void save(T object);
    
    void update(T object);

    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
     */
    void remove(PK id);

    void clear();
    
    public void flush();
    
}