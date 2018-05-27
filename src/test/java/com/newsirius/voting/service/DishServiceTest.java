package com.newsirius.voting.service;

import com.newsirius.voting.DishTestData;
import com.newsirius.voting.model.Dish;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.newsirius.voting.DishTestData.*;
import static com.newsirius.voting.RestaurantTestData.RESTAURANT1_ID;

public class DishServiceTest extends AbstractBaseServiceTest {

    @Autowired
    DishService dishService;

    @Test
    public void save() {
        Dish expected = new Dish(null, "Супчик", BigDecimal.valueOf(299), LocalDate.now());
        Dish created = dishService.save(expected, RESTAURANT1_ID);
        created.setId(created.getId());
        DishTestData.assertMatch(dishService.getMenuCurrentDay(RESTAURANT1_ID), DISH1, DISH2, DISH3, expected);
    }

    @Test
    public void getMenuCurrentDay() {
        assertMatch(dishService.getMenuCurrentDay(RESTAURANT1_ID), DISH1, DISH2, DISH3);
    }
}