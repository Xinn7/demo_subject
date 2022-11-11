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

	// �إ߽ҵ{
	@PostMapping(value = "/api/subject")
	public SubjectRes subject(@RequestBody SubjectReq req) {
		return subjectService.creatSubject(req.getSubNum(), req.getSubName(), req.getWeek(), req.getStartTime(),
				req.getEndTime(), req.getUnits());
	}

	// �ק�ҵ{
	@PostMapping(value = "/api/updateSubject")
	public SubjectRes updateSubject(@RequestBody SubjectReq req) {
		return subjectService.updateSubject(req.getSubNum(), req.getSubName(), req.getWeek(), req.getStartTime(),
				req.getEndTime(), req.getUnits());
	}

	// �̽ҵ{�N�X�j�M
	@PostMapping(value = "/api/searchByNum")
	public SubjectRes searchByNum(@RequestBody SubjectReq req) {
		return subjectService.searchByNum(req.getSubNum());
	}

	// �̽ҵ{�W�ٷj�M
	@PostMapping(value = "/api/searchByName")
	public SubjectRes searchByName(@RequestBody SubjectReq req) {
		return subjectService.searchByName(req.getSubName());
	}

	// �s�W�ǥ�
	@PostMapping(value = "/api/student")
	public StudentSelectRes student(@RequestBody SubjectReq req) {
		return subjectService.student(req.getStuId(), req.getStuName());
	}

	// ���
	@PostMapping(value = "/api/selectSubject")
	public StudentSelectRes selectSubject(@RequestBody SubjectReq req) {
		return subjectService.selectSubject(req.getStuId(), req.getSubIdList());
	}

	// �ǥͤw��W�ҵ{�d��
	@PostMapping(value = "/api/searchByStuSub")
	public SearchByStuRes searchByStuSub(@RequestBody SubjectReq req) {
		return subjectService.searchByStuSub(req.getStuId());
	}

	// �h��
	@PostMapping(value = "/api/deleteSubject")
	public StudentSelectRes deleteSubject(@RequestBody SubjectReq req) {
		return subjectService.deleteSub(req.getStuId(), req.getSubNum());
	}
}
