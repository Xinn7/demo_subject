package com.example.demo_subject.constants;

public enum SubjectRtnCode {

	SUCCESSFUL("200","Successful!"),
	SUBJECT_NOT_EXIST("400","��ؤ��s�b"),
	SUBJECT_ID_ERROR("400","�нT�{��إN�X�O�_���T�A�ſ�J�ۦP�N�X"),
	CANNOT_NULL_OR_EMPTY("400", "Id or Name cannot be null or empty!!"),
	SUBJECT_SAME("400","�L�k�ײ߬ۦP���ҵ{"),
	TIME_SAME("400","�ҵ{�İ�"),
	UNITS_LIMIT("400","�Ǥ��W�L�W��"),
	SUTUDENT_NOT_EXIST("400","�нT�{�Ǹ��O�_���T"),
	WEEK_ERROR("400","�P����J���~"),
	TIME_ERROR("400","�W�Үɶ���8�I��18�I"),
	UNITS_ERROR("400","�Ǥ��ƶ���1��3�Ǥ�"),
	SUBJECT_EMPTY("400","�z�٥����"),
	SUBJECT_EXIST("400","�ҵ{�w�s�b"),
	SUTUDENT_EXIST("400","�ǥͤw�s�b");
	
	
	private String code;
	
	private String message;
	
	private SubjectRtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
