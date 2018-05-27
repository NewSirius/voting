package com.newsirius.voting.repository.dishes;

import com.newsirius.voting.model.Dish;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository {

    Dish save(Dish dish, int restaurantId);

    List<Dish> getMenu(int restaurantId, LocalDate date);

}
