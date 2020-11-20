package com.slow.college.model;

import lombok.Data;

@Data
public class StudentHasTrainingtask {
	
	private Integer id;
	
	private Integer studentId;
	
	private Integer taskId;
	
	private Integer target;

	public StudentHasTrainingtask() {
		// TODO Auto-generated constructor stub
	}

	public StudentHasTrainingtask(Integer id, Integer studentId, Integer taskId, Integer target) {
		this.id = id;
		this.studentId = studentId;
		this.taskId = taskId;
		this.target = target;
	}

}
