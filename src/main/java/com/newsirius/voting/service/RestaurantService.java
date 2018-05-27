package com.newsirius.voting.service;

import com.newsirius.voting.model.Restaurant;

import java.util.List;

public interface RestaurantService {

    Restaurant save(Restaurant restaurant);

    void delete(int id);

    Restaurant getByIdWithRatingAndMenu(int id);

    List<Restaurant> getAll();

    List<Restaurant> getAllWithRatingCurrentDay();
}
