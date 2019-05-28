package com.uml_review.uml.Login.Entity;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class Login {

    @NotNull
    @Length(max=16)
    String username;

    @NotNull
    @Length(max=16)
    String password;

    @NotNull
    String email;
}
