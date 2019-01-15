/***********************************************************************
 * Module:  Attachment.java
 * Author:  benQ
 * Purpose: Defines the Class Attachment
 ***********************************************************************/

package com.unlimited.oj.model;

import javax.persistence.*;
import com.unlimited.oj.model.*;
import java.util.*;

@Entity(name="Attachment")
@Table(name="Attachment")
public class Attachment implements java.io.Serializable {
   private Long id;
   private java.sql.Blob image;
   
   /**
    * Empty constructor which is required by EJB 3.0 spec.
    *
    */
   public Attachment() {
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
   public Long getId()
   {
      return id;
   }
   
   /**
    * Set value of id
    *
    * @param newId 
    */
   public void setId(Long newId)
   {
      this.id = newId;
   }
   
   /**
    * Get value of image
    *
    * @return image 
    */
   @Basic(optional=true)
   @Column(name="image", insertable=true, updatable=true)
   public java.sql.Blob getImage()
   {
      return image;
   }
   
   /**
    * Set value of image
    *
    * @param newImage 
    */
   public void setImage(java.sql.Blob newImage)
   {
      this.image = newImage;
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object other) {
   
      if (other == null)
         return false;
      
      if (other == this)
         return true;   
   
      if (!(other instanceof Attachment))
         return false;
   
      Attachment cast = (Attachment) other;
   
      
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
      if (this.image != null) 
         hashCode = 29 * hashCode + image.hashCode();
      return hashCode;
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
      StringBuffer ret = new StringBuffer();
      ret.append( "com.unlimited.oj.common.resource.Attachment: " );
      ret.append( "id='" + id + "'");
      ret.append( "image='" + image + "'");
      return ret.toString();
   }

}