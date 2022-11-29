package com.example.demo_subject.service.ifs;

import java.util.List;

import com.example.demo_subject.vo.SubjectResp;

public interface SubjectService {

	// �إ߽ҵ{
	public SubjectResp creatSubject(String subjectId,String subName,int week,int startTime,int endTime,int units);
	
	// �ק�ҵ{
	public SubjectResp updateSubject(String subjectId,String subName,int week,int startTime,int endTime,int units);

	// �̽ҵ{�N�X�j�M
	public SubjectResp searchBySubjId(String subjectId);
	
	// �̽ҵ{�W�ٷj�M
	public SubjectResp searchBySubjName(String subName);
	
	// �s�W�ǥ�
	public SubjectResp student(String stuId, String stuName);
	
	//���
	public SubjectResp selectSubject(String stuId, List<String> subjectIdList);
	
	//�ǥͤw��W�ҵ{�d��
	public SubjectResp searchSubjByStu(String stuId);
	
	//�h�� TODO �h��ҵ{����List
	public SubjectResp deleteSubByStu(String stuId,List<String> subjectIdList);
}
