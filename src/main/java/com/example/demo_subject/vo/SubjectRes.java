package com.example.demo_subject.vo;

import java.util.List;

import com.example.demo_subject.entity.Subject;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubjectRes {

	private Subject subject;

	private String message;
	
	private List<Subject> subList;

	public SubjectRes() {

	}
	
	public SubjectRes(Subject subject,String message) {
		this.subject = subject;
		this.message = message;
	}

	public SubjectRes(String message,List<Subject> subList) {
		this.message = message;
		this.subList = subList;
	}
	
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Subject> getSubList() {
		return subList;
	}

	public void setSubList(List<Subject> subList) {
		this.subList = subList;
	}

}
