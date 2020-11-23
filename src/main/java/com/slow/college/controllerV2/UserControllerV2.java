package com.slow.college.controllerV2;

import com.slow.college.exception.ScBizException;
import com.slow.college.param.response.BaseRsp;
import com.slow.college.param.user.UserLoginItem;
import com.slow.college.request.UserReq;
import com.slow.college.response.ObjectResponse;
import com.slow.college.service.UserService;
import com.slow.college.service.UserServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1/user", produces = "application/json; charset=UTF-8")
public class UserControllerV2 {

    @Autowired
    private UserServiceV2 userService;

    @RequestMapping(value = "/getStudentProfile", method = RequestMethod.POST)
    public BaseRsp getStudentProfile(UserReq req) throws ScBizException {
        return userService.getStudentProfile(req);
    }

}
