package com.uml_review.uml.Menus.Entity;

import lombok.Data;

@Data
public class Dish {

    Integer dishId;

    String name;
    String avatar;

    Double level;
    Double price;


    String loc_Canteen;
    String loc_Floor;
    String loc_Window;

    String discription;

    Integer likes;
    Integer comments;

    Integer like_me;
}
