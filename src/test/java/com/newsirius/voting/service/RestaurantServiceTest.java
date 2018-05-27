package com.newsirius.voting.service;

import com.newsirius.voting.DishTestData;
import com.newsirius.voting.VoteTestData;
import com.newsirius.voting.model.Restaurant;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.newsirius.voting.RestaurantTestData.*;
import static com.newsirius.voting.VoteTestData.RATING3;


public class RestaurantServiceTest extends  AbstractBaseServiceTest {

    @Autowired
    RestaurantService restaurantService;

    @Test
    public void save() {
        Restaurant expected = new Restaurant(null, "Restaurant7");
        Restaurant savedRestaurant = restaurantService.save(expected);
        expected.setId(savedRestaurant.getId());
        assertMatch(restaurantService.getAll(), RESTAURANT1, RESTAURANT2, RESTAURANT3, RESTAURANT4, RESTAURANT5, RESTAURANT6, expected);
    }

    @Test
    public void delete() {
        restaurantService.delete(1000);
        assertMatch(restaurantService.getAll(), RESTAURANT2, RESTAURANT3, RESTAURANT4, RESTAURANT5, RESTAURANT6);
    }

    @Test
    public void getByIdWithRatingAndMenu() {
        Restaurant actual = restaurantService.getByIdWithRatingAndMenu(1002);
        assertMatch(actual, RESTAURANT3);
        VoteTestData.assertMatch(actual.getVoteRating(), RATING3);
        DishTestData.assertMatch(actual.getDishes(), DishTestData.DISH7, DishTestData.DISH8, DishTestData.DISH9, DishTestData.DISH10);
    }

    @Test
    public void getAll() {
        List<Restaurant> actualList = restaurantService.getAll();
        assertMatch(actualList, RESTAURANTS);
    }

    @Test
    public void getAllWithRatingCurrentDay() {
        List<Restaurant> list = restaurantService.getAllWithRatingCurrentDay();
        assertMatch(list, RESTAURANTS_CURRENT_DAY);
        VoteTestData.assertMatch(VoteTestData.getRatings(list), VoteTestData.RATINGS);
    }
}