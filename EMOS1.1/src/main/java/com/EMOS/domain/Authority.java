package com.EMOS.domain;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Authority implements GrantedAuthority{

	private static final long serialVersionUID = 4739949984675721546L;
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String authority;
	@ManyToOne
	@JsonIgnore
	private User user;
	
	public Authority() {}
	
	public Authority (String authority) {
		this.authority = authority;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
