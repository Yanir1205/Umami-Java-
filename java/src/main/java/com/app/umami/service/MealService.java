package com.app.umami.service;

import com.app.umami.entity.Meal;
import com.app.umami.exception.InternalServerException;
import com.app.umami.exception.ResourceNotFoundException;
import com.app.umami.repository.MealRepository;
import com.mongodb.BasicDBObject;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MealService {

    private final MealRepository mealRepository;

    public Meal addMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public Meal updateMeal(String id, Meal meal) {
        Meal oldMeal = mealRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Meal not found"));

        oldMeal = meal;
        return mealRepository.save(oldMeal);
    }

    public Meal getMeal(String id) {
        return mealRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Meal not found"));
    }

    public List<Meal> getAllMeals(String userId, String type, String tags, String city, String country, String group, String meal) {
        List<Meal> meals = null;

        try {
            if (StringUtils.isNotBlank(userId)) {
                meals = mealRepository.findMealsByUserId(userId);
                meals = getMeals(userId, meals);
            } else if (StringUtils.isNotBlank(type)) {
                meals = mealRepository.findMealsByType(type);
            } else if (StringUtils.isNotBlank(tags)) {
                meals = mealRepository.findMealsByTags(tags);
            }else if (StringUtils.isNotBlank(city)) {
                meals = mealRepository.findMealsByCity(city);
            }else if (StringUtils.isNotBlank(country)) {
                meals = mealRepository.findMealsByCountry(country);
            }else if (StringUtils.isNotBlank(group)) {
                group = group.substring(1);
                if (StringUtils.isNotBlank(meal)) {
                    meals = mealRepository.findAll();
                } else {
                    meals = mealRepository.findMealsByGroup(group);
                }
            } else {
                meals = mealRepository.findAll();
            }
        } catch (Exception e) {
            throw new InternalServerException("Error occurred while getting all meals" + e.getMessage());
        }

        return meals;
    }

    private List<Meal> getMeals(String userId, List<Meal> meals) {
        Object occurrensId = null;
        Object date = null;
        Object total = null;

        for (Meal meal : meals) {
            BasicDBObject hostedBy = meal.getHostedBy();
            String id = (String) hostedBy.get("_id");

            if (StringUtils.equals(id, userId)) {
                meal.setIsHosted(Boolean.TRUE);
            } else {
                meal.setIsHosted(Boolean.FALSE);
            }

            for (BasicDBObject occurrence : meal.getOccurrences()) {
                List<Object> attendees = (List<Object>) occurrence.get("attendees");

                for (Object attendee : attendees) {
                    Map<String, Object> map = (Map<String, Object>) attendee;
                    if (StringUtils.equals(userId, (String) map.get("_id"))) {
                        total = map.get("numOfAttendees");
                        date = occurrence.get("date");
                        occurrensId = occurrence.get("id");
                        break;
                    }
                }
            }

            meal.setOccurensId(occurrensId);
            meal.setDate(date);
            meal.setTotal(total);
        }

        return meals;
    }

    public void deleteMeal(String id) {
        Meal meal = mealRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Meal not found"));
        mealRepository.delete(meal);
    }

}
