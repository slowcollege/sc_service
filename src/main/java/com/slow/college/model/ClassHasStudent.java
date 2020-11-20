package com.slow.college.model;

import lombok.Data;

@Data
public class ClassHasStudent {
	
	private Integer id;
	
	private Integer classId;
	
	private Integer studentId;

	public ClassHasStudent() {
		// TODO Auto-generated constructor stub
	}

	public ClassHasStudent(Integer id, Integer classId, Integer studentId) {
		this.id = id;
		this.classId = classId;
		this.studentId = studentId;
	}

}
