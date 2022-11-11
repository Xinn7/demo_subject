package com.example.demo_subject.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo_subject.constants.SubjectRtnCode;
import com.example.demo_subject.entity.Student;
import com.example.demo_subject.entity.Subject;
import com.example.demo_subject.repository.StudentDao;
import com.example.demo_subject.repository.SubjectDao;
import com.example.demo_subject.service.ifs.SubjectService;
import com.example.demo_subject.vo.SearchByStuRes;
import com.example.demo_subject.vo.StudentSelectRes;
import com.example.demo_subject.vo.SubjectRes;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectDao subjectDao;

	@Autowired
	private StudentDao studentDao;

	// 建立課程
	@Override
	public SubjectRes creatSubject(String subNum, String subName, int week, int startTime, int endTime, int units) {
		if (!StringUtils.hasText(subName) || !StringUtils.hasText(subNum)) {
			return new SubjectRes(null, SubjectRtnCode.CANNOT_NULL.getMessage());
		}
		if (week < 1 || week > 5) {
			return new SubjectRes(null, SubjectRtnCode.WEEK_ERROR.getMessage());
		}
		if (startTime < 8 | endTime > 18 | (endTime - startTime) <= 0) {
			return new SubjectRes(null, SubjectRtnCode.TIME_ERROR.getMessage());
		}
		if (units < 1 || units > 3) {
			return new SubjectRes(null, SubjectRtnCode.UNITS_ERROR.getMessage());
		}
		if (subjectDao.existsById(subNum)) {
			return new SubjectRes(null, SubjectRtnCode.SUBJECT_IS_EXIST.getMessage());
		}
		Subject subject = new Subject(subNum, subName, week, startTime, endTime, units);
		subjectDao.save(subject);
		return new SubjectRes(subject, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// 更新課程
	@Override
	public SubjectRes updateSubject(String subNum, String subName, int week, int startTime, int endTime, int units) {
		if (!StringUtils.hasText(subName) || !StringUtils.hasText(subNum)) {
			return new SubjectRes(null, SubjectRtnCode.CANNOT_NULL.getMessage());
		}
		if (week < 1 || week > 5) {
			return new SubjectRes(null, SubjectRtnCode.WEEK_ERROR.getMessage());
		}
		if (startTime < 8 | endTime > 18 | (endTime - startTime) <= 0) {
			return new SubjectRes(null, SubjectRtnCode.TIME_ERROR.getMessage());
		}
		if (units < 1 || units > 3) {
			return new SubjectRes(null, SubjectRtnCode.UNITS_ERROR.getMessage());
		}
		if (subjectDao.existsById(subNum)) {
			Subject subject = new Subject(subNum, subName, week, startTime, endTime, units);
			subjectDao.save(subject);
			return new SubjectRes(subject, SubjectRtnCode.SUCCESSFUL.getMessage());
		}
		return new SubjectRes(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
	}

	// 依課程代碼查詢
	@Override
	public SubjectRes searchByNum(String subNum) {
		if (!StringUtils.hasText(subNum)) {
			return new SubjectRes(null, SubjectRtnCode.CANNOT_NULL.getMessage());
		}
		Optional<Subject> subOp = subjectDao.findById(subNum);
		if (subOp.isPresent()) {
			return new SubjectRes(subOp.get(), SubjectRtnCode.SUCCESSFUL.getMessage());
		}
		return new SubjectRes(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
	}

	// 依課程名稱查詢
	@Override
	public SubjectRes searchByName(String subName) {
		if (!StringUtils.hasText(subName)) {
			return new SubjectRes(null, SubjectRtnCode.CANNOT_NULL.getMessage());
		}
		// public List<Subject> findBySubName(String subName);
		// 找到的List<Subject>為空
		List<Subject> subjList = subjectDao.findBySubName(subName);
		if (subjList.isEmpty()) {
			return new SubjectRes(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
		}
		return new SubjectRes(SubjectRtnCode.SUCCESSFUL.getMessage(), subjList);
	}

	// 新增學生
	@Override
	public StudentSelectRes student(String stuId, String stuName) {
		if (!StringUtils.hasText(stuId) || !StringUtils.hasText(stuName)) {
			return new StudentSelectRes(null, SubjectRtnCode.CANNOT_NULL.getMessage());
		}
		// 判斷是否存在 TODO
		if (studentDao.existsById(stuId)) {
			return new StudentSelectRes(null, SubjectRtnCode.SUTUDENT_EXIST.getMessage());
		}
		Student student = new Student(stuId, stuName);
		studentDao.save(student);
		return new StudentSelectRes(student, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// 選課
	@Override
	public StudentSelectRes selectSubject(String stuId, List<String> subIdList) {
		// 學號為空 / 空白 / null
		if (!StringUtils.hasText(stuId)) {
			return new StudentSelectRes(null, SubjectRtnCode.CANNOT_NULL.getMessage());
		}
		Optional<Student> stuOp = studentDao.findById(stuId);
		if (stuOp.isPresent()) {
			Student student = stuOp.get();
			String subject = student.getSubNumber();
			// 還未選課，subject為null
			// 原本已有課程
			if (subject != null) {
				String[] subArray = subject.split(",");
				for (String item : subArray) {
					String str = item.trim();
					// 不能重複輸入已選上的課程代碼
					if (subIdList.contains(str)) {
						return new StudentSelectRes(null, SubjectRtnCode.SUBJECT_SAME.getMessage());
					}
					subIdList.add(str);
				}
			}
			List<Subject> subList = subjectDao.findAllById(subIdList);
			// 不能選沒有在DB的課程
			// 若都選擇不在DB的課程則無法選課
			if (subList.isEmpty()) {
				return new StudentSelectRes(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
			}
			// 不能修相同名稱的課程
			List<String> nameList = new ArrayList<>();
			for (Subject item : subList) {
				String name = item.getSubName();
				nameList.add(name);
			}
			for (int i = 0; i < (nameList.size() - 1); i++) {
				for (int j = i + 1; j < nameList.size(); j++) {
					if (nameList.get(i).equalsIgnoreCase(nameList.get(j))) {
						return new StudentSelectRes(null, SubjectRtnCode.SUBJECT_SAME.getMessage());
					}

				}
			}
			// 衝堂
			for (int i = 0; i < (subList.size() - 1); i++) {
				Subject subjecti = subList.get(i);
				for (int j = i + 1; j < (subList.size()); j++) {
					Subject subjectj = subList.get(j);
					if (subjecti.getWeek() == subjectj.getWeek()) {
						if (!(subjecti.getStartTime() >= subjectj.getEndTime())
								&& !(subjecti.getEndTime() <= subjectj.getStartTime())) {
							return new StudentSelectRes(null, SubjectRtnCode.TIME_SAME.getMessage());
						}
					}
				}
			}
			// 判斷學分上限為10
			int sum = 0;
			for (Subject item : subList) {
				sum += item.getUnits();
			}
			if (sum > 10) {
				return new StudentSelectRes(null, SubjectRtnCode.UNITS_LIMIT.getMessage());
			}
			List<String> newSubIdList = new ArrayList<>();
			for (Subject item : subList) {
				String subjId = item.getSubNum();
				newSubIdList.add(subjId);
			}
			String newStr = newSubIdList.toString().substring(1, newSubIdList.toString().length() - 1); // 去除陣列前後的括號[]
			student.setSubNumber(newStr);
			studentDao.save(student);
			return new StudentSelectRes(student, SubjectRtnCode.SUCCESSFUL.getMessage());
		}
		return new StudentSelectRes(null, SubjectRtnCode.SUTUDENT_NOT_EXIST.getMessage());
	}

	// 學生已選上課程查詢
	@Override
	public SearchByStuRes searchByStuSub(String stuId) {
		if (!StringUtils.hasText(stuId)) {
			return new SearchByStuRes(null, null, SubjectRtnCode.CANNOT_NULL.getMessage());
		}
		Optional<Student> stuOp = studentDao.findById(stuId);
		if (stuOp.isPresent()) {
			Student student = stuOp.get();
			String stuSubj = student.getSubNumber();
			// 還未選課
			if (!StringUtils.hasText(stuSubj)) {
				return new SearchByStuRes(null, null, SubjectRtnCode.SUBJECT_IS_EMPTY.getMessage());
			}
			String[] subArray = stuSubj.split(",");
			List<String> subNumList = new ArrayList<String>();
			for (String item : subArray) {
				String str = item.trim();
				subNumList.add(str);
			}
			List<Subject> subInFoList = subjectDao.findAllById(subNumList);
			return new SearchByStuRes(student, subInFoList, SubjectRtnCode.SUCCESSFUL.getMessage());
		}
		return new SearchByStuRes(null, null, SubjectRtnCode.SUTUDENT_NOT_EXIST.getMessage());
	}

	// 退選
	@Override
	public StudentSelectRes deleteSub(String stuId, String subNum) {
		if (!StringUtils.hasText(stuId) || !StringUtils.hasText(subNum)) {
			return new StudentSelectRes(null, SubjectRtnCode.CANNOT_NULL.getMessage());
		}
		Student student = studentDao.findById(stuId).get();
		String stuSubjNum = student.getSubNumber();
		if (!StringUtils.hasText(stuSubjNum)) {
			return new StudentSelectRes(null, SubjectRtnCode.SUTUDENT_NOT_EXIST.getMessage());
		}
		String[] subArray = stuSubjNum.split(",");
		List<String> subNumList = new ArrayList<String>();
		for (String item : subArray) {
			String str = item.trim();
			subNumList.add(str);
		}
		if (!subNumList.contains(subNum)) {
			return new StudentSelectRes(student, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
		}
		subNumList.remove(subNum);
		String newStr = subNumList.toString().substring(1, subNumList.toString().length() - 1); // 去除陣列前後的括號[]
		student.setSubNumber(newStr);
		studentDao.save(student);
		return new StudentSelectRes(student, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

}
