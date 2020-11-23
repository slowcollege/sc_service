package com.slow.college.model;

import lombok.Data;

/**
 * @version 1.0
 * @description: TODO:班级-学生关联表
 * @author：gy
 * @date 2020/11/23 10:38
 **/
@Data
public class ClassHasStudent {

    /**
     * id
     */
    private Integer id;

    /**
     * 班级id
     */
    private Integer classId;

    /**
     * 学生id
     */
    private Integer studentId;

    public ClassHasStudent() {
        // TODO Auto-generated constructor stub
    }

    public ClassHasStudent(Integer id, Integer classId, Integer studentId) {
        this.id = id;
        this.classId = classId;
        this.studentId = studentId;
    }

}
