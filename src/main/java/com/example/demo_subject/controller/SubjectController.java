package com.example.demo_subject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_subject.service.ifs.SubjectService;
import com.example.demo_subject.vo.SearchByStuRes;
import com.example.demo_subject.vo.StudentSelectRes;
import com.example.demo_subject.vo.SubjectReq;
import com.example.demo_subject.vo.SubjectRes;

@RestController
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	// 建立課程
	@PostMapping(value = "/api/subject")
	public SubjectRes subject(@RequestBody SubjectReq req) {
		return subjectService.creatSubject(req.getSubNum(), req.getSubName(), req.getWeek(), req.getStartTime(),
				req.getEndTime(), req.getUnits());
	}

	// 修改課程
	@PostMapping(value = "/api/updateSubject")
	public SubjectRes updateSubject(@RequestBody SubjectReq req) {
		return subjectService.updateSubject(req.getSubNum(), req.getSubName(), req.getWeek(), req.getStartTime(),
				req.getEndTime(), req.getUnits());
	}

	// 依課程代碼搜尋
	@PostMapping(value = "/api/searchByNum")
	public SubjectRes searchByNum(@RequestBody SubjectReq req) {
		return subjectService.searchByNum(req.getSubNum());
	}

	// 依課程名稱搜尋
	@PostMapping(value = "/api/searchByName")
	public SubjectRes searchByName(@RequestBody SubjectReq req) {
		return subjectService.searchByName(req.getSubName());
	}

	// 新增學生
	@PostMapping(value = "/api/student")
	public StudentSelectRes student(@RequestBody SubjectReq req) {
		return subjectService.student(req.getStuId(), req.getStuName());
	}

	// 選課
	@PostMapping(value = "/api/selectSubject")
	public StudentSelectRes selectSubject(@RequestBody SubjectReq req) {
		return subjectService.selectSubject(req.getStuId(), req.getSubIdList());
	}

	// 學生已選上課程查詢
	@PostMapping(value = "/api/searchByStuSub")
	public SearchByStuRes searchByStuSub(@RequestBody SubjectReq req) {
		return subjectService.searchByStuSub(req.getStuId());
	}

	// 退選
	@PostMapping(value = "/api/deleteSubject")
	public StudentSelectRes deleteSubject(@RequestBody SubjectReq req) {
		return subjectService.deleteSub(req.getStuId(), req.getSubNum());
	}
}
