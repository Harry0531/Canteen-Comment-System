package com.uml_review.uml.Comment.Entity;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.Date;

@Data
public class TComment {
    Integer commentId;
    Integer userId;
    Integer dishId;

    Integer likes;
    Double level;
    String content;
    String photo;
    Date time;
}
