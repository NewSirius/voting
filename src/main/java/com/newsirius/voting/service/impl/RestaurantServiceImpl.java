package com.newsirius.voting.service.impl;

import com.newsirius.voting.model.Restaurant;
import com.newsirius.voting.repository.restaurants.CrudRestaurantRepository;
import com.newsirius.voting.service.RestaurantService;
import com.newsirius.voting.util.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.newsirius.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final CrudRestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(CrudRestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }

    @Override
    public Restaurant getByIdWithRatingAndMenu(int id) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepository.getByIdWithRatingAndMenu(id), id);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> getAllWithRatingCurrentDay() {
        return restaurantRepository.getAllWithRatingCurrentDay();
    }
}
