package com.bbs.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bbs.project.mapper.TopicMapper;
import com.bbs.project.model.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {
    User getCurrentUser(HttpServletRequest request);

    /**
     * 保存用户
     * @param user
     * @return
     */
    boolean savUser(User user);
}
