package com.bbs.project;

import com.bbs.project.mapper.UserMapper;
import com.bbs.project.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class UserTest {
    @Resource
    private UserMapper userMapper;


    @Test
    public void getAllUser() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
}
