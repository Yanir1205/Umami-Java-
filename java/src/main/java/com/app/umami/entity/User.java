package com.app.umami.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class User {

    @Id
    private String id;

    private String fullName;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String imgUrl;

}
