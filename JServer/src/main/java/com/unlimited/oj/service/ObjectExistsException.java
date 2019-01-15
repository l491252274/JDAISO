package com.unlimited.oj.service;


/**
 * An exception that is thrown by classes wanting to trap unique 
 * constraint violations.  This is used to wrap Spring's 
 * DataIntegrityViolationException so it's checked in the web layer.
 *
 * @author <a href="mailto:checkie@scau.edu.cn">Checkie</a>
 */
public class ObjectExistsException extends Exception {
    private static final long serialVersionUID = 4050411105178810162L;

    /**
     * Constructor for OjbectExistsException.
     *
     * @param message exception message
     */
    public ObjectExistsException(final String message) {
        super(message);
    }
}
