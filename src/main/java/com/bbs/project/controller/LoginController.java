package com.bbs.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bbs.project.commen.BaseResponse;
import com.bbs.project.commen.ErrorCode;
import com.bbs.project.commen.ResultUtils;
import com.bbs.project.exception.BusinessException;
import com.bbs.project.model.User;
import com.bbs.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserService userService;

    private static final String loggedInUser = "loggedInUser";


    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @GetMapping
    public BaseResponse<User> getLoginUser(HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        return ResultUtils.success(user);
    }


    /**
     * 登录w
     *
     * @param user
     * @param request
     * @return
     */
    @PostMapping(value = "")
    public BaseResponse<String> login( User user, HttpServletRequest request) {
        if (user == null || user.getUserName() == null || user.getUserPass() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid username or password");
        }

        // 根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, user.getUserName()).eq(User::getUserPass, user.getUserPass());
        User existingUser = userService.getOne(queryWrapper);

        if (existingUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "Invalid username or password");
        }

        // 登录成功，将用户信息保存到 Session 中
        HttpSession session = request.getSession();

        session.setAttribute(loggedInUser, existingUser);

        return ResultUtils.success("Login successful");
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 获取当前会话，如果不存在则不创建新会话

        if (session != null) {
            session.removeAttribute(loggedInUser); // 清除会话信息
        }

        return ResultUtils.success("Logout successful");
    }
}
