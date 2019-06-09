package com.uml_review.uml.Login.Entity;

import lombok.Data;

@Data
public class Key {
    Integer userId;
    String password;
    Integer firstLogin;
}
