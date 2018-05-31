package com.newsirius.voting.service;

import com.newsirius.voting.model.Restaurant;
import com.newsirius.voting.util.exception.NotFoundException;

import java.util.List;

public interface RestaurantService {

    Restaurant save(Restaurant restaurant);

    void delete(int id) throws NotFoundException;

    Restaurant getByIdWithRatingAndMenu(int id) throws NotFoundException;

    List<Restaurant> getAll();

    List<Restaurant> getAllWithRatingCurrentDay();
}
