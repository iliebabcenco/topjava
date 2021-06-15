package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealService;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet(name = "MealServlet", value = "/mealServlet")
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static MealService mealService;


    @Override
    public void init() throws ServletException {
//        super.init();
        mealService = MealService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Enter meal servlet");
        List<MealTo> mealList = mealService.getMeals();

        String action = request.getParameter("action");
        String strId = request.getParameter("id");
        int id = 0;
        MealTo meal = null;
        if (strId != null) {
            id = Integer.parseInt(strId);
            meal = mealList.get(id);
        }

        String path = "meals.jsp";
        if (action != null) {
            System.out.println("Enter if with action= " + action);
            switch (action) {
                case "update":
                    request.setAttribute("id", mealList.indexOf(meal));
                    request.setAttribute("dateTime", meal.getDateTime());
                    request.setAttribute("description", meal.getDescription());
                    request.setAttribute("calories", meal.getCalories());
                    request.setAttribute("excess", meal.isExcess());
                    path = "save-update-meal.jsp";
                    break;
                case "delete":
                    mealList.remove(meal);
                    break;
                case "Save":
                    System.out.println("save case from mealservlet");
                    int mealId = Integer.parseInt( request.getParameter("id"));
                    String description = request.getParameter("description");
                    int calories = Integer.parseInt( request.getParameter("calories"));
                    String dateTimeStr = (request.getParameter("dataTime"));
                    System.out.println("data is = "+dateTimeStr);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
                    boolean excess = Boolean.parseBoolean(request.getParameter("excess"));
                    if (mealId < mealList.size()) {
                        MealTo foundMeal = mealList.get(mealId);
                        foundMeal.setDescription(description);
                        foundMeal.setExcess(excess);
                        foundMeal.setCalories(calories);
                        foundMeal.setDateTime(dateTime);
                    } else {
                        mealList.add(new MealTo(dateTime, description, calories, excess));
                    }
                    break;
            }
        }


        request.setAttribute("mealList", mealList);

        request.getRequestDispatcher(path).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
