package com.slow.college;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MockMvcTests {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    private static final Logger log = LogManager.getLogger(MockMvcTests.class);

    @Before
    public void setUp() {
        // MockMvc Builders使用构建MockMvc对象（项目拦截器有效）
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void getStudentProfile() throws Exception {
        String url = "/api/v1/user/getStudentProfile";
        String token = "2";
        MvcResult result = this.mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("token", token)
        )
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andReturn();
        //Assert.assertNotNull(result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getContentAsString());
        Assert.assertTrue("结果集存在", result.getResponse().getContentAsString().contains("result"));//结果不符合判定就会打印错误
    }


}

