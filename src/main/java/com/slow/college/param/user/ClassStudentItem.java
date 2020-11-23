package com.slow.college.param.user;

import java.util.List;

import lombok.Data;

@Data
public class ClassStudentItem {
	
	private Integer classId;
	
	private String className;
	
	private String classDesc;
	
	private String date;
	
	private List<StudentClassItem> student;

}
