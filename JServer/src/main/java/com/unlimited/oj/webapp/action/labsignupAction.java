package com.unlimited.oj.webapp.action;

import org.springframework.security.AccessDeniedException;
import org.springframework.security.context.SecurityContextHolder;
 
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;
 
 

import com.unlimited.oj.util.Tool;
import com.unlimited.oj.webapp.util.RequestUtil;
import com.unlimited.webserver.model.Lab;

import org.springframework.mail.MailException;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Action to allow new labs to sign up.
 */
public class labsignupAction extends BaseAction
{

    private static final long serialVersionUID = 6558317334878272308L;
    private Lab lab;
     
    
    @Override
    public void prepare()
    {
        super.prepare();
    }

    public void setCancel(String cancel)
    {
        this.cancel = cancel;
    }

    public void setlab(Lab lab)
    {
        this.lab = lab;
    }

    /**
     * Return an instance of the lab - to display when validation errors occur
     *
     * @return a populated lab
     */
    public Lab getLab()
    {
        return lab;
    }

    /**
     * When method=GET, "input" is returned. Otherwise, "success" is returned.
     *
     * @return cancel, input or success
     */
    @Override
    public String execute()
    {
        if (cancel != null)
        {
            return CANCEL;
        }
        if (ServletActionContext.getRequest().getMethod().equals("GET"))
        {
            return INPUT();
        }
        return SUCCESS();
    }

    /**
     * Returns "input"
     *
     * @return "input" by default
     */
    @Override
    public String doDefault()
    {
        return INPUT();
    }

    /**
     * Save the lab, encrypting their passwords if necessary
     *
     * @return success when good things happen
     * @throws Exception
     *             when bad things happen
     */
    public String save()
    {
        
        Lab u = new Lab();
        u.setFloor(lab.getFloor());
        u.setId(lab.getId());
        u.setRoomid(lab.getRoomid());
        try {
			labManager.saveLab(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         

        return SUCCESS();
    }

     /**
     * Activate lab Account
     *
     * @return
     */
    public String activeAccount()
    {
         
        return SUCCESS();
    }
    
    
    
}
