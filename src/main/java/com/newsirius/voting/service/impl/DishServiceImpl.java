package com.newsirius.voting.service.impl;

import com.newsirius.voting.model.Dish;
import com.newsirius.voting.model.VoteRatingEntity;
import com.newsirius.voting.repository.dishes.CrudDishRepository;
import com.newsirius.voting.repository.restaurants.CrudRestaurantRepository;
import com.newsirius.voting.repository.vote.CrudVoteRatingEntityRepository;
import com.newsirius.voting.service.DishService;
import com.newsirius.voting.util.ValidationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    private final CrudDishRepository dishRepository;

    private final CrudRestaurantRepository restaurantRepository;

    private final CrudVoteRatingEntityRepository voteRatingEntityRepository;

    public DishServiceImpl(CrudDishRepository dishRepository, CrudRestaurantRepository restaurantRepository, CrudVoteRatingEntityRepository voteRatingEntityRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.voteRatingEntityRepository = voteRatingEntityRepository;
    }

    @Override
    @Transactional
    public Dish saveWithCheckRating(Dish dish, int restaurantId) {
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        LocalDate date = dish.getDate();

        //create a zero rating for the restaurant, if it is the first dish for this restaurant for the current day
        if (voteRatingEntityRepository.getByRestaurantIdAndLocalDate(restaurantId, date) == null) {
            VoteRatingEntity voteRatingEntity = new VoteRatingEntity(null, dish.getRestaurant(), 0, date);
            voteRatingEntityRepository.save(voteRatingEntity);
        }
        return ValidationUtil.checkNotFoundWithId(dishRepository.save(dish), dish.getId());
    }

    @Override
    public List<Dish> getMenuCurrentDay(int restaurantId) {
        return dishRepository.getByRestaurant_IdAndDate(restaurantId, LocalDate.now());
    }
}
