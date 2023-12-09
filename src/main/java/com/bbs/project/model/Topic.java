package com.bbs.project.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Data
@TableName("TBL_TOPIC")
public class Topic {
    @TableId(type = IdType.NONE)
    private Integer topicId;
    private String title;
    private String content;
    private Date publishTime;
    private Date modifyTime;
    private Integer userId;
    private Integer boardId;
}
