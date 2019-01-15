/***********************************************************************
 * Module:  TaskTest.java
 * Author:  develop
 * Purpose: Defines test class to test the peristence of class Task
 ***********************************************************************/

package com.trisun.m2m.dao;

import java.util.*;

import org.apache.commons.logging.Log;
import org.hibernate.Transaction;
import junit.framework.Test;
import junit.framework.TestSuite;

import test.*;
import com.trisun.m2m.model.*;

import test.*;
import com.trisun.m2m.dao.*;

/**
 * Test case class to test the persistence of Task
 *
 */
public class TaskTest extends BaseDaoTestCase{

   Random random = new Random(1000);

   private Log logger = log;
    
   /**
    * Test object insert. Create a <Code>Task</Code> instance and save it into database by DAO's save 
    * method. Query <Code>Task</Code> instance from database by primary key and verify if persistent  
    * state had been successfully saved into database.
    * 
    * @throws Exception
    */
   public void testInsert() throws Exception {
      logger.debug("Test insert begins!");
      Transaction tx = null;
      tx = session.beginTransaction();
      Task task = null;
      // Create a new Task instance
      task = preInsert();
      // Insert it into database
      doInsert(task);
      // Verify if insert succeeds
      afterInsert(task);
      tx.rollback();
      logger.debug("Test insert ends!");
   }

   /**
    * Test object update. Create a <Code>Task</Code> instance and save it into database. Modify 
    * its persistent state and update database by DAO's update method. Query <Code>Task</Code> 
    * instance from database by primary key and verify if modified persistent state had been 
    * successfully updated into database.
    * 
    * @throws Exception
    */
   public void testUpdate() throws Exception {
      logger.debug("Test update begins!");
      Transaction tx = null;
      tx = session.beginTransaction();
      Task task = null;
      // Create a new Task instance and insert it into database
      task = preUpdate();
      // Modify its persistent state and update it into database
      doUpdate(task);
      // Verify if update succeeds
      afterUpdate(task);
      tx.rollback();
      logger.debug("Test update ends!");
   }

   /**
    * Test object delete. Create a <Code>Task</Code> instance and save it into database. Delete the 
    * instance by DAO's delete method and verify if it had been successfully deleted.
    * 
    * @throws Exception
    */
   public void testDelete() throws Exception {
      logger.debug("Test delete begins!");
      Transaction tx = null;
      tx = session.beginTransaction();
      Task task = null;
      // Create a new Task instance and insert it into database
      task = preDelete();
      // Delete it from database
      doDelete(task);
      // Verify if delete succeeds
      afterDelete(task);
      tx.rollback();
      logger.debug("Test delete ends!");
   }

   
   /**
    * Test query function </tt>findByTitle</tt>. Create multiple <Code>Task</Code> instances and set 
    * their attribute <Code>title</Code> to specified value. Save these instances into database and query them 
    * by DAO's <tt>findByTitle</tt> method. Verify if the method can find all instances saved before.
    * 
    * @throws Exception
    */
   public void testFindByTitle() throws Exception {
      logger.debug("Test find-by-title begins!");
      Transaction tx = null;
      tx = session.beginTransaction();
      java.lang.String title = String.valueOf(random.nextInt((int)Math.round(Math.pow(10,8))));
      List tasks = new ArrayList();
      // Create Task instances and save them into database
      tasks = preFindByTitle(title);
      // Query Task instances by property title
      List _result = doFindByTitle(title);
      // Verify query result
      afterFindByTitle(tasks, _result);
      tx.rollback();
      logger.debug("Test find-by-title ends!");
   }
   
   /**
    * Test query function </tt>findByInDate</tt>. Create multiple <Code>Task</Code> instances and set 
    * their attribute <Code>inDate</Code> to specified value. Save these instances into database and query them 
    * by DAO's <tt>findByInDate</tt> method. Verify if the method can find all instances saved before.
    * 
    * @throws Exception
    */
   public void testFindByInDate() throws Exception {
      logger.debug("Test find-by-inDate begins!");
      Transaction tx = null;
      tx = session.beginTransaction();
      Date inDate = new java.util.Date(random.nextInt(10000));
      List tasks = new ArrayList();
      // Create Task instances and save them into database
      tasks = preFindByInDate(inDate);
      // Query Task instances by property inDate
      List _result = doFindByInDate(inDate);
      // Verify query result
      afterFindByInDate(tasks, _result);
      tx.rollback();
      logger.debug("Test find-by-inDate ends!");
   }
   
   /**
    * Test query function </tt>findByAuthor</tt>. Create multiple <Code>Task</Code> instances and set 
    * their attribute <Code>author</Code> to specified value. Save these instances into database and query them 
    * by DAO's <tt>findByAuthor</tt> method. Verify if the method can find all instances saved before.
    * 
    * @throws Exception
    */
   public void testFindByAuthor() throws Exception {
      logger.debug("Test find-by-author begins!");
      Transaction tx = null;
      tx = session.beginTransaction();
      java.lang.String author = String.valueOf(random.nextInt((int)Math.round(Math.pow(10,8))));
      List tasks = new ArrayList();
      // Create Task instances and save them into database
      tasks = preFindByAuthor(author);
      // Query Task instances by property author
      List _result = doFindByAuthor(author);
      // Verify query result
      afterFindByAuthor(tasks, _result);
      tx.rollback();
      logger.debug("Test find-by-author ends!");
   }

   /**
    * Do insert test preparation. Create a new <Code>Task</Code> instance.
    * 
    * @return new <Code>Task</Code> instance
    * @throws Exception
    */
   private Task preInsert() throws Exception {
      Task task = TaskTestHelper.newInstance(false);
   
      try
      {   } catch(Exception ex){}
      return task;
   }
   
   /**
    * Insert specified <Code>Task</Code> instance into database. 
    * 
    * @param task instance to insert
    * @throws Exception
    */
   private void doInsert(Task task) {
      TaskTestHelper.save(task);
   }
   
   /**
    * Query <Code>Task</Code> instance from database by primary key. Verify if persistent state 
    * had been successfully persisted.
    *
    * @param task inserted instance
    * @throws Exception
    */
   private void afterInsert(Task task) {
      Task anotherTask = TaskTestHelper.dao.get(task.getId());
      assertEquals("Queried result does not equal to inserted instance",
         task, anotherTask);
      TaskTestHelper.delete(anotherTask);
   }

   /**
    * Do update test preparation. Create a new <Code>Task</Code> instance and save it into database.
    * 
    * @return new <Code>Task</Code> instance
    * @throws Exception
    * @see TaskTest#prepare()
    */
   private Task preUpdate() throws Exception {
      return prepare();
   }
   
   /**
    * Modify persistent state of specified <Code>Task</Code> instance and update it to database.
    *
    * @param task object to update
    * @throws Exception
    */
   private void doUpdate(Task task) throws Exception {
      TaskTestHelper.modifyObject(task);
      TaskTestHelper.dao.update(task);
   }
   
   /**
    * Query <Code>Task</Code> instance from database by primary key. Verify if modified persistent 
    * state had been successfully updated into database
    *
    * @param task object to update
    * @throws Exception
    */
   private void afterUpdate(Task task) throws Exception {
      Task another = TaskTestHelper.dao.get(task.getId());
      assertEquals("Queried result does not equal to updated instance", task, another);  
      TaskTestHelper.delete(another);
   }

   /**
    * Do delete test preparation. Create a new <Code>Task</Code> instance and save it into database.
    * 
    * @return new <Code>Task</Code> instance
    * @throws Exception
    * @see TaskTest#prepare()
    */
   private Task preDelete() throws Exception {
      return prepare();
   }
   
   /**
    * Delete specified <Code>Task</Code> instance from database.
    *
    * @param task object to delete
    * @throws Exception
    */
   private void doDelete(Task task) throws Exception {
      TaskTestHelper.delete(task);
   }
   
   /**
    * Verify if specified <Code>Task</Code> instance had been successfully deleted.
    *
    * @param task deleted object 
    * @throws Exception
    */
   private void afterDelete(Task task) throws Exception {
      Task another = TaskTestHelper.dao.get(task.getId());
      assertNull("Inserted instance is not deleted", another);
   }

   /**
    * Do function <tt>FindByTitle</tt> test preparation. Create <Code>Task</Code> instances 
    * and set their attribute title to specified value. Save these instances into database.
    *
    * @return newly created object list
    * @throws Exception
    */
   private List preFindByTitle(java.lang.String title) throws Exception {
      List tasks = new ArrayList();
   
      // Delete old instances from database if any
      List oldTasks = TaskTestHelper.dao.findBy("title", title);
   
      if (oldTasks != null) {
         Iterator it = oldTasks.iterator();
         while (it.hasNext()) 
            TaskTestHelper.delete((Task) it.next()); 
      }
   
      // Create instances and set their property title to value title
      int randomsize = 5 + Math.abs((random).nextInt(7));
      for (int i = 0; i < randomsize; i++) {
         Task task = TaskTestHelper.newInstance(false);
         task.setTitle(title);
         TaskTestHelper.save(task);
         tasks.add(i, task);
      }
      return tasks;
   }
   
   /**
    * Query <Code>Task</Code> instances from database by attribute <Code>title</Code>.
    *
    * @return result object list
    * @throws Exception
    */
   private List doFindByTitle(java.lang.String title) {
      return TaskTestHelper.dao.findBy("title", title);
   }
   
   /**
    * Verify if method <tt>findByTitle</tt> can find all <Code>Task</Code> instances saved before.
    * 
    * @param tasks inserted instance list
    * @param resultFound result instance list
    * @throws Exception
    */
   private void afterFindByTitle(List tasks, List resultFound) {
      if (tasks != null && tasks.size() > 0) {
         assertNotNull("Result returned by find-by-title is null.", resultFound);
         assertEquals("Result count returned by find-by-title is incorrect.", tasks.size(), resultFound.size());
   
         Iterator it = tasks.iterator();
         while (it.hasNext()) {
            Task task = (Task) it.next();
            Task another = TaskTestHelper.getTaskByPk(resultFound, task.getId());
            assertEquals("Result returned by find-by-title does not equal to inserted task object.", task, another);
            TaskTestHelper.delete(another);
         }
      }
   }
   
   /**
    * Do function <tt>FindByInDate</tt> test preparation. Create <Code>Task</Code> instances 
    * and set their attribute inDate to specified value. Save these instances into database.
    *
    * @return newly created object list
    * @throws Exception
    */
   private List preFindByInDate(Date inDate) throws Exception {
      List tasks = new ArrayList();
   
      // Delete old instances from database if any
      List oldTasks = TaskTestHelper.dao.findBy("inDate", inDate);
   
      if (oldTasks != null) {
         Iterator it = oldTasks.iterator();
         while (it.hasNext()) 
            TaskTestHelper.delete((Task) it.next()); 
      }
   
      // Create instances and set their property inDate to value inDate
      int randomsize = 5 + Math.abs((random).nextInt(7));
      for (int i = 0; i < randomsize; i++) {
         Task task = TaskTestHelper.newInstance(false);
         task.setInDate(inDate);
         TaskTestHelper.save(task);
         tasks.add(i, task);
      }
      return tasks;
   }
   
   /**
    * Query <Code>Task</Code> instances from database by attribute <Code>inDate</Code>.
    *
    * @return result object list
    * @throws Exception
    */
   private List doFindByInDate(Date inDate) {
      return TaskTestHelper.dao.findBy("inDate", inDate);
   }
   
   /**
    * Verify if method <tt>findByInDate</tt> can find all <Code>Task</Code> instances saved before.
    * 
    * @param tasks inserted instance list
    * @param resultFound result instance list
    * @throws Exception
    */
   private void afterFindByInDate(List tasks, List resultFound) {
      if (tasks != null && tasks.size() > 0) {
         assertNotNull("Result returned by find-by-inDate is null.", resultFound);
         assertEquals("Result count returned by find-by-inDate is incorrect.", tasks.size(), resultFound.size());
   
         Iterator it = tasks.iterator();
         while (it.hasNext()) {
            Task task = (Task) it.next();
            Task another = TaskTestHelper.getTaskByPk(resultFound, task.getId());
            assertEquals("Result returned by find-by-inDate does not equal to inserted task object.", task, another);
            TaskTestHelper.delete(another);
         }
      }
   }
   
   /**
    * Do function <tt>FindByAuthor</tt> test preparation. Create <Code>Task</Code> instances 
    * and set their attribute author to specified value. Save these instances into database.
    *
    * @return newly created object list
    * @throws Exception
    */
   private List preFindByAuthor(java.lang.String author) throws Exception {
      List tasks = new ArrayList();
   
      // Delete old instances from database if any
      List oldTasks = TaskTestHelper.dao.findBy("author", author);
   
      if (oldTasks != null) {
         Iterator it = oldTasks.iterator();
         while (it.hasNext()) 
            TaskTestHelper.delete((Task) it.next()); 
      }
   
      // Create instances and set their property author to value author
      int randomsize = 5 + Math.abs((random).nextInt(7));
      for (int i = 0; i < randomsize; i++) {
         Task task = TaskTestHelper.newInstance(false);
         task.setAuthor(author);
         TaskTestHelper.save(task);
         tasks.add(i, task);
      }
      return tasks;
   }
   
   /**
    * Query <Code>Task</Code> instances from database by attribute <Code>author</Code>.
    *
    * @return result object list
    * @throws Exception
    */
   private List doFindByAuthor(java.lang.String author) {
      return TaskTestHelper.dao.findBy("author", author);
   }
   
   /**
    * Verify if method <tt>findByAuthor</tt> can find all <Code>Task</Code> instances saved before.
    * 
    * @param tasks inserted instance list
    * @param resultFound result instance list
    * @throws Exception
    */
   private void afterFindByAuthor(List tasks, List resultFound) {
      if (tasks != null && tasks.size() > 0) {
         assertNotNull("Result returned by find-by-author is null.", resultFound);
         assertEquals("Result count returned by find-by-author is incorrect.", tasks.size(), resultFound.size());
   
         Iterator it = tasks.iterator();
         while (it.hasNext()) {
            Task task = (Task) it.next();
            Task another = TaskTestHelper.getTaskByPk(resultFound, task.getId());
            assertEquals("Result returned by find-by-author does not equal to inserted task object.", task, another);
            TaskTestHelper.delete(another);
         }
      }
   }

   /**
    * Make some test preparation that is common to test methods. Create a new <Code>Task</Code> 
    * instance. Delete the instance from database if instance with the same primary key had already 
    * been saved. Save the instance in the end.
    *
    * @return newly created and saved <Code>Task</Code> instance
    * @throws Exception
    */
   private Task prepare() {
      Task task = TaskTestHelper.newInstance(false);
      deleteBeforeSave(task);
      return task;
   }
   
   /** 
    * Delete the instance from database if instance with the same primary key as specified 
    * <Code>Task</Code> instance had already been saved.
    *
    * @param task
    * @throws Exception
    */
   private void deleteBeforeSave(Task task) {
   
      try
      {   } catch(Exception ex){}
      TaskTestHelper.save(task);
   }

   /**
    * Return test suite containing current test class
    *
    * @return test suite containing test class <Code>TaskTest</Code>
    */
   public static Test suite() {
      return new TestSuite(TaskTest.class);
   }
      
   public void setUp() 
   {
       try
       {
           super.setUp();
       
           // init dao objects
           TaskTestHelper.dao = (TaskDao)applicationContext.getBean("taskDao");
       } catch(Exception ex){ex.printStackTrace();}
   }   
}