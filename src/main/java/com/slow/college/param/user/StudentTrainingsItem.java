package com.slow.college.param.user;

import lombok.Data;

@Data
public class StudentTrainingsItem {
	
	private Integer id;
	
	private String name;
	
	private Byte state;
	
	private String stateDesc;
	
	private Integer achievement;
	
	private String unit;
	
	private String[] imgList;
	
	private String videoUrl;
	
	private String desc;

}
