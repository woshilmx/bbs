package com.bbs.project.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("TBL_BOARD") // 指定实体类对应的数据库表名
public class Board {

    private Integer boardId;
    private String boardName;
    private Integer parentId;

    // Getters and setters
    // ...

    // (可选) toString() 方法
    // ...
}
