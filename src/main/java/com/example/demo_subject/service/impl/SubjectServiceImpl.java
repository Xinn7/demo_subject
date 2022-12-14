package com.example.demo_subject.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.demo_subject.constants.SubjectRtnCode;
import com.example.demo_subject.entity.Student;
import com.example.demo_subject.entity.Subject;
import com.example.demo_subject.repository.StudentDao;
import com.example.demo_subject.repository.SubjectDao;
import com.example.demo_subject.service.ifs.SubjectService;
import com.example.demo_subject.vo.SubjectResp;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectDao subjectDao;

	@Autowired
	private StudentDao studentDao;

	// ミ揭祘
	@Override
	public SubjectResp creatSubject(String subjectId, String subName, int week, int startTime, int endTime, int units) {
		// 絋粄把计琌タ絋
		SubjectResp check = checkParams(subjectId, subName, week, startTime, endTime, units);

		// 把计Τ粇
		if (check != null) {
			return check;
		}

		// 絋粄揭祘琌
		// 
		if (subjectDao.existsById(subjectId)) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_EXIST.getMessage());
		}

		// ぃ
		// ミ穝揭祘
		Subject subject = new Subject(subjectId, subName, week, startTime, endTime, units);
		subjectDao.save(subject);
		return new SubjectResp(subject, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// 穝揭祘
	@Override
	public SubjectResp updateSubject(String subjectId, String subName, int week, int startTime, int endTime,
			int units) {
		// 絋粄把计琌タ絋
		SubjectResp check = checkParams(subjectId, subName, week, startTime, endTime, units);

		// 把计Τ粇
		if (check != null) {
			return check;
		}

		// 絋粄揭祘琌
		Optional<Subject> subjOp = subjectDao.findById(subjectId);

		// ぃ
		if (!subjOp.isPresent()) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
		}

		// 
		Subject subject = subjOp.get();

		// 穝揭祘戈
		subject.updateSubject(subName, week, startTime, endTime, units);
		subjectDao.save(subject);
		return new SubjectResp(subject, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// ㄌ揭祘絏琩高
	@Override
	public SubjectResp searchBySubjId(String subjectId) {
		// 絋粄把计
		if (!StringUtils.hasText(subjectId)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		// 絋粄揭祘琌
		Optional<Subject> subOp = subjectDao.findById(subjectId);

		// 
		if (subOp.isPresent()) {
			return new SubjectResp(subOp.get(), SubjectRtnCode.SUCCESSFUL.getMessage());
		}

		// ぃ
		return new SubjectResp(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
	}

	// ㄌ揭祘嘿琩高
	@Override
	public SubjectResp searchBySubjName(String subName) {
		// 絋粄把计
		if (!StringUtils.hasText(subName)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		// ㄌ揭祘嘿琩高揭祘戈癟
		List<Subject> subjList = subjectDao.findBySubName(subName);

		// 琩高挡狦
		if (subjList.isEmpty()) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
		}

		return new SubjectResp(null, subjList, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// 穝糤厩ネ
	@Override
	public SubjectResp student(String stuId, String stuName) {
		// 絋粄把计
		if (!StringUtils.hasText(stuId) || !StringUtils.hasText(stuName)) {
			return new SubjectResp(SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage(), null);
		}

		// 耞厩ネ琌
		// 
		if (studentDao.existsById(stuId)) {
			return new SubjectResp(SubjectRtnCode.SUTUDENT_EXIST.getMessage(), null);
		}

		// ぃ
		// 穝糤厩ネ
		Student student = new Student(stuId, stuName);
		studentDao.save(student);
		return new SubjectResp(SubjectRtnCode.SUCCESSFUL.getMessage(), student);
	}

	// 匡揭
	@Override
	public SubjectResp selectSubject(String stuId, List<String> subjectIdList) {
		// 絋粄把计
		if (!StringUtils.hasText(stuId) || CollectionUtils.isEmpty(subjectIdList)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		// 絋粄厩ネ琌
		Optional<Student> stuOp = studentDao.findById(stuId);

		// ぃ
		if (!stuOp.isPresent()) {
			return new SubjectResp(null, SubjectRtnCode.SUTUDENT_NOT_EXIST.getMessage());
		}

		// 
		// 眔厩ネ戈癟
		Student student = stuOp.get();

		// 眔厩ネ匡揭絏
		String studentSubIdStr = student.getSubjectId();

		// 盢匡匡揭絏パ﹃锣传ΘList
		// 临ゼ匡揭匡揭絏穦null
		if (studentSubIdStr != null) {

			String[] subArray = studentSubIdStr.split(",");
			for (String item : subArray) {
				String str = item.trim();

				// ぃ狡块匡揭祘絏
				if (subjectIdList.contains(str)) {
					return new SubjectResp(null, SubjectRtnCode.SUBJECT_SAME.getMessage());
				}

				// 盢匡蛤匡揭祘絏ㄖ癬
				subjectIdList.add(str);
			}
		}

		// ㄌ揭祘絏眔揭祘戈
		List<Subject> subjectList = subjectDao.findAllById(subjectIdList);

		// 匡揭祘ぃ揭祘睲虫い
		// 匡块ㄢΩ絏
		if (subjectList.size() < subjectIdList.size()) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_ID_ERROR.getMessage());
		}

		// 耞琌揭祘嘿狡侥绑┪笷厩だ
		SubjectResp check = checkSelectLimit(subjectList);
		// ぃ才兵ン
		if (check != null) {
			return check;
		}

		// 眔才匡揭兵ン匡揭絏 & 羆厩だ计
		int totalUnits = 0;
		List<String> newSubIdList = new ArrayList<>();
		for (Subject item : subjectList) {
			String subjId = item.getSubjectId();
			newSubIdList.add(subjId);
			totalUnits += item.getUnits();
		}

		// 埃皚玡珹腹[]
		String newSubjectIdStr = newSubIdList.toString().substring(1, newSubIdList.toString().length() - 1);
		student.setSubjectId(newSubjectIdStr);
		studentDao.save(student);
		return new SubjectResp(student, totalUnits, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// 厩ネ匡揭祘琩高
	@Override
	public SubjectResp searchSubjByStu(String stuId) {
		// 絋粄把计
		if (!StringUtils.hasText(stuId)) {
			return new SubjectResp(null, null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		Optional<Student> stuOp = studentDao.findById(stuId);

		// 耞琌Τ厩ネ
		// ぃ
		if (!stuOp.isPresent()) {
			return new SubjectResp(null, null, SubjectRtnCode.SUTUDENT_NOT_EXIST.getMessage());
		}

		// 
		// 眔厩ネ匡揭祘絏
		Student student = stuOp.get();
		String stuSubjIdStr = student.getSubjectId();

		// 临ゼ匡揭
		if (!StringUtils.hasText(stuSubjIdStr)) {
			return new SubjectResp(null, null, SubjectRtnCode.SUBJECT_EMPTY.getMessage());
		}

		// 盢匡揭祘絏(﹃)锣传ΘList
		String[] subArray = stuSubjIdStr.split(",");
		List<String> subjIdList = new ArrayList<String>();
		for (String item : subArray) {
			String str = item.trim();
			subjIdList.add(str);
		}

		// 眔揭祘戈癟
		List<Subject> subInFoList = subjectDao.findAllById(subjIdList);
		return new SubjectResp(student, subInFoList, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// 癶匡
	@Override
	public SubjectResp deleteSubByStu(String stuId, List<String> deleteSubjectIdList) {
		// 絋粄把计
		if (!StringUtils.hasText(stuId) || CollectionUtils.isEmpty(deleteSubjectIdList)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		Optional<Student> stuOp = studentDao.findById(stuId);

		// 耞琌Τ厩ネ
		if (!stuOp.isPresent()) {
			return new SubjectResp(SubjectRtnCode.SUTUDENT_NOT_EXIST.getMessage(), null);
		}

		// 眔厩ネ匡揭祘絏
		Student student = stuOp.get();
		String stuSubjId = student.getSubjectId();

		// 盢匡揭祘絏(﹃)锣传ΘList
		String[] subArray = stuSubjId.split(",");
		List<String> subjIdList = new ArrayList<String>();
		for (String item : subArray) {
			String str = item.trim();
			subjIdList.add(str);
		}

		// 穝糤穝Listノㄓゑ癸埃ヘ琌
		List<String> newSubjIdList = new ArrayList<String>();
		newSubjIdList.addAll(subjIdList);

		// 癶匡
		subjIdList.removeAll(deleteSubjectIdList);

		// ゑ癸埃ヘ琌
		if (newSubjIdList.size() == subjIdList.size()) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
		}

		String newStr = subjIdList.toString().substring(1, subjIdList.toString().length() - 1); // 埃皚玡珹腹[]
		student.setSubjectId(newStr);
		studentDao.save(student);
		return new SubjectResp(SubjectRtnCode.SUCCESSFUL.getMessage(), student);
	}

	// 耞块琌タ絋
	private SubjectResp checkParams(String subjectId, String subName, int week, int startTime, int endTime, int units) {
		// 耞揭祘嘿&揭祘絏琌タ絋
		if (!StringUtils.hasText(subName) || !StringUtils.hasText(subjectId)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		// 耞琍戳琌タ絋
		if (week < 1 || week > 5) {
			return new SubjectResp(null, SubjectRtnCode.WEEK_ERROR.getMessage());
		}

		// 耞丁琌タ絋
		if (startTime < 8 || endTime > 18 || (endTime - startTime) <= 0) {
			return new SubjectResp(null, SubjectRtnCode.TIME_ERROR.getMessage());
		}

		// 耞厩だ琌タ絋
		if (units < 1 || units > 3) {
			return new SubjectResp(null, SubjectRtnCode.UNITS_ERROR.getMessage());
		}

		return null;
	}

	// 匡揭:1.ぃ揭祘 2.ぃ侥绑 3.厩だ10
	private SubjectResp checkSelectLimit(List<Subject> subjectList) {
		// ぃ嘿揭祘
		// 穝糤Listノ既揭祘嘿
		List<String> nameList = new ArrayList<>();
		for (Subject item : subjectList) {
			// 眔绑揭揭祘嘿
			String name = item.getSubName();
			nameList.add(name);
		}

		// 眖材秨﹍ゑ癸程ぃノゑ癸
		for (int i = 0; i < (nameList.size() - 1); i++) {
			// ゑ癸埃セōぇ–
			for (int j = i + 1; j < nameList.size(); j++) {
				// 揭祘嘿狡
				if (nameList.get(i).equalsIgnoreCase(nameList.get(j))) {
					return new SubjectResp(null, SubjectRtnCode.SUBJECT_SAME.getMessage());
				}

			}
		}
		// 侥绑
		// 眖材秨﹍ゑ癸程ぃノゑ癸
		for (int i = 0; i < (subjectList.size() - 1); i++) {
			Subject subject1 = subjectList.get(i);
			// ゑ癸埃セōぇ–
			for (int j = i + 1; j < (subjectList.size()); j++) {
				Subject subject2 = subjectList.get(j);
				// ぱ
				if (subject1.getWeek() == subject2.getWeek()) {
					// subject1秨﹍丁"⊿Τ"subject2挡丁
					// subject1挡丁"⊿Τ"subject2秨﹍丁ぇ玡
					if (!(subject1.getStartTime() >= subject2.getEndTime())
							&& !(subject1.getEndTime() <= subject2.getStartTime())) {
						return new SubjectResp(null, SubjectRtnCode.TIME_SAME.getMessage());
					}
				}
			}
		}
		// 耞厩だ10
		int sum = 0;
		for (Subject item : subjectList) {
			// 眔–绑揭厩だ
			sum += item.getUnits();
		}

		// 厩だ10
		if (sum > 10) {
			return new SubjectResp(null, SubjectRtnCode.UNITS_LIMIT.getMessage());
		}
		return null;
	}

}
