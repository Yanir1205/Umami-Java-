package com.app.umami.repository;

import com.app.umami.entity.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MealRepository extends MongoRepository<Meal, String>, MealRepositoryCustom {
}
