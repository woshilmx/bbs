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
import com.bbs.project.model.TopicVo;
import com.bbs.project.model.User;
import com.bbs.project.service.ReplyService;
import com.bbs.project.service.TopicService;
import com.bbs.project.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.ReplayCache;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("topic")
public class TopicController {
    @Resource
    private TopicService topicService;

    @Resource
    private UserService userService;

    @Resource
    private ReplyService replyService;

    /**
     * 根据板块 ID 获取板块信息
     *
     * @param topicId 板块 ID
     * @return 返回板块信息的 BaseResponse
     */
    @GetMapping("/{topicId}")
    public BaseResponse<Topic> getTopicById(@PathVariable Integer topicId) {
        if (topicId == null || topicId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid topicId");
        }

        Topic topic = topicService.getById(topicId);
        if (topic != null) {
            return ResultUtils.success(topic);
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
    }

    /**
     * 创建板块
     *
     * @param topic 板块信息
     * @return 返回创建结果的 BaseResponse
     */
    @PostMapping("/create")
    public BaseResponse<String> createTopic(@RequestBody Topic topic) {
        if (topic == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Topic name cannot be empty");
        }

        topic.setPublishTime(new Date());
        topic.setModifyTime(new Date());
        boolean created = topicService.saveTopic(topic);
        if (created) {
            return ResultUtils.success("Topic created successfully");
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }

    /**
     * 更新板块信息
     *
     * @param topic 板块信息
     * @return 返回更新结果的 BaseResponse
     */
    @PutMapping("/update")
    public BaseResponse<String> updateTopic(@RequestBody Topic topic) {
        if (topic == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Topic information incomplete");
        }
        if (topic.getTopicId() == null || topic.getTopicId() == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Topic information incomplete");
        }

        boolean updated = topicService.updateById(topic);
        if (updated) {
            return ResultUtils.success("Topic updated successfully");
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }

    /**
     * 删除板块
     *
     * @param topicId 板块 ID
     * @return 返回删除结果的 BaseResponse
     */
    @DeleteMapping("/delete/{topicId}")
    public BaseResponse<String> deleteTopic(@PathVariable Integer topicId) {
        if (topicId == null || topicId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid topicId");
        }

        boolean removed = topicService.removeById(topicId);
        if (removed) {
            return ResultUtils.success("Topic deleted successfully");
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
    public BaseResponse<IPage<Topic>> listTopics(@RequestParam(defaultValue = "1") Integer current,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        if (current <= 0 || size <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid pagination parameters");
        }
        Page<Topic> page = new Page<>(current, size);
        topicService.page(page);
        return ResultUtils.success(page);
    }

    /**
     * 根据板块id获取主题
     *
     * @param boardId
     * @return
     */
    @GetMapping("board")
    public BaseResponse<List<TopicVo>> listTopicByBorderId(Integer boardId) {
        if (boardId == null || boardId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Topic::getBoardId, boardId);
        List<Topic> list = topicService.list(queryWrapper);


        List<TopicVo> topicVoList = list.stream().map(topic -> {
//            获取姓名
            TopicVo topicVo = new TopicVo();

            LambdaQueryWrapper<Reply> replyLambdaQueryWrapper = new LambdaQueryWrapper<>();
            replyLambdaQueryWrapper.eq(Reply::getTopicId, topic.getTopicId());
            long count = replyService.count(replyLambdaQueryWrapper);
            User one = userService.getById(topic.getUserId());
            topicVo.setTopicId(topic.getTopicId());
            topicVo.setTitle(topic.getTitle());
            topicVo.setUserName(one.getUserName());
            topicVo.setReplayCount(count);
            return topicVo;
        }).collect(Collectors.toList());


        return ResultUtils.success(topicVoList);
    }
}
