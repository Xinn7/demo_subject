package com.example.demo_subject.vo;

import java.util.List;

import com.example.demo_subject.entity.Student;
import com.example.demo_subject.entity.Subject;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchByStuRes {

	private Student student;

	private List<Subject> subList;
	
	private String message;

	public SearchByStuRes() {

	}

	public SearchByStuRes(Student student, List<Subject> subList, String message) {
		this.student = student;
		this.subList = subList;
		this.message = message;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Subject> getSubList() {
		return subList;
	}

	public void setSubList(List<Subject> subList) {
		this.subList = subList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
