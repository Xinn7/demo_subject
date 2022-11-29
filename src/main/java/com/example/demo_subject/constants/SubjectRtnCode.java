package com.example.demo_subject.constants;

public enum SubjectRtnCode {

	SUCCESSFUL("200","Successful!"),
	SUBJECT_NOT_EXIST("400","科目不存在"),
	SUBJECT_ID_ERROR("400","請確認科目代碼是否正確，勿輸入相同代碼"),
	CANNOT_NULL_OR_EMPTY("400", "Id or Name cannot be null or empty!!"),
	SUBJECT_SAME("400","無法修習相同的課程"),
	TIME_SAME("400","課程衝堂"),
	UNITS_LIMIT("400","學分超過上限"),
	SUTUDENT_NOT_EXIST("400","請確認學號是否正確"),
	WEEK_ERROR("400","星期輸入錯誤"),
	TIME_ERROR("400","上課時間為8點到18點"),
	UNITS_ERROR("400","學分數須為1至3學分"),
	SUBJECT_EMPTY("400","您還未選課"),
	SUBJECT_EXIST("400","課程已存在"),
	SUTUDENT_EXIST("400","學生已存在");
	
	
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
