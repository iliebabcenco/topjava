package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public synchronized Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        if (meal.getUserId() != repository.get(meal.getId()).getUserId()) {
            return null;
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public synchronized boolean delete(int id, int userId) {
        if (repository.get(id).getUserId() != userId)
            return false;
        return repository.remove(id) != null;
    }

    @Override
    public synchronized Meal get(int id, int userId) {
        if (repository.get(id).getUserId() != userId) {
            return null;
        }
        return repository.get(id);
    }

    @Override
    public synchronized List<Meal> getAll(int userId) {
        Stream<Meal> mealStream = repository.values().stream().filter(meal -> meal.getUserId() == userId);
        return mealStream.sorted(Comparator.comparing(Meal::getDateTime)).collect(Collectors.toList());
    }
}

