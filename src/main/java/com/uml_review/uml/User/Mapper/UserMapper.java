package com.uml_review.uml.User.Mapper;

import com.uml_review.uml.User.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select `user_id` as \"userId\",\n" +
            "\t\t`nickname`,`avatar`,`motto` from person\n" +
            "\t\twhere `user_id`=#{param1}")
    User user_info(Integer userId);

    @Update("update person set `nickname`=#{nickname},`avatar`=#{avatar},motto=#{motto} where `user_id`=#{userId}")
    Integer user_Update(User user);
}
