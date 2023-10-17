package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "nasabah")
public class Nasabah {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private long accountId;
	
	@NotNull
	private String name;
	
	 @Transient
	    private String response;
	    
	    @Transient
	    private String status;
	
	public Nasabah() {
        // Default constructor
    }
	
	 public Nasabah(String name) {
	        this.name = name;
	    }

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
