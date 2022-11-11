package com.example.demo_subject.service.ifs;

import java.util.List;
import java.util.Set;

import com.example.demo_subject.vo.SearchByStuRes;
import com.example.demo_subject.vo.StudentSelectRes;
import com.example.demo_subject.vo.SubjectRes;

public interface SubjectService {

	// �إ߽ҵ{
	public SubjectRes creatSubject(String subNum,String subName,int week,int startTime,int endTime,int units);
	
	// �ק�ҵ{
	public SubjectRes updateSubject(String subNum,String subName,int week,int startTime,int endTime,int units);

	// �̽ҵ{�N�X�j�M
	public SubjectRes searchByNum(String subNum);
	
	// �̽ҵ{�W�ٷj�M
	public SubjectRes searchByName(String subName);
	
	// �s�W�ǥ�
	public StudentSelectRes student(String stuId, String stuName);
	
	//���
	public StudentSelectRes selectSubject(String stuId, List<String> subIdList);
	
	//�ǥͤw��W�ҵ{�d��
	public SearchByStuRes searchByStuSub(String stuId);
	
	//�h��
	public StudentSelectRes deleteSub(String stuId,String subNum);
}
