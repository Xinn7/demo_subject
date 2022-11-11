package com.example.demo_subject.vo;

import com.example.demo_subject.entity.Student;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentSelectRes {

	private Student student;

	private String message;

	public StudentSelectRes() {

	}

	public StudentSelectRes(Student student,String message) {
		this.student = student;
		this.message = message;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
