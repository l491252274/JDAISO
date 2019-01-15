/***********************************************************************
 * Module:  Menu.java
 * Author:  develop
 * Purpose: Defines the Class Menu
 ***********************************************************************/

package com.unlimited.oj.model;

import javax.persistence.*;
import com.unlimited.oj.model.*;
import java.util.*;

@Entity(name="Menu")
@Table(name="menu")
public class Menu implements java.io.Serializable {
   private java.lang.Long id;
   private java.lang.Long parentID;
   private java.lang.String accessPath;
   private Boolean checked;
   private int delFlag = 0;
   private String resourceCode;
   private String resourceDesc;
   private int resourceGrade = 0;
   private String resourceName;
   private int resourceOrder = 0;
   private java.lang.String resourceType;
   private java.lang.String author;
   /** 是否可见 */
   private Boolean visible;
   /** 打开该节点需要的keys，多值以;号分隔 */
   private java.lang.String authority;
   
   /**
    * Empty constructor which is required by EJB 3.0 spec.
    *
    */
   public Menu() {
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
    * Get value of parentID
    *
    * @return parentID 
    */
   @Basic(optional=true)
   @Column(name="parentID", insertable=true, updatable=true)
   public java.lang.Long getParentID()
   {
      return parentID;
   }
   
   /**
    * Set value of parentID
    *
    * @param newParentID 
    */
   public void setParentID(java.lang.Long newParentID)
   {
      this.parentID = newParentID;
   }
   
   /**
    * Get value of accessPath
    *
    * @return accessPath 
    */
   @Basic(optional=true)
   @Column(name="accessPath", insertable=true, updatable=true, length=512)
   public java.lang.String getAccessPath()
   {
      return accessPath;
   }
   
   /**
    * Set value of accessPath
    *
    * @param newAccessPath 
    */
   public void setAccessPath(java.lang.String newAccessPath)
   {
      this.accessPath = newAccessPath;
   }
   
   /**
    * Get value of checked
    *
    * @return checked 
    */
   @Basic(optional=true)
   @Column(name="checked", insertable=true, updatable=true)
   public Boolean getChecked()
   {
      return checked;
   }
   
   /**
    * Set value of checked
    *
    * @param newChecked 
    */
   public void setChecked(Boolean newChecked)
   {
      this.checked = newChecked;
   }
   
   /**
    * Get value of delFlag
    *
    * @return delFlag 
    */
   @Basic(optional=true)
   @Column(name="delFlag", insertable=true, updatable=true)
   public int getDelFlag()
   {
      return delFlag;
   }
   
   /**
    * Set value of delFlag
    *
    * @param newDelFlag 
    */
   public void setDelFlag(int newDelFlag)
   {
      this.delFlag = newDelFlag;
   }
   
   /**
    * Get value of resourceCode
    *
    * @return resourceCode 
    */
   @Basic(optional=true)
   @Column(name="resourceCode", insertable=true, updatable=true, length=20)
   public String getResourceCode()
   {
      return resourceCode;
   }
   
   /**
    * Set value of resourceCode
    *
    * @param newResourceCode 
    */
   public void setResourceCode(String newResourceCode)
   {
      this.resourceCode = newResourceCode;
   }
   
   /**
    * Get value of resourceDesc
    *
    * @return resourceDesc 
    */
   @Basic(optional=true)
   @Column(name="resourceDesc", insertable=true, updatable=true, length=80)
   public String getResourceDesc()
   {
      return resourceDesc;
   }
   
   /**
    * Set value of resourceDesc
    *
    * @param newResourceDesc 
    */
   public void setResourceDesc(String newResourceDesc)
   {
      this.resourceDesc = newResourceDesc;
   }
   
   /**
    * Get value of resourceGrade
    *
    * @return resourceGrade 
    */
   @Basic(optional=true)
   @Column(name="resourceGrade", insertable=true, updatable=true)
   public int getResourceGrade()
   {
      return resourceGrade;
   }
   
   /**
    * Set value of resourceGrade
    *
    * @param newResourceGrade 
    */
   public void setResourceGrade(int newResourceGrade)
   {
      this.resourceGrade = newResourceGrade;
   }
   
   /**
    * Get value of resourceName
    *
    * @return resourceName 
    */
   @Basic(optional=true)
   @Column(name="resourceName", insertable=true, updatable=true, length=50)
   public String getResourceName()
   {
      return resourceName;
   }
   
   /**
    * Set value of resourceName
    *
    * @param newResourceName 
    */
   public void setResourceName(String newResourceName)
   {
      this.resourceName = newResourceName;
   }
   
   /**
    * Get value of resourceOrder
    *
    * @return resourceOrder 
    */
   @Basic(optional=true)
   @Column(name="resourceOrder", insertable=true, updatable=true)
   public int getResourceOrder()
   {
      return resourceOrder;
   }
   
   /**
    * Set value of resourceOrder
    *
    * @param newResourceOrder 
    */
   public void setResourceOrder(int newResourceOrder)
   {
      this.resourceOrder = newResourceOrder;
   }
   
   /**
    * Get value of resourceType
    *
    * @return resourceType 
    */
   @Basic(optional=true)
   @Column(name="resourceType", insertable=true, updatable=true, length=20)
   public java.lang.String getResourceType()
   {
      return resourceType;
   }
   
   /**
    * Set value of resourceType
    *
    * @param newResourceType 
    */
   public void setResourceType(java.lang.String newResourceType)
   {
      this.resourceType = newResourceType;
   }
   
   /**
    * Get value of author
    *
    * @return author 
    */
   @Basic(optional=true)
   @Column(name="author", insertable=true, updatable=true, length=20)
   public java.lang.String getAuthor()
   {
      return author;
   }
   
   /**
    * Set value of author
    *
    * @param newAuthor 
    */
   public void setAuthor(java.lang.String newAuthor)
   {
      this.author = newAuthor;
   }
   
   /**
    * Get value of visible
    *
    * @return visible 
    */
   @Basic(optional=true)
   @Column(name="visible", insertable=true, updatable=true)
   public Boolean getVisible()
   {
      return visible;
   }
   
   /**
    * Set value of visible
    *
    * @param newVisible 
    */
   public void setVisible(Boolean newVisible)
   {
      this.visible = newVisible;
   }
   
   /**
    * Get value of authority
    *
    * @return authority 
    */
   @Basic(optional=true)
   @Column(name="authority", insertable=true, updatable=true, length=80)
   public java.lang.String getAuthority()
   {
      return authority;
   }
   
   /**
    * Set value of authority
    *
    * @param newAuthority 
    */
   public void setAuthority(java.lang.String newAuthority)
   {
      this.authority = newAuthority;
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object other) {
   
      if (other == null)
         return false;
      
      if (other == this)
         return true;   
   
      if (!(other instanceof Menu))
         return false;
   
      Menu cast = (Menu) other;
   
      
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
      if (this.parentID != null) 
         hashCode = 29 * hashCode + parentID.hashCode();
      if (this.accessPath != null) 
         hashCode = 29 * hashCode + accessPath.hashCode();
      if (this.checked != null) 
         hashCode = 29 * hashCode + checked.hashCode();
      hashCode = 29 * hashCode + (new Integer(delFlag)).hashCode();
      if (this.resourceCode != null) 
         hashCode = 29 * hashCode + resourceCode.hashCode();
      if (this.resourceDesc != null) 
         hashCode = 29 * hashCode + resourceDesc.hashCode();
      hashCode = 29 * hashCode + (new Integer(resourceGrade)).hashCode();
      if (this.resourceName != null) 
         hashCode = 29 * hashCode + resourceName.hashCode();
      hashCode = 29 * hashCode + (new Integer(resourceOrder)).hashCode();
      if (this.resourceType != null) 
         hashCode = 29 * hashCode + resourceType.hashCode();
      if (this.author != null) 
         hashCode = 29 * hashCode + author.hashCode();
      if (this.visible != null) 
         hashCode = 29 * hashCode + visible.hashCode();
      if (this.authority != null) 
         hashCode = 29 * hashCode + authority.hashCode();
      return hashCode;
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
      StringBuffer ret = new StringBuffer();
      ret.append( "com.unlimited.oj.common.Menu: " );
      ret.append( "id='" + id + "'");
      ret.append( "parentID='" + parentID + "'");
      ret.append( "accessPath='" + accessPath + "'");
      ret.append( "checked='" + checked + "'");
      ret.append( "delFlag='" + delFlag + "'");
      ret.append( "resourceCode='" + resourceCode + "'");
      ret.append( "resourceDesc='" + resourceDesc + "'");
      ret.append( "resourceGrade='" + resourceGrade + "'");
      ret.append( "resourceName='" + resourceName + "'");
      ret.append( "resourceOrder='" + resourceOrder + "'");
      ret.append( "resourceType='" + resourceType + "'");
      ret.append( "author='" + author + "'");
      ret.append( "visible='" + visible + "'");
      ret.append( "authority='" + authority + "'");
      return ret.toString();
   }

}