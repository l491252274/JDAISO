/***********************************************************************
 * Module:  LoginLog.java
 * Author:  benQ
 * Purpose: Defines the Class LoginLog
 ***********************************************************************/

package com.unlimited.oj.model;

import javax.persistence.*;
import com.unlimited.oj.model.*;
import java.util.*;

@Entity(name="LoginLog")
@Table(name="login_log")
public class LoginLog implements java.io.Serializable {
   private java.lang.String memo;
   private java.lang.String userName;
   
   public java.lang.Long id;
   public java.lang.String password;
   public java.lang.String ip;
   public java.util.Date time;
   
   /**
    * Empty constructor which is required by EJB 3.0 spec.
    *
    */
   public LoginLog() {
      // TODO Add your own initialization code here.
   }
   
   /**
    * Get value of id
    *
    * @return id 
    */
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   @Column(name="id", nullable=false, insertable=true, updatable=true)
   public java.lang.Long getId()
   {
      return id;
   }
   
   /**
    * Set value of id
    *
    * @param newId 
    */
   public void setId(java.lang.Long newId)
   {
      this.id = newId;
   }
   
   /**
    * Get value of password
    *
    * @return password 
    */
   @Basic(optional=true)
   @Column(name="password", insertable=true, updatable=true, length=20)
   public java.lang.String getPassword()
   {
      return password;
   }
   
   /**
    * Set value of password
    *
    * @param newPassword 
    */
   public void setPassword(java.lang.String newPassword)
   {
      this.password = newPassword;
   }
   
   /**
    * Get value of ip
    *
    * @return ip 
    */
   @Basic(optional=true)
   @Column(name="ip", insertable=true, updatable=true, length=30)
   public java.lang.String getIp()
   {
      return ip;
   }
   
   /**
    * Set value of ip
    *
    * @param newIp 
    */
   public void setIp(java.lang.String newIp)
   {
      this.ip = newIp;
   }
   
   /**
    * Get value of time
    *
    * @return time 
    */
   @Basic(optional=true)
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="time", insertable=true, updatable=true)
   public java.util.Date getTime()
   {
      return time;
   }
   
   /**
    * Set value of time
    *
    * @param newTime 
    */
   public void setTime(java.util.Date newTime)
   {
      this.time = newTime;
   }
   
   /**
    * Get value of memo
    *
    * @return memo 
    */
   @Basic(optional=true)
   @Column(name="memo", insertable=true, updatable=true, length=254)
   public java.lang.String getMemo()
   {
      return memo;
   }
   
   /**
    * Set value of memo
    *
    * @param newMemo 
    */
   public void setMemo(java.lang.String newMemo)
   {
      this.memo = newMemo;
   }
   
   /**
    * Get value of userName
    *
    * @return userName 
    */
   @Basic(optional=true)
   @Column(name="user_name", insertable=true, updatable=true, length=20)
   public java.lang.String getUserName()
   {
      return userName;
   }
   
   /**
    * Set value of userName
    *
    * @param newUserName 
    */
   public void setUserName(java.lang.String newUserName)
   {
      this.userName = newUserName;
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object other) {
   
      if (other == null)
         return false;
      
      if (other == this)
         return true;   
   
      if (!(other instanceof LoginLog))
         return false;
   
      LoginLog cast = (LoginLog) other;
   
      
      if (this.getId()==null || cast.getId()==null)
      {
          if(this.getId()==null && cast.getId()==null)
              return true;
          else
              return false;
      }
         
      if (!this.getId().equals(cast.getId()))
          return false;
   
      return true;
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
      int hashCode = 0;
      if (this.password != null) 
         hashCode = 29 * hashCode + password.hashCode();
      if (this.ip != null) 
         hashCode = 29 * hashCode + ip.hashCode();
      if (this.time != null) 
         hashCode = 29 * hashCode + time.hashCode();
      if (this.memo != null) 
         hashCode = 29 * hashCode + memo.hashCode();
      if (this.userName != null) 
         hashCode = 29 * hashCode + userName.hashCode();
      return hashCode;
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
      StringBuffer ret = new StringBuffer();
      ret.append( "com.unlimited.oj.log.LoginLog: " );
      ret.append( "id='" + id + "'");
      ret.append( "password='" + password + "'");
      ret.append( "ip='" + ip + "'");
      ret.append( "time='" + time + "'");
      ret.append( "memo='" + memo + "'");
      ret.append( "userName='" + userName + "'");
      return ret.toString();
   }

}