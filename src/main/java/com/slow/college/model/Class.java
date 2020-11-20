package com.slow.college.model;

import lombok.Data;

@Data
public class Class {
	
	private Integer id;
	
	private String name;
	
	private Integer monitorId;
	
	private Integer vicemonitorId;
	
	private String desc;

	public Class() {
		// TODO Auto-generated constructor stub
	}

	public Class(Integer id, String name, Integer monitorId, Integer vicemonitorId, String desc) {
		this.id = id;
		this.name = name;
		this.monitorId = monitorId;
		this.vicemonitorId = vicemonitorId;
		this.desc = desc;
	}

}
