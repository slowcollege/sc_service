package com.slow.college.model;

import lombok.Data;

@Data
public class Student {
	
	private Integer id;
	
	private String name;
	
	private String phone;
	
	private String code;
	
	private String password;
	
	private String image;
	
	private Integer score;
	
	private String token;
	
	private String createTime;

	public Student() {
		// TODO Auto-generated constructor stub
	}

	public Student(Integer id, String name, String phone, String code, String password, String image, Integer score,
			String token, String createTime) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.code = code;
		this.password = password;
		this.image = image;
		this.score = score;
		this.token = token;
		this.createTime = createTime;
	}

}
