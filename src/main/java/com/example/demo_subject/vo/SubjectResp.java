package com.example.demo_subject.vo;

import java.util.List;

import com.example.demo_subject.entity.Student;
import com.example.demo_subject.entity.Subject;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubjectResp {

	private Student student;

	private Subject subject;

	private List<Subject> subList;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int totalUnits;

	private String message;

	public SubjectResp() {

	}
	
	public SubjectResp(String message, Student student) {
		this.message = message;
		this.student = student;
	}

	public SubjectResp(Subject subject, String message) {
		this.subject = subject;
		this.message = message;
	}

	public SubjectResp(Student student, int totalUnits, String message) {
		this.student = student;
		this.message = message;
		this.totalUnits = totalUnits;
	}

	public SubjectResp(Student student, List<Subject> subList, String message) {
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

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public List<Subject> getSubList() {
		return subList;
	}

	public void setSubList(List<Subject> subList) {
		this.subList = subList;
	}

	public int getTotalUnits() {
		return totalUnits;
	}

	public void setTotalUnits(int totalUnits) {
		this.totalUnits = totalUnits;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
