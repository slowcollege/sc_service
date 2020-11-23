package com.slow.college.service.impl;

import com.slow.college.exception.ScBizException;
import com.slow.college.mapper.StudentMapper;
import com.slow.college.param.item.UserItem;
import com.slow.college.param.response.BaseRsp;
import com.slow.college.param.response.StudentProFileRsp;
import com.slow.college.param.scenum.RspEnum;
import com.slow.college.request.UserReq;
import com.slow.college.service.UserServiceV2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImplV2 implements UserServiceV2 {

    private static final Logger log = LogManager.getLogger(UserServiceImplV2.class);

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public BaseRsp getStudentProfile(UserReq req) throws ScBizException {
        if (req.getToken() == null) {
            return BaseRsp.fail(RspEnum.USER_UNEXISTS);
        }
        UserItem s = studentMapper.selectUserItemByToken(req.getToken().trim());
        if (s == null) {
            return BaseRsp.fail(RspEnum.USER_UNEXISTS);
        }
        StudentProFileRsp resp = new StudentProFileRsp(s.getName(),s.getClassName(),s.getCode(),s.getImage(),s.getScore());
       //保存返回信息
        return BaseRsp.success(resp);
    }


}
