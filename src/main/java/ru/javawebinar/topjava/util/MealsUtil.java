package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int caloriesPerDay = 600;



    public static void main(String[] args) {
        List<Meal> meals = createMeals();
        List<MealTo> mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<Meal> createMeals() {
        return Arrays.asList(
                new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 10, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 10, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 10, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 10, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 11, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 11, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 11, 20, 0), "Ужин", 410),
                new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 11, 20, 0), "Ужин", 410),
                new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 11, 20, 0), "Мясо по-французски", 2001),
                new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 11, 20, 0), "Шаурма", 1500),
                new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 11, 20, 0), "Бигмак", 1500),
                new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 11, 20, 0), "Курица", 800)
        );
    }

    public static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
