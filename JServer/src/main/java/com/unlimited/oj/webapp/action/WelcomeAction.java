package com.unlimited.oj.webapp.action;

import com.opensymphony.xwork2.Preparable;

/**
 * Action for welcome page.
 */
public class WelcomeAction extends BaseAction
{
    private static final long serialVersionUID = 6111558938712115191L;

    /**
     * Grab the entity from the database before populating with request parameters
     */
    public void prepare()
    {
    	super.prepare();

    }

    /**
     * Default: just returns "success"
     * @return "success"
     */
    public String execute()
    {
        return SUCCESS();
    }

}
