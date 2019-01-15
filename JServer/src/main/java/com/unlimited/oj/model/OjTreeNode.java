/***********************************************************************
 * Module:  OjTreeNode.java
 * Author:  develop
 * Purpose: Defines the Class OjTreeNode
 ***********************************************************************/

package com.unlimited.oj.model;

import javax.persistence.*;
import com.unlimited.oj.model.*;
import java.util.*;

@Entity(name="OjTreeNode")
@Table(name="oj_tree_node")
public class OjTreeNode implements com.unlimited.oj.model.IOwner, java.io.Serializable {
   private java.lang.Long id;
   /** 其值为-1的表示为根结点 */
   private java.lang.Long pid;
   private String name;
   private String url;
   /** 小于100的为目录节点，大于等于100的为叶子节点
    * 目录节点有：
    * 1一般目录项；
    * 
    * 叶子节点有：
    * 100题目叶子 */
   private int type;
   private String description;
   private String title;
   private String target;
   private String icon;
   private String iconopen;
   /** 是否被打开（否则仍处于加锁状态，该项不保存到数据库） */
   private int opened;
   private int orderNum;
   /** 为连接点位置（用URL最后一段表示），全为小写 */
   private java.lang.String connectPointer;
   /** 当为题目叶子结点时，为对应的题目编号 */
   private java.lang.Long problemId;
   /** 做签字（一般使用用户帐号） */
   private java.lang.String sign = null;
   private java.lang.String author;
   /** 打开该节点需要的keys，多值以;号分隔 */
   private java.lang.String doors;
   /** 该项是否可以被看见 */
   private boolean visible;
   
   /**
    * Empty constructor which is required by EJB 3.0 spec.
    *
    */
   public OjTreeNode() {
      // TODO Add your own initialization code here.
   }
   
   /** @param username */
   public boolean isOwner(java.lang.String username) {
      if(username == null || sign == null)
         return false;
      return sign.equalsIgnoreCase(username);
   }
   
   /** @param username */
   public boolean isAuthor(java.lang.String username) {
           if(author!=null && author.equalsIgnoreCase(username))
               return true;
           else
               return false;
   
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
    * Get value of pid
    *
    * @return pid 
    */
   @Basic(optional=true)
   @Column(name="pid", insertable=true, updatable=true)
   public java.lang.Long getPid()
   {
      return pid;
   }
   
   /**
    * Set value of pid
    *
    * @param newPid 
    */
   public void setPid(java.lang.Long newPid)
   {
      this.pid = newPid;
   }
   
   /**
    * Get value of name
    *
    * @return name 
    */
   @Basic(optional=true)
   @Column(name="name", insertable=true, updatable=true, length=50)
   public String getName()
   {
      return name;
   }
   
   /**
    * Set value of name
    *
    * @param newName 
    */
   public void setName(String newName)
   {
      this.name = newName;
   }
   
   /**
    * Get value of url
    *
    * @return url 
    */
   @Basic(optional=true)
   @Column(name="url", insertable=true, updatable=true, length=512)
   public String getUrl()
   {
      return url;
   }
   
   /**
    * Set value of url
    *
    * @param newUrl 
    */
   public void setUrl(String newUrl)
   {
      this.url = newUrl;
   }
   
   /**
    * Get value of type
    *
    * @return type 
    */
   @Basic(optional=true)
   @Column(name="type", insertable=true, updatable=true)
   public int getType()
   {
      return type;
   }
   
   /**
    * Set value of type
    *
    * @param newType 
    */
   public void setType(int newType)
   {
      this.type = newType;
   }
   
   /**
    * Get value of description
    *
    * @return description 
    */
   @Basic(optional=true)
   @Lob
   @Column(name="description", insertable=true, updatable=true)
   public String getDescription()
   {
      return description;
   }
   
   /**
    * Set value of description
    *
    * @param newDescription 
    */
   public void setDescription(String newDescription)
   {
      this.description = newDescription;
   }
   
   /**
    * Get value of title
    *
    * @return title 
    */
   @Basic(optional=true)
   @Column(name="title", insertable=true, updatable=true, length=50)
   public String getTitle()
   {
      return title;
   }
   
   /**
    * Set value of title
    *
    * @param newTitle 
    */
   public void setTitle(String newTitle)
   {
      this.title = newTitle;
   }
   
   /**
    * Get value of target
    *
    * @return target 
    */
   @Basic(optional=true)
   @Column(name="target", insertable=true, updatable=true, length=20)
   public String getTarget()
   {
      return target;
   }
   
   /**
    * Set value of target
    *
    * @param newTarget 
    */
   public void setTarget(String newTarget)
   {
      this.target = newTarget;
   }
   
   /**
    * Get value of icon
    *
    * @return icon 
    */
   @Basic(optional=true)
   @Column(name="icon", insertable=true, updatable=true, length=20)
   public String getIcon()
   {
      return icon;
   }
   
   /**
    * Set value of icon
    *
    * @param newIcon 
    */
   public void setIcon(String newIcon)
   {
      this.icon = newIcon;
   }
   
   /**
    * Get value of iconopen
    *
    * @return iconopen 
    */
   @Basic(optional=true)
   @Column(name="iconopen", insertable=true, updatable=true, length=20)
   public String getIconopen()
   {
      return iconopen;
   }
   
   /**
    * Set value of iconopen
    *
    * @param newIconopen 
    */
   public void setIconopen(String newIconopen)
   {
      this.iconopen = newIconopen;
   }
   
   /**
    * Get value of opened
    *
    * @return opened 
    */
   @Transient
   public int getOpened()
   {
      return opened;
   }
   
   /**
    * Set value of opened
    *
    * @param newOpened 
    */
   public void setOpened(int newOpened)
   {
      this.opened = newOpened;
   }
   
   /**
    * Get value of orderNum
    *
    * @return orderNum 
    */
   @Basic(optional=true)
   @Column(name="orderNum", insertable=true, updatable=true)
   public int getOrderNum()
   {
      return orderNum;
   }
   
   /**
    * Set value of orderNum
    *
    * @param newOrderNum 
    */
   public void setOrderNum(int newOrderNum)
   {
      this.orderNum = newOrderNum;
   }
   
   /**
    * Get value of connectPointer
    *
    * @return connectPointer 
    */
   @Basic(optional=true)
   @Column(name="connect_pointer", insertable=true, updatable=true, length=100)
   public java.lang.String getConnectPointer()
   {
      return connectPointer;
   }
   
   /**
    * Set value of connectPointer
    *
    * @param newConnectPointer 
    */
   public void setConnectPointer(java.lang.String newConnectPointer)
   {
      this.connectPointer = newConnectPointer;
   }
   
   /**
    * Get value of problemId
    *
    * @return problemId 
    */
   @Basic(optional=true)
   @Column(name="problem_id", insertable=true, updatable=true)
   public java.lang.Long getProblemId()
   {
      return problemId;
   }
   
   /**
    * Set value of problemId
    *
    * @param newProblemId 
    */
   public void setProblemId(java.lang.Long newProblemId)
   {
      this.problemId = newProblemId;
   }
   
   /**
    * Get value of sign
    *
    * @return sign 
    */
   @Basic(optional=true)
   @Column(name="sign", insertable=true, updatable=true, length=20)
   public java.lang.String getSign()
   {
      return sign;
   }
   
   /**
    * Set value of sign
    *
    * @param newSign 
    */
   public void setSign(java.lang.String newSign)
   {
      this.sign = newSign;
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
    * Get value of doors
    *
    * @return doors 
    */
   @Basic(optional=true)
   @Column(name="doors", insertable=true, updatable=true, length=80)
   public java.lang.String getDoors()
   {
      return doors;
   }
   
   /**
    * Set value of doors
    *
    * @param newDoors 
    */
   public void setDoors(java.lang.String newDoors)
   {
      this.doors = newDoors;
   }
   
   /**
    * Get value of visible
    *
    * @return visible 
    */
   @Basic(optional=true)
   @Column(name="visible", insertable=true, updatable=true)
   public boolean getVisible()
   {
      return visible;
   }
   
   /**
    * Set value of visible
    *
    * @param newVisible 
    */
   public void setVisible(boolean newVisible)
   {
      this.visible = newVisible;
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object other) {
   
      if (other == null)
         return false;
      
      if (other == this)
         return true;   
   
      if (!(other instanceof OjTreeNode))
         return false;
   
      OjTreeNode cast = (OjTreeNode) other;
   
      
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
      if (this.pid != null) 
         hashCode = 29 * hashCode + pid.hashCode();
      if (this.name != null) 
         hashCode = 29 * hashCode + name.hashCode();
      if (this.url != null) 
         hashCode = 29 * hashCode + url.hashCode();
      hashCode = 29 * hashCode + (new Integer(type)).hashCode();
      if (this.description != null) 
         hashCode = 29 * hashCode + description.hashCode();
      if (this.title != null) 
         hashCode = 29 * hashCode + title.hashCode();
      if (this.target != null) 
         hashCode = 29 * hashCode + target.hashCode();
      if (this.icon != null) 
         hashCode = 29 * hashCode + icon.hashCode();
      if (this.iconopen != null) 
         hashCode = 29 * hashCode + iconopen.hashCode();
      hashCode = 29 * hashCode + (new Integer(orderNum)).hashCode();
      if (this.connectPointer != null) 
         hashCode = 29 * hashCode + connectPointer.hashCode();
      if (this.problemId != null) 
         hashCode = 29 * hashCode + problemId.hashCode();
      if (this.sign != null) 
         hashCode = 29 * hashCode + sign.hashCode();
      if (this.author != null) 
         hashCode = 29 * hashCode + author.hashCode();
      if (this.doors != null) 
         hashCode = 29 * hashCode + doors.hashCode();
      hashCode = 29 * hashCode + (new Boolean(visible)).hashCode();
      return hashCode;
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
      StringBuffer ret = new StringBuffer();
      ret.append( "com.unlimited.oj.common.OjTreeNode: " );
      ret.append( "id='" + id + "'");
      ret.append( "pid='" + pid + "'");
      ret.append( "name='" + name + "'");
      ret.append( "url='" + url + "'");
      ret.append( "type='" + type + "'");
      ret.append( "description='" + description + "'");
      ret.append( "title='" + title + "'");
      ret.append( "target='" + target + "'");
      ret.append( "icon='" + icon + "'");
      ret.append( "iconopen='" + iconopen + "'");
      ret.append( "opened='" + opened + "'");
      ret.append( "orderNum='" + orderNum + "'");
      ret.append( "connectPointer='" + connectPointer + "'");
      ret.append( "problemId='" + problemId + "'");
      ret.append( "sign='" + sign + "'");
      ret.append( "author='" + author + "'");
      ret.append( "doors='" + doors + "'");
      ret.append( "visible='" + visible + "'");
      return ret.toString();
   }

}