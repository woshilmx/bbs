package com.bbs.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bbs.project.model.Reply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyMapper extends BaseMapper<Reply> {
    // 这里可以添加自定义的 SQL 方法
    // 比如根据需要查询特定条件的回复信息等
}
