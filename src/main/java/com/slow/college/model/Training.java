package com.slow.college.model;

import lombok.Data;
/**
 * @description: TODO：学生每日指定任务完成情况
 * @author：gy
 * @date 2020/11/23 11:01
 * @version 1.0
 **/
@Data
public class Training {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 学生选择的任务
	 */
	private Integer studentHasTrainingtaskId;

	/**
	 * 打卡日期
	 */
	private String date;

	/**
	 * 备注
	 */
	private String desc;

	/**
	 * 完成情况：0：未完成；1：已完成；2：完成不达标
	 */
	private Byte done;
	/**
	 * 上传视频地址
	 */
	private String video;
	/**
	 * 完成数量
	 */
	private Integer achievement;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 来源
	 */
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
