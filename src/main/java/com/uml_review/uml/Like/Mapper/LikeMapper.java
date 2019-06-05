package com.uml_review.uml.Like.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LikeMapper {

    @Select("select * from likee where `user_id`=#{param1} and `type_id`=#{param2} and `type`=2")
    Integer query_comment(Integer userId,Integer typeId);

    @Select("select * from likee where `user_id`=#{param1} and `type_id`=#{param2} and `type`=1")
    Integer query_dish(Integer userId,Integer typeId);

    @Select("Insert into likee (`user_id`,`type_id`,`type`)values(#{param1},#{param2},#{param3});\n" +
            "select last_insert_id() ")
    Integer add_like(Integer userId,Integer typeId,Integer type);

    @Delete("delete  from likee where `user_id`=#{param1} and `type_id`=#{param2} and `type`=#{param3}")
    Integer delete_like(Integer userId,Integer typeId,Integer type);

    @Update("update comments set `likes`=`likes`+1 where `comment_id`=#{param1}")
    Integer update_comment_add(Integer comment_id);

    @Update("update comments set `likes`=`likes`-1 where `comment_id`=#{param1}")
    Integer update_comment_sub(Integer comment_id);

    @Update("update menu set `likes`=`likes`+1 where `dish_id`=#{param1}")
    Integer update_dish_add(Integer comment_id);

    @Update("update menu set `likes`=`likes`-1 where `dish_id`=#{param1}")
    Integer update_dish_sub(Integer dish_id);

    @Select("select `type_id` from likee where `user_id`=#{param1}  and `type`=#{param2}")
    List<Integer> query_like(Integer user_id,Integer type);
}
