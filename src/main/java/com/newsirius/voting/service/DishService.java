package com.newsirius.voting.service;

import com.newsirius.voting.model.Dish;

import java.util.List;

public interface DishService {

    Dish saveWithCheckRating(Dish dish, int restaurantId);

    List<Dish> getMenuCurrentDay(int restaurantId);
}
