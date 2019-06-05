package com.uml_review.uml.Login.Entity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    String userId;
    String username;
    String password;

    public  String getToken(User user){
        String token="";
        token = JWT.create().withAudience(user.getUserId()).withExpiresAt(new Date(System.currentTimeMillis()+60*60*1000)).sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
