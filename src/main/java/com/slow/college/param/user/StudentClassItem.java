package com.slow.college.param.user;

import java.util.List;

import lombok.Data;

@Data
public class StudentClassItem {
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	private String duty;
	
	private Integer trainingDays;
	
	//private String createTime;
	
	private Integer score;
	
	private List<StudentTrainingsItem> trainings;

}
