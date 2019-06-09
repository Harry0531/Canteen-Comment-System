package com.uml_review.uml.Login.Mapper;

import com.uml_review.uml.Login.Entity.Key;
import com.uml_review.uml.Login.Entity.Login;
import com.uml_review.uml.Login.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface LoginMapper {

    @Select("select  count(`user_id`) from `user` where `username`=#{param1}or`email`=#{param1} ")
    Integer nameVeri(String name);

    @Select("Insert into `user`(`username`,`password`,`email`)values(#{username},#{password},#{email});\n" +
            "select last_insert_id() as aa;\n")
    Integer register(Login param);

    @Insert("Insert into person (`user_id`,`nickname`)values(#{param1},#{param2})")
    Integer add_info(Integer userId,String username);

    @Select("select `user_id` as \"userId\",`password`,`firstLogin` from user where `username`=#{param1} or `email`=#{param1};")
    Key login(String str);

    @Select("select `user_id` as \"userId\",\n" +
            "\t\t\t`username` ,`password`\n" +
            "\t\t\tfrom `user` where `user_id`=#{param1};")
    User findUserById(Integer userId);

    @Update("update user set `firstLogin`=0 where `user_id` = #{param1}")
    Integer update_first(Integer userId);
}
