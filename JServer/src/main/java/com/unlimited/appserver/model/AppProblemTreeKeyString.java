package com.unlimited.appserver.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 
 * 用来存储APP用户需要获取的题目的所有父节点的keystring的值
 * @author 佳洋
 *
 */
@Entity(name = "appProblemTree")
@Table(name = "appProblemTree")
public class AppProblemTreeKeyString {

	private Long id;
	private String keyString;
	
	
	/**
	 * Empty constructor which is required by EJB 3.0 spec.
	 * 
	 */
	public AppProblemTreeKeyString(){
		
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, insertable = true, updatable = true)
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Basic(optional = true)
	@Column(name = "keystring",nullable=false, insertable = true, updatable = true)
	public String getKeyString() {
		return keyString;
	}


	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
	
	
}
