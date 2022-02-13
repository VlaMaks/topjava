package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/")
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    public static final List<MealTo> mealsTo = new CopyOnWriteArrayList<>();
    private int currentMealIndex;

    static {
        List<Meal> meal = MealsUtil.createMeals();
        for (Meal m : meal) {
            mealsTo.add(new MealTo(m.getDateTime(), m.getDescription(), m.getCalories(), m.getCalories() > MealsUtil.caloriesPerDay));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        switch(action) {
            case "/new":
                showNewForm(req, resp);
                break;
            case "/insert":
                insertMeal(req, resp);
                break;
            case "/delete":
                deleteMeal(req, resp);
                break;
            case "/edit":
                showEditForm(req, resp);
                break;
            case "/update":
                updateMeal(req, resp);
                break;
            default:
                listMeals(req, resp);
        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void listMeals(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("meals", mealsTo);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("meals-info.jsp");
        dispatcher.forward(req, resp);
    }

    private void insertMeal(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.valueOf(req.getParameter("calories"));
        Meal newMeal = new Meal();
        newMeal.setCalories(calories);
        newMeal.setDateTime(dateTime);
        newMeal.setDescription(description);
        MealServlet.mealsTo.add(MealsUtil.createTo(newMeal, newMeal.getCalories() > MealsUtil.caloriesPerDay));
        resp.sendRedirect("meals");
    }

    private void deleteMeal(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        mealsTo.remove(id);
        resp.sendRedirect("meals");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        currentMealIndex = id;
        MealTo existingMeal = mealsTo.get(id);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/meals-info.jsp");
        req.setAttribute("existingMeal", existingMeal);
        dispatcher.forward(req, resp);
    }

    private void updateMeal(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.valueOf(req.getParameter("calories"));
        MealTo newMeal = new MealTo(dateTime, description, calories, calories > MealsUtil.caloriesPerDay);
        mealsTo.set(currentMealIndex, newMeal);
        resp.sendRedirect("meals");
    }
}
