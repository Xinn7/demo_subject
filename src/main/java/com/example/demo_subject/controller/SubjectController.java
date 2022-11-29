package com.example.demo_subject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_subject.service.ifs.SubjectService;
import com.example.demo_subject.vo.SubjAndStuReq;
import com.example.demo_subject.vo.SubjectResp;

@CrossOrigin
@RestController
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	// 建立課程
	@PostMapping(value = "/api/creatSubject")
	public SubjectResp creatSubject(@RequestBody SubjAndStuReq req) {
		return subjectService.creatSubject(req.getSubjectId(), req.getSubName(), req.getWeek(), req.getStartTime(),
				req.getEndTime(), req.getUnits());
	}

	// 修改課程
	@PostMapping(value = "/api/updateSubject")
	public SubjectResp updateSubject(@RequestBody SubjAndStuReq req) {
		return subjectService.updateSubject(req.getSubjectId(), req.getSubName(), req.getWeek(), req.getStartTime(),
				req.getEndTime(), req.getUnits());
	}

	// 依課程代碼搜尋
	@PostMapping(value = "/api/searchBySubjId")
	public SubjectResp searchBySubjId(@RequestBody SubjAndStuReq req) {
		return subjectService.searchBySubjId(req.getSubjectId());
	}

	// 依課程名稱搜尋
	@PostMapping(value = "/api/searchBySubjName")
	public SubjectResp searchBySubjName(@RequestBody SubjAndStuReq req) {
		return subjectService.searchBySubjName(req.getSubName());
	}

	// 新增學生
	@PostMapping(value = "/api/student")
	public SubjectResp student(@RequestBody SubjAndStuReq req) {
		return subjectService.student(req.getStuId(), req.getStuName());
	}

	// 選課
	@PostMapping(value = "/api/selectSubject")
	public SubjectResp selectSubject(@RequestBody SubjAndStuReq req) {
		return subjectService.selectSubject(req.getStuId(), req.getSubIdList());
	}

	// 學生已選上課程查詢
	@PostMapping(value = "/api/searchSubjByStu")
	public SubjectResp searchSubjByStu(@RequestBody SubjAndStuReq req) {
		return subjectService.searchSubjByStu(req.getStuId());
	}

	// 退選
	@PostMapping(value = "/api/deleteSubByStu")
	public SubjectResp deleteSubByStu(@RequestBody SubjAndStuReq req) {
		return subjectService.deleteSubByStu(req.getStuId(), req.getSubIdList());
	}
}
