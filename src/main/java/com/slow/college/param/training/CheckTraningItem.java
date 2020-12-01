package com.slow.college.param.training;

import lombok.Data;

@Data
public class CheckTraningItem {
	
	private Integer taskId;
	
	private Integer target;
	
	private Byte done;		//完成状态0未完成1已完成2完成不达标
	
	private Integer trainingtaskId;
	
	private Integer achievement;	//已完成数量
	
	private String desc;	//文字凭证
	
	private String video;	//视频地址，需覆盖
	
	private String unit;	//单位
	
	private Long dayTrainingtaskId;	//当日打卡记录id
	
	private String createTime;
	
	private Integer score;	//可获得分数

}
