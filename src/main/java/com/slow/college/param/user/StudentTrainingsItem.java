package com.slow.college.param.user;

import java.util.List;

import lombok.Data;

@Data
public class StudentTrainingsItem {
	
	private Integer studentId;
	
	private Integer id;
	
	private String name;
	
	private Byte state;
	
	private String stateDesc;
	
	private Integer achievement;
	
	private Integer target;
	
	private String unit;
	
	private List<String> imgList;
	
	private String videoUrl;
	
	private String desc;
	
	private Integer trainingId;

}
