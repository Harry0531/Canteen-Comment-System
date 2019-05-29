package com.uml_review.uml.Comment.Entity;

import lombok.Data;

@Data
public class Comment {

    Integer userId;
    Integer dishId;

    Double level;
    String content;
    String photo;
}
