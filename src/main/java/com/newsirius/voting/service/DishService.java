package com.newsirius.voting.service;

import com.newsirius.voting.model.Dish;

import java.util.List;

public interface DishService {

    Dish save(Dish dish, int restaurantId);

    List<Dish> getMenuCurrentDay(int restaurantId);
}
