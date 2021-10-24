package com.app.umami.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.BasicDBObject;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "meal")
public class Meal {

    @Id
    @JsonProperty(value = "_id")
    private String id;

    private Boolean isActive;

    private Boolean isPromoted;

    private String cuisineType;

    private String mealType;

    private String title;

    private Integer price;

    private Integer capacity;

    private String currency;

    private String description;

    private List<BasicDBObject> occurrences;

    private List<String> tags;

    private BasicDBObject location;

    private List<String> imgUrls;

    private BasicDBObject hostedBy;

    private List<BasicDBObject> reviews;

    private BasicDBObject menu;

    @Transient
    private Object total;

    @Transient
    private Object date;

    @Transient
    private Object occurensId;

    @Transient
    private Boolean isHosted;
}
