package com.app.umami.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "country")
public class Country {

    @Id
    private String id;

    private String city;

    private String country;
}
