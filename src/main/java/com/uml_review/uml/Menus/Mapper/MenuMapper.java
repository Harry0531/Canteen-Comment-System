package com.uml_review.uml.Menus.Mapper;

import com.uml_review.uml.Menus.Entity.Dish;
import com.uml_review.uml.Menus.Entity.Str;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Select("Insert into menu (`name`,`avatar`,`level`,`price`,`loc_canteen`,`loc_floor`,`loc_window`,`discription`)\n" +
            "\t\t\tvalues(#{name},#{avatar},#{level},#{price},#{loc_Canteen},#{loc_Floor},#{loc_Window},#{discription});\t\t\n" +
            "\t\t\tselect last_insert_id() as aa;")
    Integer add(Dish dish);

    @Select("select `dish_id`as \"dishId\",\n" +
            "\t\t`name`,`avatar`,`level`,`price`,\n" +
            "\t\t`loc_canteen`as\"loc_Canteen\",\n" +
            "\t\t`loc_floor`as \"loc_Floor\",\n" +
            "\t\t`loc_window`as \"loc_Window\",\n" +
            "\t\t`discription`\n,`likes`,`comments`" +
            "\t\tfrom menu where `dish_id`=#{param1};")
    Dish info(Integer dishId);

    @Update("UPDATE menu SET `name`=#{name}," +
            "\t\t\t\t`avatar`=#{avatar}," +
            "\t\t\t\t`level`=#{level}," +
            "\t\t\t\t`price`=#{price}," +
            "\t\t\t\t`loc_canteen`=#{loc_Canteen}," +
            "\t\t\t\t`loc_floor`=#{loc_Floor}," +
            "\t\t\t\t`loc_window`=#{loc_Window}," +
            "\t\t\t\t`discription`=#{discription}" +
            "\t\t\t\tWHERE `dish_id`=#{dishId}")
    Integer update(Dish dish);


    @Select("SELECT dish_id as \"dishId\",\n" +
            "\t\t`name`,`avatar`,`discription`,`price`,`level`,`likes`,`comments`,\n" +
            "\t\t`loc_canteen`as \"loc_Canteen\",\n" +
            "\t\t`loc_floor`as \"loc_Floor\",\n" +
            "\t\t`loc_window`as \"loc_Window\"  FROM menu WHERE `name` like \"${words}\" order by `level`desc")
    List<Dish> query(Str str);

    @Delete("delete from menu where `dish_id`=#{param1}")
    Integer dish_delete(Integer dishId);
}
