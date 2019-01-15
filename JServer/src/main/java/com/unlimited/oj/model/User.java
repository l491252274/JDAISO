package com.unlimited.oj.model;

import javax.persistence.*;
import com.unlimited.oj.model.*;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** This class represents the basic "user" object in AppFuse that allows for authentication
 * and user management.  It implements Acegi Security's UserDetails interface.
 * 
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Updated by Dan Kibler (dan@getrolling.com)
 *         Extended to implement Acegi UserDetails interface
 *         by David Carter david@carter.net */
@Entity(name="User")
@Table(name="users")
public class User implements UserDetails, java.io.Serializable {
   /** 维一ID */
   private java.lang.Long id;
   /** 用户名 */
   private java.lang.String username;
   /** 密码 */
   private java.lang.String password;
   /** 密码确认 */
   private java.lang.String confirmPassword;
   private java.lang.String oldPassword;
   /** 姓 */
   private String firstName;
   /** 名 */
   private String lastName;
   /** 电子邮件 */
   private java.lang.String email;
   /** 电话号码 */
   private String phoneNumber;
   /** 激活标志 */
   private java.lang.Boolean enabled = false;
   /** 帐号过期标志 */
   private java.lang.Boolean accountExpired = false;
   /** 帐号锁定 */
   private java.lang.Boolean accountLocked = false;
   /** 信用过期 */
   private java.lang.Boolean credentialsExpired = false;
   
   private java.util.List<Role> roleList = null;
   
   
   /**
    * @pdGenerated default getter
    */
   @ManyToMany(fetch=FetchType.EAGER)
   @JoinTable(
      name="users_vs_roles",
      joinColumns={
         @JoinColumn(name="user_id", referencedColumnName="id", nullable=false)
      },
      inverseJoinColumns={
         @JoinColumn(name="role_id", referencedColumnName="id", nullable=false)
      }
   )
   public java.util.List<Role> getRoleList() {
      if (roleList == null)
         roleList = new java.util.ArrayList<Role>();
      return roleList;
   }
   
   /**
    * @pdGenerated default iterator getter
    */
   @Transient
   public java.util.Iterator getIteratorRoleList() {
      if (roleList == null)
         roleList = new java.util.ArrayList<Role>();
      return roleList.iterator();
   }
   
   /** 
    * @pdGenerated default setter
    * @param newRoleList
    */
   public void setRoleList(java.util.List<Role> newRoleList) {
      //removeAllRoleList();
      this.roleList = newRoleList;   
   }
   
   /** 
    * @pdGenerated default add
    * @param newRole
    */
   public void addRoleList(Role newRole) {
      if (newRole == null)
         return;
      if (this.roleList == null)
         this.roleList = new java.util.ArrayList<Role>();
      if (!this.roleList.contains(newRole))
      {
         this.roleList.add(newRole);
         newRole.addUserList(this);
      }
   }
   
   /** 
    * @pdGenerated default remove
    * @param oldRole
    */
   public void removeRoleList(Role oldRole) {
      if (oldRole == null)
         return;
      if (this.roleList != null)
         if (this.roleList.contains(oldRole))
         {
            this.roleList.remove(oldRole);
            oldRole.removeUserList(this);
         }
   }
   
   /**
    * @pdGenerated default removeAll
    */
   public void removeAllRoleList() {
      if (roleList != null)
      {
         Role oldRole;
         for (java.util.Iterator iter = getIteratorRoleList(); iter.hasNext();)
         {
            oldRole = (Role)iter.next();
            iter.remove();
            oldRole.removeUserList(this);
         }
      }
   }

  
   /**
    * Empty constructor which is required by EJB 3.0 spec.
    *
    */
   public User() {
      // TODO Add your own initialization code here.
   }
   
   @Transient
   public boolean isAccountNonExpired() {
      // TODO: implement
      return true;
   }
   
   @Transient
   public boolean isAccountNonLocked() {
      // TODO: implement
      return true;
   }
   
   @Transient
   public boolean isCredentialsNonExpired() {
      // TODO: implement
      return true;
   }
   
   @Transient
   public boolean isEnabled() {
      // TODO: implement
      return enabled;
   }
   
   @Transient
   public GrantedAuthority[] getAuthorities() {
      return roleList.toArray(new GrantedAuthority[0]);
   }
   
   @Transient
   public java.lang.String getFullName() {
         return getLastName() + getFirstName();
   }
   
   @Transient
   public boolean isAdministrator() {
   	   for(Role role: getRoleList())
   	   {
   		   if(role.getName().equals("ROLE_ADMIN"))
   			   return true;
   	   }
   	   return false;
   }
   
   @Transient
   public boolean isContestAdministrator() {
      for(Role role: getRoleList())
      {
   	   if(role.getName().equals("ROLE_ADMINCONTEST"))
   		   return true;
      }
      return false;
   }
   
   @Transient
   public boolean isExerciseAdministrator() {
      for(Role role: getRoleList())
      {
   	   if(role.getName().equals("ROLE_ADMINEXERCISE"))
   		   return true;
      }
      return false;
   }
   
   @Transient
   public boolean isExamAdministrator() {
      for(Role role: getRoleList())
      {
   	   if(role.getName().equals("ROLE_ADMINEXAM"))
   		   return true;
      }
      return false;
   }
   
   @Transient
   public boolean isObserver() {
      for(Role role: getRoleList())
      {
   	   if(role.getName().equals("ROLE_OBSERVER"))
   		   return true;
      }
      return false;
   }

   /** @param title */
   public String encodeHtml(String title) {
           if (title == null)
               return "&nbsp;";
           StringBuffer stringbuffer = new StringBuffer();
           for (int i = 0; i < title.length(); i++)
           {
               char ch = title.charAt(i);
               if (ch == '<')
               {
                   stringbuffer.append("&lt;");
                   continue;
               }
               if (ch == '>')
               {
                   stringbuffer.append("&gt;");
                   continue;
               }
               if (ch == '&')
               {
                   stringbuffer.append("&#38;");
                   continue;
               }
               if (ch == '"')
                   stringbuffer.append("&#34;");
               else
                   stringbuffer.append(ch);
           }
   
           return stringbuffer.toString();
   }
  
   @Transient
   public String getHtmlFullname() {
      if(firstName==null && lastName==null)
         return "&nbsp;";
      else if(firstName!=null && lastName==null)
         return encodeHtml(firstName);
      else if(firstName==null && lastName!=null)
         return encodeHtml(lastName);
      else 
         return encodeHtml(firstName+lastName);
   }
   
   @Transient
   public boolean isAcmer() {
      for(Role role: getRoleList())
      {
   	   if(role.getName().equals("ROLE_ACMER"))
   		   return true;
      }
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
    * Get value of username
    *
    * @return username 
    */
   @Basic(optional=false)
   @Column(name="user_name", nullable=false, insertable=true, updatable=true, length=20)
   public java.lang.String getUsername()
   {
      return username;
   }
   
   /**
    * Set value of username
    *
    * @param newUsername 
    */
   public void setUsername(java.lang.String newUsername)
   {
      this.username = newUsername;
   }
   
   
   /**
    * Get value of password
    *
    * @return password 
    */
   @Basic(optional=false)
   @Column(name="password", nullable=false, insertable=true, updatable=true, length=80)
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
    * Get value of confirmPassword
    *
    * @return confirmPassword 
    */
   @Transient
   public java.lang.String getConfirmPassword()
   {
      return confirmPassword;
   }
   
   /**
    * Set value of confirmPassword
    *
    * @param newConfirmPassword 
    */
   public void setConfirmPassword(java.lang.String newConfirmPassword)
   {
      this.confirmPassword = newConfirmPassword;
   }
   
   /**
    * Get value of oldPassword
    *
    * @return oldPassword 
    */
   @Transient
   public java.lang.String getOldPassword()
   {
      return oldPassword;
   }
   
   /**
    * Set value of oldPassword
    *
    * @param newOldPassword 
    */
   public void setOldPassword(java.lang.String newOldPassword)
   {
      this.oldPassword = newOldPassword;
   }
   
   
   /**
    * Get value of firstName
    *
    * @return firstName 
    */
   @Basic(optional=true)
   @Column(name="firstName", insertable=true, updatable=true, length=20)
   public String getFirstName()
   {
      return firstName;
   }
   
   /**
    * Set value of firstName
    *
    * @param newFirstName 
    */
   public void setFirstName(String newFirstName)
   {
      this.firstName = newFirstName;
   }
   
   /**
    * Get value of lastName
    *
    * @return lastName 
    */
   @Basic(optional=true)
   @Column(name="lastName", insertable=true, updatable=true, length=20)
   public String getLastName()
   {
      return lastName;
   }
   
   /**
    * Set value of lastName
    *
    * @param newLastName 
    */
   public void setLastName(String newLastName)
   {
      this.lastName = newLastName;
   }
   
   /**
    * Get value of email
    *
    * @return email 
    */
   @Basic(optional=true)
   @Column(name="email", insertable=true, updatable=true, length=100)
   public java.lang.String getEmail()
   {
      return email;
   }
   
   /**
    * Set value of email
    *
    * @param newEmail 
    */
   public void setEmail(java.lang.String newEmail)
   {
      this.email = newEmail;
   }
   
   /**
    * Get value of phoneNumber
    *
    * @return phoneNumber 
    */
   @Basic(optional=true)
   @Column(name="phoneNumber", insertable=true, updatable=true, length=20)
   public String getPhoneNumber()
   {
      return phoneNumber;
   }
   
   /**
    * Set value of phoneNumber
    *
    * @param newPhoneNumber 
    */
   public void setPhoneNumber(String newPhoneNumber)
   {
      this.phoneNumber = newPhoneNumber;
   }
   
   /**
    * Get value of enabled
    *
    * @return enabled 
    */
   @Basic(optional=true)
   @Column(name="enabled", insertable=true, updatable=true)
   public java.lang.Boolean getEnabled()
   {
      return enabled;
   }
   
   /**
    * Set value of enabled
    *
    * @param newEnabled 
    */
   public void setEnabled(java.lang.Boolean newEnabled)
   {
      this.enabled = newEnabled;
   }
   
   /**
    * Get value of accountExpired
    *
    * @return accountExpired 
    */
   @Basic(optional=true)
   @Column(name="accountExpired", insertable=true, updatable=true)
   public java.lang.Boolean getAccountExpired()
   {
      return accountExpired;
   }
   
   /**
    * Set value of accountExpired
    *
    * @param newAccountExpired 
    */
   public void setAccountExpired(java.lang.Boolean newAccountExpired)
   {
      this.accountExpired = newAccountExpired;
   }
   
   /**
    * Get value of accountLocked
    *
    * @return accountLocked 
    */
   @Basic(optional=true)
   @Column(name="accountLocked", insertable=true, updatable=true)
   public java.lang.Boolean getAccountLocked()
   {
      return accountLocked;
   }
   
   /**
    * Set value of accountLocked
    *
    * @param newAccountLocked 
    */
   public void setAccountLocked(java.lang.Boolean newAccountLocked)
   {
      this.accountLocked = newAccountLocked;
   }
   
   /**
    * Get value of credentialsExpired
    *
    * @return credentialsExpired 
    */
   @Basic(optional=true)
   @Column(name="credentialsExpired", insertable=true, updatable=true)
   public java.lang.Boolean getCredentialsExpired()
   {
      return credentialsExpired;
   }
   
   /**
    * Set value of credentialsExpired
    *
    * @param newCredentialsExpired 
    */
   public void setCredentialsExpired(java.lang.Boolean newCredentialsExpired)
   {
      this.credentialsExpired = newCredentialsExpired;
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object other) {
   
      if (other == null)
         return false;
      
      if (other == this)
         return true;   
   
      if (!(other instanceof User))
         return false;
   
      User cast = (User) other;
   
      
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
}