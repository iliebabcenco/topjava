package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        Meal resultMeal = repository.save(meal);
        if (resultMeal == null) {
            throw new NotFoundException("No such meal to save");
        }
        return resultMeal;
    }

    public void delete(int id, int userId) {
        boolean toDelete = repository.delete(id, userId);
        if (!toDelete) {
            throw new NotFoundException("No such meal to delete");
        }
        checkNotFoundWithId(toDelete, id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Meal meal) {
        Meal mealToSave = repository.save(meal);
        if (mealToSave == null) {
            throw new NotFoundException("No such meal to save");
        }
        checkNotFoundWithId(mealToSave, meal.getId());
    }

}