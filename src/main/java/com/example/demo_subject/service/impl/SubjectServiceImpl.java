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

	// 建立課程
	@Override
	public SubjectResp creatSubject(String subjectId, String subName, int week, int startTime, int endTime, int units) {
		// 確認參數是否正確
		SubjectResp check = checkParams(subjectId, subName, week, startTime, endTime, units);

		// 參數有誤
		if (check != null) {
			return check;
		}

		// 確認課程是否已存在
		// 已存在
		if (subjectDao.existsById(subjectId)) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_EXIST.getMessage());
		}

		// 不存在
		// 建立新課程
		Subject subject = new Subject(subjectId, subName, week, startTime, endTime, units);
		subjectDao.save(subject);
		return new SubjectResp(subject, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// 更新課程
	@Override
	public SubjectResp updateSubject(String subjectId, String subName, int week, int startTime, int endTime,
			int units) {
		// 確認參數是否正確
		SubjectResp check = checkParams(subjectId, subName, week, startTime, endTime, units);

		// 參數有誤
		if (check != null) {
			return check;
		}

		// 確認課程是否存在
		Optional<Subject> subjOp = subjectDao.findById(subjectId);

		// 不存在
		if (!subjOp.isPresent()) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
		}

		// 已存在
		Subject subject = subjOp.get();

		// 更新課程資料
		subject.updateSubject(subName, week, startTime, endTime, units);
		subjectDao.save(subject);
		return new SubjectResp(subject, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// 依課程代碼查詢
	@Override
	public SubjectResp searchBySubjId(String subjectId) {
		// 確認參數
		if (!StringUtils.hasText(subjectId)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		// 確認課程是否存在
		Optional<Subject> subOp = subjectDao.findById(subjectId);

		// 存在
		if (subOp.isPresent()) {
			return new SubjectResp(subOp.get(), SubjectRtnCode.SUCCESSFUL.getMessage());
		}

		// 不存在
		return new SubjectResp(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
	}

	// 依課程名稱查詢
	@Override
	public SubjectResp searchBySubjName(String subName) {
		// 確認參數
		if (!StringUtils.hasText(subName)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		// 依課程名稱查詢課程資訊
		List<Subject> subjList = subjectDao.findBySubName(subName);

		// 查詢結果為空
		if (subjList.isEmpty()) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
		}

		return new SubjectResp(null, subjList, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// 新增學生
	@Override
	public SubjectResp student(String stuId, String stuName) {
		// 確認參數
		if (!StringUtils.hasText(stuId) || !StringUtils.hasText(stuName)) {
			return new SubjectResp(SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage(), null);
		}

		// 判斷此學生是否存在
		// 已存在
		if (studentDao.existsById(stuId)) {
			return new SubjectResp(SubjectRtnCode.SUTUDENT_EXIST.getMessage(), null);
		}

		// 不存在
		// 新增學生
		Student student = new Student(stuId, stuName);
		studentDao.save(student);
		return new SubjectResp(SubjectRtnCode.SUCCESSFUL.getMessage(), student);
	}

	// 選課
	@Override
	public SubjectResp selectSubject(String stuId, List<String> subjectIdList) {
		// 確認參數
		if (!StringUtils.hasText(stuId) || CollectionUtils.isEmpty(subjectIdList)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		// 確認此學生是否存在
		Optional<Student> stuOp = studentDao.findById(stuId);

		// 不存在
		if (!stuOp.isPresent()) {
			return new SubjectResp(null, SubjectRtnCode.SUTUDENT_NOT_EXIST.getMessage());
		}

		// 存在
		// 取得此學生資訊
		Student student = stuOp.get();

		// 取得此學生的選課代碼
		String studentSubIdStr = student.getSubjectId();

		// 將加選的選課代碼由字串轉換成List
		// 還未選課時，選課代碼會為null
		if (studentSubIdStr != null) {

			String[] subArray = studentSubIdStr.split(",");
			for (String item : subArray) {
				String str = item.trim();

				// 不能重複輸入已選上的課程代碼
				if (subjectIdList.contains(str)) {
					return new SubjectResp(null, SubjectRtnCode.SUBJECT_SAME.getMessage());
				}

				// 將已選上跟加選的課程代碼合併在一起
				subjectIdList.add(str);
			}
		}

		// 依課程代碼取得課程資料
		List<Subject> subjectList = subjectDao.findAllById(subjectIdList);

		// 選的課程不在課程清單中
		// 初選時，輸入兩次相同代碼
		if (subjectList.size() < subjectIdList.size()) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_ID_ERROR.getMessage());
		}

		// 判斷是否課程名稱重複、衝堂或達學分上限
		SubjectResp check = checkSelectLimit(subjectList);
		// 不符合限制條件
		if (check != null) {
			return check;
		}

		// 取得符合選課條件的選課代碼 & 總學分數
		int totalUnits = 0;
		List<String> newSubIdList = new ArrayList<>();
		for (Subject item : subjectList) {
			String subjId = item.getSubjectId();
			newSubIdList.add(subjId);
			totalUnits += item.getUnits();
		}

		// 去除陣列前後的括號[]
		String newSubjectIdStr = newSubIdList.toString().substring(1, newSubIdList.toString().length() - 1);
		student.setSubjectId(newSubjectIdStr);
		studentDao.save(student);
		return new SubjectResp(student, totalUnits, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// 學生已選上課程查詢
	@Override
	public SubjectResp searchSubjByStu(String stuId) {
		// 確認參數
		if (!StringUtils.hasText(stuId)) {
			return new SubjectResp(null, null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		Optional<Student> stuOp = studentDao.findById(stuId);

		// 判斷是否有此學生
		// 不存在
		if (!stuOp.isPresent()) {
			return new SubjectResp(null, null, SubjectRtnCode.SUTUDENT_NOT_EXIST.getMessage());
		}

		// 存在
		// 取得此學生已選上的課程代碼
		Student student = stuOp.get();
		String stuSubjIdStr = student.getSubjectId();

		// 還未選課
		if (!StringUtils.hasText(stuSubjIdStr)) {
			return new SubjectResp(null, null, SubjectRtnCode.SUBJECT_EMPTY.getMessage());
		}

		// 將已選上的課程代碼(字串)轉換成List
		String[] subArray = stuSubjIdStr.split(",");
		List<String> subjIdList = new ArrayList<String>();
		for (String item : subArray) {
			String str = item.trim();
			subjIdList.add(str);
		}

		// 取得課程資訊
		List<Subject> subInFoList = subjectDao.findAllById(subjIdList);
		return new SubjectResp(student, subInFoList, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// 退選
	@Override
	public SubjectResp deleteSubByStu(String stuId, List<String> deleteSubjectIdList) {
		// 確認參數
		if (!StringUtils.hasText(stuId) || CollectionUtils.isEmpty(deleteSubjectIdList)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		Optional<Student> stuOp = studentDao.findById(stuId);

		// 判斷是否有此學生
		if (!stuOp.isPresent()) {
			return new SubjectResp(SubjectRtnCode.SUTUDENT_NOT_EXIST.getMessage(), null);
		}

		// 取得此學生已選上的課程代碼
		Student student = stuOp.get();
		String stuSubjId = student.getSubjectId();

		// 將已選上的課程代碼(字串)轉換成List
		String[] subArray = stuSubjId.split(",");
		List<String> subjIdList = new ArrayList<String>();
		for (String item : subArray) {
			String str = item.trim();
			subjIdList.add(str);
		}

		// 新增一個新的List，用來比對刪除的科目是否存在
		List<String> newSubjIdList = new ArrayList<String>();
		newSubjIdList.addAll(subjIdList);

		// 退選
		subjIdList.removeAll(deleteSubjectIdList);

		// 比對刪除的科目是否存在
		if (newSubjIdList.size() == subjIdList.size()) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
		}

		String newStr = subjIdList.toString().substring(1, subjIdList.toString().length() - 1); // 去除陣列前後的括號[]
		student.setSubjectId(newStr);
		studentDao.save(student);
		return new SubjectResp(SubjectRtnCode.SUCCESSFUL.getMessage(), student);
	}

	// 判斷輸入值是否正確
	private SubjectResp checkParams(String subjectId, String subName, int week, int startTime, int endTime, int units) {
		// 判斷課程名稱&課程代碼是否正確
		if (!StringUtils.hasText(subName) || !StringUtils.hasText(subjectId)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		// 判斷星期是否正確
		if (week < 1 || week > 5) {
			return new SubjectResp(null, SubjectRtnCode.WEEK_ERROR.getMessage());
		}

		// 判斷時間是否正確
		if (startTime < 8 || endTime > 18 || (endTime - startTime) <= 0) {
			return new SubjectResp(null, SubjectRtnCode.TIME_ERROR.getMessage());
		}

		// 判斷學分是否正確
		if (units < 1 || units > 3) {
			return new SubjectResp(null, SubjectRtnCode.UNITS_ERROR.getMessage());
		}

		return null;
	}

	// 選課限制:1.不能修相同課程 2.不能衝堂 3.學分上限為10
	private SubjectResp checkSelectLimit(List<Subject> subjectList) {
		// 不能修相同名稱的課程
		// 新增一個List用於暫存課程名稱
		List<String> nameList = new ArrayList<>();
		for (Subject item : subjectList) {
			// 取得美堂課的課程名稱
			String name = item.getSubName();
			nameList.add(name);
		}

		// 從第一個開始比對，最後一個不用比對
		for (int i = 0; i < (nameList.size() - 1); i++) {
			// 比對除了自己本身之後的每一個
			for (int j = i + 1; j < nameList.size(); j++) {
				// 課程名稱重複
				if (nameList.get(i).equalsIgnoreCase(nameList.get(j))) {
					return new SubjectResp(null, SubjectRtnCode.SUBJECT_SAME.getMessage());
				}

			}
		}
		// 衝堂
		// 從第一個開始比對，最後一個不用比對
		for (int i = 0; i < (subjectList.size() - 1); i++) {
			Subject subject1 = subjectList.get(i);
			// 比對除了自己本身之後的每一個
			for (int j = i + 1; j < (subjectList.size()); j++) {
				Subject subject2 = subjectList.get(j);
				// 同一天
				if (subject1.getWeek() == subject2.getWeek()) {
					// subject1的開始時間，"沒有"在subject2的結束時間後
					// subject1的結束時間，"沒有"在subject2的開始時間之前
					if (!(subject1.getStartTime() >= subject2.getEndTime())
							&& !(subject1.getEndTime() <= subject2.getStartTime())) {
						return new SubjectResp(null, SubjectRtnCode.TIME_SAME.getMessage());
					}
				}
			}
		}
		// 判斷學分上限為10
		int sum = 0;
		for (Subject item : subjectList) {
			// 取得每堂課的學分，並相加
			sum += item.getUnits();
		}

		// 學分大於10
		if (sum > 10) {
			return new SubjectResp(null, SubjectRtnCode.UNITS_LIMIT.getMessage());
		}
		return null;
	}

}
