/***********************************************************************
 * Module:  BaseTestCase.java
 * Author:  develop
 * Purpose: Defines class that can run all unit tests by its main function
 ***********************************************************************/

package test;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Class that can run all unit tests by its main function
 */
public class AllTests {

   /**
    * Create a new test suite that contains all test suites of classes that can be tested.
    * 
    * @return test suite containing all unit tests selected
    */
   public static Test suite() 
   {
   
      TestSuite suite = new TestSuite();
      
      suite.addTest(com.trisun.m2m.dao.TaskTest.suite());

      return suite;
   }

   /**
    * Main function
    *
    * @param args argument list
    */
   public static void main(String args[]) 
   {
      TestRunner.run(suite());
   }

}