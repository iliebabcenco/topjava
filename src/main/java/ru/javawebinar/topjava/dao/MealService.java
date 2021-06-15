package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MealService {

    private static MealService instance = new MealService();

    public static MealService getInstance() {
        if (instance == null) {
            return new MealService();
        }
        return instance;
    }

    private MealService() {
    }

    public List<MealTo> getMeals() {
        List<Meal> listOfMeals = new ArrayList<>();
        listOfMeals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        listOfMeals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        listOfMeals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        listOfMeals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        listOfMeals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        listOfMeals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        listOfMeals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        List<MealTo> listOfMealsTo = new ArrayList<>();
        int localExcess = 0;
        for (Meal meal: listOfMeals) {
            localExcess += meal.getCalories();
            listOfMealsTo.add(MealsUtil.createTo(meal, localExcess > MealTo.caloriesPerDay));
        }
        return listOfMealsTo;
    }

}
