package com.example.demo.entity;

import java.util.LinkedList;
import java.util.List;

public class TransactionPoint {
	List<ListTransactionPoint> data = new LinkedList<>();
    String response;
    String status;

	public List<ListTransactionPoint> getData() {
		return data;
	}

	public void setData(List<ListTransactionPoint> data) {
		this.data = data;
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
