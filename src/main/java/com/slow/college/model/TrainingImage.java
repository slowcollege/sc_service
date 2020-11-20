package com.slow.college.model;

import lombok.Data;

@Data
public class TrainingImage {
	
	private Integer id;
	
	private Long TrainingId;
	
	private String imageUrl;

	public TrainingImage() {
		// TODO Auto-generated constructor stub
	}

	public TrainingImage(Integer id, Long trainingId, String imageUrl) {
		this.id = id;
		TrainingId = trainingId;
		this.imageUrl = imageUrl;
	}

}
