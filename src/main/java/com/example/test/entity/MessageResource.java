/*
 * Copyright (c) 2017, HSBC. All rights reserved.
 */
package com.example.test.entity;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;


/**
 * 
 *
 * @author A-team WeiHeng
 * @date 2017-09-28
 */
@Entity
public class MessageResource{

	@javax.persistence.Id
	@GeneratedValue
	private Integer id;

	private String name;

	private String value;

	private String locale;
	
	

	public MessageResource() {
		super();
	}



	public MessageResource(String name, String value, String locale) {
		super();
		this.name = name;
		this.value = value;
		this.locale = locale;
	}



	public MessageResource(Integer id, String name, String value, String locale) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.locale = locale;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public String getLocale() {
		return locale;
	}



	public void setLocale(String locale) {
		this.locale = locale;
	}



	@Override
	public String toString() {
		return "MessageResource [id=" + id + ", name=" + name + ", value=" + value + ", locale=" + locale + "]";
	}

	
}
