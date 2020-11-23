package com.slow.college.param.training;

import lombok.Data;

@Data
public class WaitTrainingItem {
	
	private Long id;
	
	private String name;
	
	private String unit;
	
	private Integer target;
	
	private Byte state;
	
	private String stateDesc;
	
	private Integer achievement;

}
