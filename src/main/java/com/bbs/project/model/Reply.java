package com.bbs.project.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Data
@Mapper
@TableName("TBL_REPLY")
public class Reply {
    private Integer replyId;
    private String title;
    private String content;
    private Date publishTime;
    private Date modifyTime;
    private Integer userId;
    private Integer topicId;
}
