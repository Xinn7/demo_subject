package com.example.demo_subject.vo;

import java.util.List;

public class SubjAndStuReq {

	private String subjectId;

	private String subName;

	private int week;

	private int startTime;

	private int endTime;

	private int units;
	
	private String stuId;
	
	private String stuName;
	
	private List<String>subIdList;
	
	public SubjAndStuReq() {
		
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
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

	public List<String> getSubIdList() {
		return subIdList;
	}

	public void setSubIdList(List<String> subIdList) {
		this.subIdList = subIdList;
	}
}
