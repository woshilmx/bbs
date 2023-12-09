package com.bbs.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bbs.project.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Insert("INSERT INTO TBL_USER (userName, userPass, head, regTime, gender) VALUES (#{userName}, #{userPass}, #{head}, #{regTime}, #{gender})")
    boolean insertUser(User user);

    // 这里可以添加自定义的 SQL 方法
    // 比如根据需要查询特定条件的用户信息等
}
