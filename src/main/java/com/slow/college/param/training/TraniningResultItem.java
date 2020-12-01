package com.slow.college.param.training;

import java.util.List;

import lombok.Data;

@Data
public class TraniningResultItem {
	
	private Integer id;
	
	private Integer achievement;
	
	private List<String> imgList;
	
	private String videoUrl;
	
	private String desc;
	
	private Integer score;

}
