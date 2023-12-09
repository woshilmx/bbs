package com.bbs.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbs.project.commen.BaseResponse;
import com.bbs.project.commen.ErrorCode;
import com.bbs.project.commen.ResultUtils;
import com.bbs.project.exception.BusinessException;
import com.bbs.project.model.Reply;
import com.bbs.project.model.Topic;
import com.bbs.project.service.ReplyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("reply")
public class ReplyController {
    @Resource
    private ReplyService replyService;

    /**
     * 根据板块 ID 获取板块信息
     *
     * @param replyId 板块 ID
     * @return 返回板块信息的 BaseResponse
     */
    @GetMapping("/{replyId}")
    public BaseResponse<Reply> getReplyById(@PathVariable Integer replyId) {
        if (replyId == null || replyId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid replyId");
        }

        Reply reply = replyService.getById(replyId);
        if (reply != null) {
            return ResultUtils.success(reply);
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
    }

    /**
     * 创建板块
     *
     * @param reply 板块信息
     * @return 返回创建结果的 BaseResponse
     */
    @PostMapping("/create")
    public BaseResponse<String> createReply(@RequestBody Reply reply) {
        if (reply == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Reply name cannot be empty");
        }

        reply.setPublishTime(new Date());
        reply.setModifyTime(new Date());
        boolean created = replyService.save(reply);
        if (created) {
            return ResultUtils.success("Reply created successfully");
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }

    /**
     * 更新板块信息
     *
     * @param reply 板块信息
     * @return 返回更新结果的 BaseResponse
     */
    @PutMapping("/update")
    public BaseResponse<String> updateReply(@RequestBody Reply reply) {
        if (reply == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Reply information incomplete");
        }
        if (reply.getReplyId() == null || reply.getReplyId() == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Reply information incomplete");
        }

        boolean updated = replyService.updateById(reply);
        if (updated) {
            return ResultUtils.success("Reply updated successfully");
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }

    /**
     * 删除板块
     *
     * @param replyId 板块 ID
     * @return 返回删除结果的 BaseResponse
     */
    @DeleteMapping("/delete/{replyId}")
    public BaseResponse<String> deleteReply(@PathVariable Integer replyId) {
        if (replyId == null || replyId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid replyId");
        }

        boolean removed = replyService.removeById(replyId);
        if (removed) {
            return ResultUtils.success("Reply deleted successfully");
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
    public BaseResponse<IPage<Reply>> listReplys(@RequestParam(defaultValue = "1") Integer current,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        if (current <= 0 || size <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid pagination parameters");
        }
        Page<Reply> page = new Page<>(current, size);
        IPage<Reply> replyPage = replyService.page(page);
        return ResultUtils.success(replyPage);
    }


    /**
     * 根据主题id回复帖子
     *
     * @param topicId
     * @return
     */
    @GetMapping("topic")
    public BaseResponse<Page<Reply>> listTopicByBorderId(Integer topicId, long current, long size) {
        if (topicId == null || topicId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<Reply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Reply::getTopicId, topicId);
        Page<Reply> page = new Page<>(current, size);
        replyService.page(page, queryWrapper);
        if (page.getRecords()==null || page.getRecords().isEmpty()){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"页数超出范围");
        }
        return ResultUtils.success(page);
    }
}
