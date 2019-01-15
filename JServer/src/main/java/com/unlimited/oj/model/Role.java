/***********************************************************************
 * Module:  Role.java
 * Author:  benQ
 * Purpose: Defines the Class Role
 ***********************************************************************/

package com.unlimited.oj.model;

import javax.persistence.*;

import org.springframework.security.GrantedAuthority;

@Entity(name = "Role")
@Table(name = "roles")
public class Role implements GrantedAuthority, java.io.Serializable {

	private static final long serialVersionUID = -8731451791980562630L;
	private java.lang.Long id;
	private java.lang.String description;
	private java.lang.String name;

	private java.util.List<User> userList = null;

	/**
	 * @pdGenerated default getter
	 */
	@ManyToMany(mappedBy = "roleList")
	public java.util.List<User> getUserList() {
		if (userList == null)
			userList = new java.util.ArrayList<User>();
		return userList;
	}

	/**
	 * @pdGenerated default iterator getter
	 */
	@Transient
	public java.util.Iterator getIteratorUserList() {
		if (userList == null)
			userList = new java.util.ArrayList<User>();
		return userList.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newUserList
	 */
	public void setUserList(java.util.List<User> newUserList) {
		// removeAllUserList();
		this.userList = newUserList;
	}

	/**
	 * @pdGenerated default add
	 * @param newUser
	 */
	public void addUserList(User newUser) {
		if (newUser == null)
			return;
		if (this.userList == null)
			this.userList = new java.util.ArrayList<User>();
		if (!this.userList.contains(newUser)) {
			this.userList.add(newUser);
			newUser.addRoleList(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldUser
	 */
	public void removeUserList(User oldUser) {
		if (oldUser == null)
			return;
		if (this.userList != null)
			if (this.userList.contains(oldUser)) {
				this.userList.remove(oldUser);
				oldUser.removeRoleList(this);
			}
	}

	/**
	 * @pdGenerated default removeAll
	 */
	public void removeAllUserList() {
		if (userList != null) {
			User oldUser;
			for (java.util.Iterator iter = getIteratorUserList(); iter
					.hasNext();) {
				oldUser = (User) iter.next();
				iter.remove();
				oldUser.removeRoleList(this);
			}
		}
	}

	@Transient
	public java.lang.String getAuthority() {
		return getName();
	}

	/** @param o */
	@Transient
	public int compareTo(Object o) {
		return (equals(o) ? 0 : -1);
	}

	/** @param name */
	public Role(java.lang.String name) {
		this.setName(name);
	}

	public Role() {
		// TODO: implement
	}

	/**
	 * Get value of id
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, insertable = true, updatable = true)
	public java.lang.Long getId() {
		return id;
	}

	/**
	 * Set value of id
	 * 
	 * @param newId
	 */
	public void setId(java.lang.Long newId) {
		this.id = newId;
	}

	/**
	 * Get value of description
	 * 
	 * @return description
	 */
	@Basic(optional = true)
	@Column(name = "description", insertable = true, updatable = true, length = 64)
	public java.lang.String getDescription() {
		return description;
	}

	/**
	 * Set value of description
	 * 
	 * @param newDescription
	 */
	public void setDescription(java.lang.String newDescription) {
		this.description = newDescription;
	}

	/**
	 * Get value of name
	 * 
	 * @return name
	 */
	@Basic(optional = true)
	@Column(name = "name", insertable = true, updatable = true, length = 20)
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Set value of name
	 * 
	 * @param newName
	 */
	public void setName(java.lang.String newName) {
		this.name = newName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {

		if (other == null)
			return false;

		if (other == this)
			return true;

		if (!(other instanceof Role))
			return false;

		Role cast = (Role) other;

		if (this.getId() == null || cast.getId() == null) {
			if (this.getId() == null && cast.getId() == null)
				return true;
			else
				return false;
		}

		if (!this.getId().equals(cast.getId()))
			return false;

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int hashCode = 0;
		if (this.description != null)
			hashCode = 29 * hashCode + description.hashCode();
		if (this.name != null)
			hashCode = 29 * hashCode + name.hashCode();
		return hashCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.unlimited.oj.common.user.Role: ");
		ret.append("id='" + id + "'");
		ret.append("description='" + description + "'");
		ret.append("name='" + name + "'");
		return ret.toString();
	}

}