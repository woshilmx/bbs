package com.bbs.project.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("TBL_TOPIC")
public class TopicVo {
    private Integer topicId;
    private String title;
    private String userName;
    private Long replayCount;
}
