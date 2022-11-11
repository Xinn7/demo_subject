package com.example.demo_subject.service.ifs;

import java.util.List;
import java.util.Set;

import com.example.demo_subject.vo.SearchByStuRes;
import com.example.demo_subject.vo.StudentSelectRes;
import com.example.demo_subject.vo.SubjectRes;

public interface SubjectService {

	// 建立課程
	public SubjectRes creatSubject(String subNum,String subName,int week,int startTime,int endTime,int units);
	
	// 修改課程
	public SubjectRes updateSubject(String subNum,String subName,int week,int startTime,int endTime,int units);

	// 依課程代碼搜尋
	public SubjectRes searchByNum(String subNum);
	
	// 依課程名稱搜尋
	public SubjectRes searchByName(String subName);
	
	// 新增學生
	public StudentSelectRes student(String stuId, String stuName);
	
	//選課
	public StudentSelectRes selectSubject(String stuId, List<String> subIdList);
	
	//學生已選上課程查詢
	public SearchByStuRes searchByStuSub(String stuId);
	
	//退選
	public StudentSelectRes deleteSub(String stuId,String subNum);
}
