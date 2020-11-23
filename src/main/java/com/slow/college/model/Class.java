package com.slow.college.model;

import lombok.Data;
/**
 * @description: TODO:班级类
 * @author：gy
 * @date 2020/11/23 10:36
 * @version 1.0
 **/
@Data
public class Class {

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 班级名
	 */
	private String name;

	/**
	 * 班长id
	 */
	private Integer monitorId;

	/**
	 * 副班长id
	 */
	private Integer vicemonitorId;

	/**
	 * 班级口号
	 */
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
