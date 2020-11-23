package com.slow.college.param.item;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName:UserItem
 * @Description TODO
 * @Author: gy
 * @Date: 2020/11/23 15:27
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
public class UserItem   {
    /**
     * id
     */
    private Integer id;

    /**
     * 学生名
     */
    private String name;

    /**
     * 学生编号
     */
    private String code;

    /**
     * 头像
     */
    private String image;

    /**
     *学分
     */
    private Integer score;

    /**
     * 班级名称
     */
    private String className;



}
