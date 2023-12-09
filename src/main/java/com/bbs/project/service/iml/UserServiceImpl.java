package com.bbs.project.service.iml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bbs.project.commen.ErrorCode;
import com.bbs.project.exception.BusinessException;
import com.bbs.project.mapper.UserMapper;
import com.bbs.project.model.User;
import com.bbs.project.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final String loggedInUser = "loggedInUser";

    @Override
    public User getCurrentUser(HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(loggedInUser);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return user;
    }

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean savUser(User user) {

        return userMapper.insertUser(user);
    }
}
