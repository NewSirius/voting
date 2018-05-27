package com.newsirius.voting.repository.dishes;

import com.newsirius.voting.model.Dish;
import com.newsirius.voting.model.VoteRatingEntity;
import com.newsirius.voting.repository.restaurants.CrudRestaurantRepository;
import com.newsirius.voting.repository.vote.CrudVoteRatingEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaDishRepositoryImpl implements DishRepository {

    @Autowired
    CrudDishRepository dishRepository;

    @Autowired
    CrudRestaurantRepository restaurantRepository;

    @Autowired
    CrudVoteRatingEntityRepository voteRatingEntityRepository;

    @Override
    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        LocalDate date = dish.getDate();

        //create a zero rating for the restaurant, if it is the first dish for the current day
        if (voteRatingEntityRepository.getByRestaurantIdAndLocalDate(restaurantId, date) == null) {
            VoteRatingEntity voteRatingEntity = new VoteRatingEntity(null, dish.getRestaurant(), 0, date);
            voteRatingEntityRepository.save(voteRatingEntity);
        }
        return dishRepository.save(dish);
    }

    @Override
    public List<Dish> getMenu(int restaurantId, LocalDate date) {
       return dishRepository.getByRestaurant_IdAndDate(restaurantId, date);
    }
}
