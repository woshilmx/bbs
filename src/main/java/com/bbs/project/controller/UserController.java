package com.bbs.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbs.project.commen.BaseResponse;
import com.bbs.project.commen.ErrorCode;
import com.bbs.project.commen.ResultUtils;
import com.bbs.project.exception.BusinessException;
import com.bbs.project.model.User;
import com.bbs.project.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 根据板块 ID 获取板块信息
     *
     * @param userId 板块 ID
     * @return 返回板块信息的 BaseResponse
     */
    @GetMapping("/{userId}")
    public BaseResponse<User> getUserById(@PathVariable Integer userId) {
        if (userId == null || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid userId");
        }
        User user = userService.getById(userId);
        if (user != null) {
            return ResultUtils.success(user);
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
    }

    /**
     * 创建板块
     *
     * @param user 板块信息
     * @return 返回创建结果的 BaseResponse
     */
    @PostMapping("/create")
    public BaseResponse<String> createUser(@RequestBody User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "User name cannot be empty");
        }

        user.setRegTime(new Date());
        user.setUserId(null);
        boolean created = userService.savUser(user);
        if (created) {
            return ResultUtils.success("User created successfully");
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }

    /**
     * 更新板块信息
     *
     * @param user 板块信息
     * @return 返回更新结果的 BaseResponse
     */
    @PutMapping("/update")
    public BaseResponse<String> updateUser(@RequestBody User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "User information incomplete");
        }
        if (user.getUserId() == null || user.getUserId() == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "User information incomplete");
        }

        boolean updated = userService.updateById(user);
        if (updated) {
            return ResultUtils.success("User updated successfully");
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }

    /**
     * 删除板块
     *
     * @param userId 板块 ID
     * @return 返回删除结果的 BaseResponse
     */
    @DeleteMapping("/delete/{userId}")
    public BaseResponse<String> deleteUser(@PathVariable Integer userId) {
        if (userId == null || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid userId");
        }

        boolean removed = userService.removeById(userId);
        if (removed) {
            return ResultUtils.success("User deleted successfully");
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }

    /**
     * 分页查询板块列表
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 返回分页结果的 BaseResponse
     */
    @GetMapping("/list")
    public BaseResponse<IPage<User>> listUsers(@RequestParam(defaultValue = "1") Integer current,
                                               @RequestParam(defaultValue = "10") Integer size) {
        if (current <= 0 || size <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid pagination parameters");
        }
        Page<User> page = new Page<>(current, size);
        IPage<User> userPage = userService.page(page);
        return ResultUtils.success(userPage);
    }
}
