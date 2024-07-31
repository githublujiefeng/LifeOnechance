package com.learn.example.springclouddemo.mockMVC;

import com.learn.restful.bean.User;
import com.learn.restful.controller.UserController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.swing.*;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class restfulTest {
    private MockMvc mvc;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }
    @Test
    public void testUserController() throws Exception {
        //测试UserController
        RequestBuilder requestBuilder;

        requestBuilder = get("/users/");
        //1、get查一下user列表，应该为空
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));

        //2、post提交一个user
        requestBuilder = post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content("{\"id\":1,\"name\":\"测试大师\",\"age\":20}");
        mvc.perform(requestBuilder)
                .andExpect(content().string(equalTo("success")));
        //3、get获取user列表
        requestBuilder = get("/users/");
        String res =mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                //.andDo()
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString(Charset.defaultCharset());//将结果变为得非乱码string
        System.out.println(res);
        //4、put修改id为1的user
        requestBuilder = put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"卢杰峰\",\"age\":150}");
        mvc.perform(requestBuilder).andExpect(content().string(equalTo("success")));
        //5、get一个id为1的user
        requestBuilder = get("/users/1");
        ResultActions actions =  mvc.perform(requestBuilder)
                .andExpect(status().isOk());
                //.andExpect(content().string(equalTo("{\"id\":1,\"name\":\"卢杰峰\",\"age\":150}")))
                //.andDo(MockMvcResultHandlers.print())
                //.andReturn();
        actions.andReturn().getResponse().setCharacterEncoding("utf-8");//获取结果的action，并对action的结果集进行编码处理
        actions.andDo(MockMvcResultHandlers.print());
        //6、del删除id为1的user
        requestBuilder = delete("/users/1");
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("success")));
    }
}
