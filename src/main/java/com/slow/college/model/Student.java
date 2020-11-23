package com.slow.college.model;

import lombok.Data;

/**
 * @version 1.0
 * @description: TODO:学生表
 * @author：gy
 * @date 2020/11/23 10:44
 **/
@Data
public class Student {
    /**
     * id
     */
    private Integer id;

    /**
     * 学生名
     */
    private String name;

    /**
     * 学生电话
     */
    private String phone;
    /**
     * 学生编号
     */
    private String code;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 头像
     */
    private String image;

    /**
     *学分
     */
    private Integer score;

    /**
     * 登录凭证
     */
    private String token;

    /**
     * 注册时间
     */
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
