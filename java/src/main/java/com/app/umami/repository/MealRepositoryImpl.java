package com.app.umami.repository;

import com.app.umami.entity.Meal;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@AllArgsConstructor
public class MealRepositoryImpl implements MealRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Meal> findMealsByTags(String tags) {
        Query query = new Query();
        query.addCriteria(where("tags").in(tags));
        return mongoTemplate.find(query, Meal.class);
    }

    @Override
    public List<Meal> findMealsByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(where("hostedBy._id").is(userId),
                where("occurrences").ne(null).andOperator(where("occurrences").elemMatch(where("attendees._id").is(userId)))));
        /*query.fields().include("isActive", "isPromoted", "cuisineType", "mealType", "title", "price", "currency",
                "capacity", "tags", "location", "imgUrls", "hostedBy", "description", "reviews", "menu" +
                        "").elemMatch("occurrences", where("attendees._id").is(userId));*/
        return mongoTemplate.find(query, Meal.class);
    }

    @Override
    public List<Meal> findMealsByType(String type) {
        Query query = new Query();
        query.addCriteria(where("cuisineType").is(type));
        return mongoTemplate.find(query, Meal.class);
    }

    @Override
    public List<Meal> findMealsByCity(String city) {
        Query query = new Query();
        query.addCriteria(where("location.city").is(city));
        return mongoTemplate.find(query, Meal.class);
    }

    @Override
    public List<Meal> findMealsByCountry(String country) {
        Query query = new Query();
        query.addCriteria(where("location.country").is(country));
        return mongoTemplate.find(query, Meal.class);
    }

    @Override
    public List<Meal> findMealsByGroup(String group) {
        TypedAggregation agg = newAggregation(Meal.class, group(group));
        AggregationResults<Meal> groupResults = mongoTemplate.aggregate(agg, Meal.class);
        return groupResults.getMappedResults();
    }
}
