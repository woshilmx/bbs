package com.bbs.project.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("TBL_USER")
@Data
public class User {
    @TableId(type = IdType.NONE)
    private Integer userId;
    private String userName;
    private String userPass;
    private String head;
    private Date regTime;
    /**
     * 1男 2女
     */
    private Integer gender;
}
