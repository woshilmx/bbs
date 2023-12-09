package com.bbs.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bbs.project.mapper.ReplyMapper;
import com.bbs.project.mapper.TopicMapper;
import com.bbs.project.model.Topic;

public interface TopicService extends IService<Topic> {
    boolean saveTopic(Topic topic);
}
