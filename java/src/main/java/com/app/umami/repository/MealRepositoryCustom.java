package com.app.umami.repository;

import com.app.umami.entity.Meal;

import java.util.List;

public interface MealRepositoryCustom {

    public List<Meal> findMealsByTags(String tags);

    public List<Meal> findMealsByUserId(String userId);

    public List<Meal> findMealsByType(String type);

    public List<Meal> findMealsByCity(String city);

    public List<Meal> findMealsByCountry(String country);

    public List<Meal> findMealsByGroup(String group);

}
