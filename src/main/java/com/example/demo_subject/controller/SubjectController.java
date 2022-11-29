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

	// �إ߽ҵ{
	@PostMapping(value = "/api/creatSubject")
	public SubjectResp creatSubject(@RequestBody SubjAndStuReq req) {
		return subjectService.creatSubject(req.getSubjectId(), req.getSubName(), req.getWeek(), req.getStartTime(),
				req.getEndTime(), req.getUnits());
	}

	// �ק�ҵ{
	@PostMapping(value = "/api/updateSubject")
	public SubjectResp updateSubject(@RequestBody SubjAndStuReq req) {
		return subjectService.updateSubject(req.getSubjectId(), req.getSubName(), req.getWeek(), req.getStartTime(),
				req.getEndTime(), req.getUnits());
	}

	// �̽ҵ{�N�X�j�M
	@PostMapping(value = "/api/searchBySubjId")
	public SubjectResp searchBySubjId(@RequestBody SubjAndStuReq req) {
		return subjectService.searchBySubjId(req.getSubjectId());
	}

	// �̽ҵ{�W�ٷj�M
	@PostMapping(value = "/api/searchBySubjName")
	public SubjectResp searchBySubjName(@RequestBody SubjAndStuReq req) {
		return subjectService.searchBySubjName(req.getSubName());
	}

	// �s�W�ǥ�
	@PostMapping(value = "/api/student")
	public SubjectResp student(@RequestBody SubjAndStuReq req) {
		return subjectService.student(req.getStuId(), req.getStuName());
	}

	// ���
	@PostMapping(value = "/api/selectSubject")
	public SubjectResp selectSubject(@RequestBody SubjAndStuReq req) {
		return subjectService.selectSubject(req.getStuId(), req.getSubIdList());
	}

	// �ǥͤw��W�ҵ{�d��
	@PostMapping(value = "/api/searchSubjByStu")
	public SubjectResp searchSubjByStu(@RequestBody SubjAndStuReq req) {
		return subjectService.searchSubjByStu(req.getStuId());
	}

	// �h��
	@PostMapping(value = "/api/deleteSubByStu")
	public SubjectResp deleteSubByStu(@RequestBody SubjAndStuReq req) {
		return subjectService.deleteSubByStu(req.getStuId(), req.getSubIdList());
	}
}
