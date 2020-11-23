package com.slow.college.model;

import lombok.Data;
/**
 * @description: TODO:用户选择的任务目标
 * @author：gy
 * @date 2020/11/23 10:59
 * @version 1.0
 **/
@Data
public class StudentHasTrainingtask {
	
	private Integer id;
	/**
	 * 学生id
	 */
	private Integer studentId;

	/**
	 * 任务id
	 */
	private Integer taskId;

	/**
	 * 目标数量
	 */
	private Integer target;

	public StudentHasTrainingtask() {
		// TODO Auto-generated constructor stub
	}

	public StudentHasTrainingtask(Integer id, Integer studentId, Integer taskId, Integer target) {
		this.id = id;
		this.studentId = studentId;
		this.taskId = taskId;
		this.target = target;
	}

}
