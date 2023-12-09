package com.bbs.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bbs.project.model.Topic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TopicMapper extends BaseMapper<Topic> {
    @Insert("INSERT INTO TBL_TOPIC (title, content, publishTime, modifyTime, userId, boardId) VALUES " +
            "(#{title}, #{content}, #{publishTime}, #{modifyTime}, #{userId}, #{boardId})")
    boolean insertTopic(Topic topic);


    // 这里可以添加自定义的 SQL 方法
    // 比如根据需要查询特定条件的主题信息等
}
