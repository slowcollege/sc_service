package com.slow.college.model;

import lombok.Data;

@Data
public class Training {
	
	private Long id;
	
	private Integer studentHasTrainingtaskId;
	
	private String date;
	
	private String desc;
	
	private Byte done;
	
	private String video;

	private Integer achievement;
	
	private String unit;
	
	private Integer score;

	public Training() {
		// TODO Auto-generated constructor stub
	}

	public Training(Long id, Integer studentHasTrainingtaskId, String date, String desc, Byte done, String video,
			Integer achievement, String unit, Integer score) {
		this.id = id;
		this.studentHasTrainingtaskId = studentHasTrainingtaskId;
		this.date = date;
		this.desc = desc;
		this.done = done;
		this.video = video;
		this.achievement = achievement;
		this.unit = unit;
		this.score = score;
	}

}
