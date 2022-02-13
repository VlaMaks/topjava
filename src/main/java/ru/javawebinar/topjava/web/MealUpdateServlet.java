package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MealUpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MealTo mealsForUpdate = MealServlet.mealsTo.get(Integer.valueOf(req.getParameter("mealId")));
        req.setAttribute("meal", mealsForUpdate);
        req.getRequestDispatcher("/meals-info.jsp").forward(req, resp);
    }
}
