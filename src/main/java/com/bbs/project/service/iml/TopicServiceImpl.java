package com.bbs.project.service.iml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bbs.project.mapper.TopicMapper;
import com.bbs.project.model.Topic;
import com.bbs.project.service.TopicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {
    @Resource
    private TopicMapper topicMapper;

    @Override
    public boolean saveTopic(Topic topic) {
return topicMapper.insertTopic(topic);

    }
}
