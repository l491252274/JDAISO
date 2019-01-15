package com.unlimited.appserver.model;

import javax.persistence.*;


/**
 * This class represent the basic "appuser" object in AppFuse that allows for
 * authentication. and appuser management.
 */
@Entity(name = "appuser")
@Table(name = "appuser")
public class AppUser implements java.io.Serializable {

	private static final long serialVersionUID = 6898373021232076302L;

	/** 维一ID */
	private Long id;
	private String account;
	private String fullname;
	private String password;
	private String token;
	private String mail;
	private String nickname;

	/**
	 * Empty constructor which is required by EJB 3.0 spec.
	 * 
	 */
	public AppUser() {

	}

	/**
	 * set value of id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * Get value of id
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, insertable = true, updatable = true)
	public Long getId() {
		return id;
	}

	/**
	 * set value of account
	 * 
	 * @param account
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * get value of account
	 * 
	 * @return account
	 */
	@Basic(optional = true)
	@Column(name = "account",nullable=false, insertable = true, updatable = true, length = 20)
	public String getAccount() {
		return account;
	}

	/**
	 * get value of passoword
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * set value of password
	 * 
	 * @return password
	 */
	@Basic(optional = true)
	@Column(name = "password",nullable=false, insertable = true, updatable = true, length = 100)
	public String getPassword() {
		return password;
	}

	/**
	 * set value of token
	 * 
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * get value of token
	 * 
	 * @return token
	 */
	@Basic(optional = true)
	@Column(name = "token", insertable = true, updatable = true, length = 40)
	public String getToken() {
		return token;
	}

	/**
	 * set value of mail
	 * 
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * get value of mail
	 * 
	 * @return mail
	 */
	@Basic(optional = true)
	@Column(name = "mail",nullable=true, insertable = true, updatable = true, length = 100)
	public String getMail() {
		return mail;
	}

	/**
	 * set value of nickname
	 * 
	 * @param nickName
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * get value of nickname
	 * 
	 * @return nickname
	 */
	@Basic(optional = true)
	@Column(name = "nickname",nullable=false, insertable = true, updatable = true, length = 20)
	public String getNickname() {
		return nickname;
	}
	

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Basic(optional = true)
	@Column(name = "fullname",nullable=false, insertable = true, updatable = true, length = 20)
	public String getFullname() {
		return fullname;
	}

}