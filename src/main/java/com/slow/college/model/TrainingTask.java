package com.slow.college.model;

import lombok.Data;

@Data
public class TrainingTask {
	
	private Integer id;
	
	private String name;

	public TrainingTask() {
		// TODO Auto-generated constructor stub
	}

	public TrainingTask(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

}
