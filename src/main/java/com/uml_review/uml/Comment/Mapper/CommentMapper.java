package com.uml_review.uml.Comment.Mapper;

import com.uml_review.uml.Comment.Entity.Comment;
import com.uml_review.uml.Comment.Entity.TComment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("Insert into comments (`user_id`,`dish_id`,`level`,`content`,`photo`)\n" +
            "\t\t\t\tvalues (#{userId},#{dishId},#{level},#{content},#{photo});\n" +
            "\t\t\t\tselect last_insert_id()")
    Integer add(Comment comment);


    @Select("select * from comments where `user_id`=#{param1} and `comment_id`=#{param2}")
    Integer verification(Integer userId,Integer commentId);

    @Delete("delete from comments where `comment_id`=#{param1}")
    Integer delete(Integer commentId);

    @Select("select `comment_id`as\"commentId\",\n" +
            "\t\t`dish_id`as \"dishId\",\n" +
            "\t\t`user_id`as \"userId\",\n" +
            "\t\t`level`,`content`,`photo`,`likes`,`time` \n" +
            "\t\tfrom comments where user_id=#{param1} order by `level` desc")
    List<TComment> query_User(Integer userId);

    @Select("select `comment_id`as\"commentId\",\n" +
            "\t\t`dish_id`as \"dishId\",\n" +
            "\t\t`user_id`as \"userId\",\n" +
            "\t\t`level`,`content`,`photo`,`likes`,`time` \n" +
            "\t\tfrom comments where dish_id=#{param1} order by `level` desc")
    List<TComment> query_Dish(Integer dishId);
}
