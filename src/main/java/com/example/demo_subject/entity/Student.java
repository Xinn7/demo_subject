package com.example.demo_subject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

	@Id
	@Column(name = "stu_id")
	private String stuId;

	@Column(name = "stu_name")
	private String stuName;

	@Column(name = "sub_number")
	private String subNumber;

	public Student() {

	}

	public Student(String stuId, String stuName) {
		this.stuId = stuId;
		this.stuName = stuName;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getSubNumber() {
		return subNumber;
	}

	public void setSubNumber(String subNamber) {
		this.subNumber = subNamber;
	}

}
