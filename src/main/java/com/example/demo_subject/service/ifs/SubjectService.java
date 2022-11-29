package com.example.demo_subject.service.ifs;

import java.util.List;

import com.example.demo_subject.vo.SubjectResp;

public interface SubjectService {

	// 建立課程
	public SubjectResp creatSubject(String subjectId,String subName,int week,int startTime,int endTime,int units);
	
	// 修改課程
	public SubjectResp updateSubject(String subjectId,String subName,int week,int startTime,int endTime,int units);

	// 依課程代碼搜尋
	public SubjectResp searchBySubjId(String subjectId);
	
	// 依課程名稱搜尋
	public SubjectResp searchBySubjName(String subName);
	
	// 新增學生
	public SubjectResp student(String stuId, String stuName);
	
	//選課
	public SubjectResp selectSubject(String stuId, List<String> subjectIdList);
	
	//學生已選上課程查詢
	public SubjectResp searchSubjByStu(String stuId);
	
	//退選 TODO 退選課程換成List
	public SubjectResp deleteSubByStu(String stuId,List<String> subjectIdList);
}
