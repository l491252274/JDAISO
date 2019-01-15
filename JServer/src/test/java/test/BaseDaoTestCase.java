/***********************************************************************
 * Module:  BaseDaoTestCase.java
 * Author:  benQ
 * Purpose: 作为所有DAO测试类的基类
 ***********************************************************************/

package test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import junit.framework.TestCase;

/**
 * Base class for running DAO tests.
 * @author mraible
 */
public abstract class BaseDaoTestCase extends TestCase {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());

    
    protected static ApplicationContext applicationContext=null;  
    
    static
    {
        applicationContext = new ClassPathXmlApplicationContext(getConfigLocations());   
    }
    
    protected SessionFactory sessionFactory=null;   
  
    protected Session session=null;   
    
    protected static String[] getConfigLocations() {
        return new String[] {
                "classpath:/applicationContext-test.xml"
            };
    }
   
    public void setUp() throws Exception 
    {
        sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");   
        session = SessionFactoryUtils.getSession(sessionFactory, true);   
        session.setFlushMode(FlushMode.AUTO);   
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));   
    }
    
    public void tearDown() throws Exception {
        TransactionSynchronizationManager.unbindResource(sessionFactory);   
        SessionFactoryUtils.releaseSession(session, sessionFactory);
    }  
}