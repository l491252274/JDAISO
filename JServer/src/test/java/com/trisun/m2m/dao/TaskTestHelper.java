/***********************************************************************
 * Module:  TaskTestHelper.java
 * Author:  develop
 * Purpose: Defines the test helper class for class Task
 ***********************************************************************/
 
package com.trisun.m2m.dao;

import java.util.*;
import javax.persistence.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.trisun.m2m.model.*;
import com.trisun.m2m.dao.TaskDao;

/**
 * Test helper class for Task. It provides some utility methods, such as methods to
 * create new instance, modify instance and save object etc.
 *
 */
public class TaskTestHelper {

   public static java.util.Random random = test.Util.getRandom();
   
   public static TaskDao dao = null;
   
   /**
    * Empty Constructor
    *
    */
   public TaskTestHelper(){
   
   }

   /**
    * Create new Task instance, initializing associated objects if required.
    *
    * @param associationInitialized if association should be initantiated
    * @return new persistent object
    */
   public static Task newInstance(boolean associationInitialized){
      Task persistentObject = new Task();
   
      persistentObject.setTitle(String.valueOf(random.nextInt((int)Math.round(Math.pow(10,8)))));
      persistentObject.setInDate(new java.util.Date(random.nextInt(10000)));
      persistentObject.setAuthor(String.valueOf(random.nextInt((int)Math.round(Math.pow(10,8)))));
      return persistentObject;
   }

   /**
    * Modify persistent state of specified instance. Primary identifier value will not be changed. 
    * 
    * @param taskObject saved persistent object
    */
   public static void modifyObject(Task persistentObject){
     persistentObject.setTitle(String.valueOf(random.nextInt((int)Math.round(Math.pow(10,8)))));
     persistentObject.setInDate(new java.util.Date(random.nextInt(10000)));
     persistentObject.setAuthor(String.valueOf(random.nextInt((int)Math.round(Math.pow(10,8)))));
   }

   /**
    * Save specified Task instance. Intialize and save parent objects to avoid violating foreign 
    * key constraints.
    *
    * @param taskObject persistent object to be saved
    * @throws Exception
    */
   public static void save(Task taskObject) {
      dao.save(taskObject);
   }

   /**
    * Delete specified <Code>Task</Code> instance, deleting associated objects if any. 
    *
    * @param taskObject persistent object to be deleted
    * @throws Exception
    */
   public static void delete(Task taskObject) {
   
      dao.remove(taskObject);
   }

   /**
    * Get <Code>Task</Code> instance from collection by primary key. 
    * 
    * @param collection Task instance collection
    * @param pk primary key
    * @return Task instance
    */
   public static Task getTaskByPk(Collection collection, java.io.Serializable pk){
      Task taskObject = null;   
      if (collection == null) return null;
     
      Iterator iterator = collection.iterator();
      while (iterator.hasNext()) {
         Task object = (Task) iterator.next();
         if (new Long(object.getId()).equals(pk)){
            taskObject = object;
            break;
         }
      }
      return taskObject;
   }
   
   /**
    * Get <Code>Task</Code> instance from instance array by primary key. 
    * 
    * @param taskArray Task instance array
    * @param pk primary key
    * @return Task instance
    */
   public static Task getTaskByPk(Task[] taskArray, java.io.Serializable pk){
      return taskArray == null ? null : getTaskByPk(Arrays.asList(taskArray), pk);
   }
}