package com.example.demo_subject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subject")
public class Subject {

	@Id
	@Column(name = "subject_id")
	private String subjectId;

	@Column(name = "sub_name")
	private String subName;

	@Column(name = "sub_week")
	private int week;

	@Column(name = "start_time")
	private int startTime;

	@Column(name = "end_time")
	private int endTime;

	@Column(name = "units")
	private int units;

	public Subject() {

	}

	public Subject(String subNum, String subName, int week, int startTime, int endTime, int units) {
		this.subjectId = subNum;
		this.subName = subName;
		this.week = week;
		this.startTime = startTime;
		this.endTime = endTime;
		this.units = units;
	}

	public String getSubNum() {
		return subjectId;
	}

	public void setSubNum(String subNum) {
		this.subjectId = subNum;
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
}
