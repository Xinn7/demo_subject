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

	// �إ߽ҵ{
	@Override
	public SubjectResp creatSubject(String subjectId, String subName, int week, int startTime, int endTime, int units) {
		// �T�{�ѼƬO�_���T
		SubjectResp check = checkParams(subjectId, subName, week, startTime, endTime, units);

		// �ѼƦ��~
		if (check != null) {
			return check;
		}

		// �T�{�ҵ{�O�_�w�s�b
		// �w�s�b
		if (subjectDao.existsById(subjectId)) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_EXIST.getMessage());
		}

		// ���s�b
		// �إ߷s�ҵ{
		Subject subject = new Subject(subjectId, subName, week, startTime, endTime, units);
		subjectDao.save(subject);
		return new SubjectResp(subject, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// ��s�ҵ{
	@Override
	public SubjectResp updateSubject(String subjectId, String subName, int week, int startTime, int endTime,
			int units) {
		// �T�{�ѼƬO�_���T
		SubjectResp check = checkParams(subjectId, subName, week, startTime, endTime, units);

		// �ѼƦ��~
		if (check != null) {
			return check;
		}

		// �T�{�ҵ{�O�_�s�b
		Optional<Subject> subjOp = subjectDao.findById(subjectId);

		// ���s�b
		if (!subjOp.isPresent()) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
		}

		// �w�s�b
		Subject subject = subjOp.get();

		// ��s�ҵ{���
		subject.updateSubject(subName, week, startTime, endTime, units);
		subjectDao.save(subject);
		return new SubjectResp(subject, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// �̽ҵ{�N�X�d��
	@Override
	public SubjectResp searchBySubjId(String subjectId) {
		// �T�{�Ѽ�
		if (!StringUtils.hasText(subjectId)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		// �T�{�ҵ{�O�_�s�b
		Optional<Subject> subOp = subjectDao.findById(subjectId);

		// �s�b
		if (subOp.isPresent()) {
			return new SubjectResp(subOp.get(), SubjectRtnCode.SUCCESSFUL.getMessage());
		}

		// ���s�b
		return new SubjectResp(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
	}

	// �̽ҵ{�W�٬d��
	@Override
	public SubjectResp searchBySubjName(String subName) {
		// �T�{�Ѽ�
		if (!StringUtils.hasText(subName)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		// �̽ҵ{�W�٬d�߽ҵ{��T
		List<Subject> subjList = subjectDao.findBySubName(subName);

		// �d�ߵ��G����
		if (subjList.isEmpty()) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
		}

		return new SubjectResp(null, subjList, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// �s�W�ǥ�
	@Override
	public SubjectResp student(String stuId, String stuName) {
		// �T�{�Ѽ�
		if (!StringUtils.hasText(stuId) || !StringUtils.hasText(stuName)) {
			return new SubjectResp(SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage(), null);
		}

		// �P�_���ǥͬO�_�s�b
		// �w�s�b
		if (studentDao.existsById(stuId)) {
			return new SubjectResp(SubjectRtnCode.SUTUDENT_EXIST.getMessage(), null);
		}

		// ���s�b
		// �s�W�ǥ�
		Student student = new Student(stuId, stuName);
		studentDao.save(student);
		return new SubjectResp(SubjectRtnCode.SUCCESSFUL.getMessage(), student);
	}

	// ���
	@Override
	public SubjectResp selectSubject(String stuId, List<String> subjectIdList) {
		// �T�{�Ѽ�
		if (!StringUtils.hasText(stuId) || CollectionUtils.isEmpty(subjectIdList)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		// �T�{���ǥͬO�_�s�b
		Optional<Student> stuOp = studentDao.findById(stuId);

		// ���s�b
		if (!stuOp.isPresent()) {
			return new SubjectResp(null, SubjectRtnCode.SUTUDENT_NOT_EXIST.getMessage());
		}

		// �s�b
		// ���o���ǥ͸�T
		Student student = stuOp.get();

		// ���o���ǥͪ���ҥN�X
		String studentSubIdStr = student.getSubjectId();

		// �N�[�諸��ҥN�X�Ѧr���ഫ��List
		// �٥���ҮɡA��ҥN�X�|��null
		if (studentSubIdStr != null) {

			String[] subArray = studentSubIdStr.split(",");
			for (String item : subArray) {
				String str = item.trim();

				// ���୫�ƿ�J�w��W���ҵ{�N�X
				if (subjectIdList.contains(str)) {
					return new SubjectResp(null, SubjectRtnCode.SUBJECT_SAME.getMessage());
				}

				// �N�w��W��[�諸�ҵ{�N�X�X�֦b�@�_
				subjectIdList.add(str);
			}
		}

		// �̽ҵ{�N�X���o�ҵ{���
		List<Subject> subjectList = subjectDao.findAllById(subjectIdList);

		// �諸�ҵ{���b�ҵ{�M�椤
		// ���ɡA��J�⦸�ۦP�N�X
		if (subjectList.size() < subjectIdList.size()) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_ID_ERROR.getMessage());
		}

		// �P�_�O�_�ҵ{�W�٭��ơB�İ�ιF�Ǥ��W��
		SubjectResp check = checkSelectLimit(subjectList);
		// ���ŦX�������
		if (check != null) {
			return check;
		}

		// ���o�ŦX��ұ��󪺿�ҥN�X & �`�Ǥ���
		int totalUnits = 0;
		List<String> newSubIdList = new ArrayList<>();
		for (Subject item : subjectList) {
			String subjId = item.getSubjectId();
			newSubIdList.add(subjId);
			totalUnits += item.getUnits();
		}

		// �h���}�C�e�᪺�A��[]
		String newSubjectIdStr = newSubIdList.toString().substring(1, newSubIdList.toString().length() - 1);
		student.setSubjectId(newSubjectIdStr);
		studentDao.save(student);
		return new SubjectResp(student, totalUnits, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// �ǥͤw��W�ҵ{�d��
	@Override
	public SubjectResp searchSubjByStu(String stuId) {
		// �T�{�Ѽ�
		if (!StringUtils.hasText(stuId)) {
			return new SubjectResp(null, null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		Optional<Student> stuOp = studentDao.findById(stuId);

		// �P�_�O�_�����ǥ�
		// ���s�b
		if (!stuOp.isPresent()) {
			return new SubjectResp(null, null, SubjectRtnCode.SUTUDENT_NOT_EXIST.getMessage());
		}

		// �s�b
		// ���o���ǥͤw��W���ҵ{�N�X
		Student student = stuOp.get();
		String stuSubjIdStr = student.getSubjectId();

		// �٥����
		if (!StringUtils.hasText(stuSubjIdStr)) {
			return new SubjectResp(null, null, SubjectRtnCode.SUBJECT_EMPTY.getMessage());
		}

		// �N�w��W���ҵ{�N�X(�r��)�ഫ��List
		String[] subArray = stuSubjIdStr.split(",");
		List<String> subjIdList = new ArrayList<String>();
		for (String item : subArray) {
			String str = item.trim();
			subjIdList.add(str);
		}

		// ���o�ҵ{��T
		List<Subject> subInFoList = subjectDao.findAllById(subjIdList);
		return new SubjectResp(student, subInFoList, SubjectRtnCode.SUCCESSFUL.getMessage());
	}

	// �h��
	@Override
	public SubjectResp deleteSubByStu(String stuId, List<String> deleteSubjectIdList) {
		// �T�{�Ѽ�
		if (!StringUtils.hasText(stuId) || CollectionUtils.isEmpty(deleteSubjectIdList)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		Optional<Student> stuOp = studentDao.findById(stuId);

		// �P�_�O�_�����ǥ�
		if (!stuOp.isPresent()) {
			return new SubjectResp(SubjectRtnCode.SUTUDENT_NOT_EXIST.getMessage(), null);
		}

		// ���o���ǥͤw��W���ҵ{�N�X
		Student student = stuOp.get();
		String stuSubjId = student.getSubjectId();

		// �N�w��W���ҵ{�N�X(�r��)�ഫ��List
		String[] subArray = stuSubjId.split(",");
		List<String> subjIdList = new ArrayList<String>();
		for (String item : subArray) {
			String str = item.trim();
			subjIdList.add(str);
		}

		// �s�W�@�ӷs��List�A�ΨӤ��R������جO�_�s�b
		List<String> newSubjIdList = new ArrayList<String>();
		newSubjIdList.addAll(subjIdList);

		// �h��
		subjIdList.removeAll(deleteSubjectIdList);

		// ���R������جO�_�s�b
		if (newSubjIdList.size() == subjIdList.size()) {
			return new SubjectResp(null, SubjectRtnCode.SUBJECT_NOT_EXIST.getMessage());
		}

		String newStr = subjIdList.toString().substring(1, subjIdList.toString().length() - 1); // �h���}�C�e�᪺�A��[]
		student.setSubjectId(newStr);
		studentDao.save(student);
		return new SubjectResp(SubjectRtnCode.SUCCESSFUL.getMessage(), student);
	}

	// �P�_��J�ȬO�_���T
	private SubjectResp checkParams(String subjectId, String subName, int week, int startTime, int endTime, int units) {
		// �P�_�ҵ{�W��&�ҵ{�N�X�O�_���T
		if (!StringUtils.hasText(subName) || !StringUtils.hasText(subjectId)) {
			return new SubjectResp(null, SubjectRtnCode.CANNOT_NULL_OR_EMPTY.getMessage());
		}

		// �P�_�P���O�_���T
		if (week < 1 || week > 5) {
			return new SubjectResp(null, SubjectRtnCode.WEEK_ERROR.getMessage());
		}

		// �P�_�ɶ��O�_���T
		if (startTime < 8 || endTime > 18 || (endTime - startTime) <= 0) {
			return new SubjectResp(null, SubjectRtnCode.TIME_ERROR.getMessage());
		}

		// �P�_�Ǥ��O�_���T
		if (units < 1 || units > 3) {
			return new SubjectResp(null, SubjectRtnCode.UNITS_ERROR.getMessage());
		}

		return null;
	}

	// ��ҭ���:1.����׬ۦP�ҵ{ 2.����İ� 3.�Ǥ��W����10
	private SubjectResp checkSelectLimit(List<Subject> subjectList) {
		// ����׬ۦP�W�٪��ҵ{
		// �s�W�@��List�Ω�Ȧs�ҵ{�W��
		List<String> nameList = new ArrayList<>();
		for (Subject item : subjectList) {
			// ���o����Ҫ��ҵ{�W��
			String name = item.getSubName();
			nameList.add(name);
		}

		// �q�Ĥ@�Ӷ}�l���A�̫�@�Ӥ��Τ��
		for (int i = 0; i < (nameList.size() - 1); i++) {
			// ��ﰣ�F�ۤv�������᪺�C�@��
			for (int j = i + 1; j < nameList.size(); j++) {
				// �ҵ{�W�٭���
				if (nameList.get(i).equalsIgnoreCase(nameList.get(j))) {
					return new SubjectResp(null, SubjectRtnCode.SUBJECT_SAME.getMessage());
				}

			}
		}
		// �İ�
		// �q�Ĥ@�Ӷ}�l���A�̫�@�Ӥ��Τ��
		for (int i = 0; i < (subjectList.size() - 1); i++) {
			Subject subject1 = subjectList.get(i);
			// ��ﰣ�F�ۤv�������᪺�C�@��
			for (int j = i + 1; j < (subjectList.size()); j++) {
				Subject subject2 = subjectList.get(j);
				// �P�@��
				if (subject1.getWeek() == subject2.getWeek()) {
					// subject1���}�l�ɶ��A"�S��"�bsubject2�������ɶ���
					// subject1�������ɶ��A"�S��"�bsubject2���}�l�ɶ����e
					if (!(subject1.getStartTime() >= subject2.getEndTime())
							&& !(subject1.getEndTime() <= subject2.getStartTime())) {
						return new SubjectResp(null, SubjectRtnCode.TIME_SAME.getMessage());
					}
				}
			}
		}
		// �P�_�Ǥ��W����10
		int sum = 0;
		for (Subject item : subjectList) {
			// ���o�C��Ҫ��Ǥ��A�ìۥ[
			sum += item.getUnits();
		}

		// �Ǥ��j��10
		if (sum > 10) {
			return new SubjectResp(null, SubjectRtnCode.UNITS_LIMIT.getMessage());
		}
		return null;
	}

}
