package com.slow.college.param.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName:StudentProFileRsp
 * @Description TODO
 * @Author: gy
 * @Date: 2020/11/23 14:46
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
public class StudentProFileRsp {

    private String name;

    private String className;

    private String code;

    private String image;

    private  Integer score;



}
