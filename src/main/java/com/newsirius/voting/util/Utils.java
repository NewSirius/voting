package com.newsirius.voting.util;

import com.newsirius.voting.model.Dish;
import com.newsirius.voting.model.Restaurant;
import com.newsirius.voting.model.Role;
import com.newsirius.voting.model.User;
import com.newsirius.voting.to.DishTo;
import com.newsirius.voting.to.RestaurantTo;
import com.newsirius.voting.to.UserTo;

import java.time.LocalDate;
import java.util.EnumSet;

public class Utils {

    public static Dish createNewDishFromTo(DishTo dishTo) {
        Dish dish = new Dish();
        dish.setDate(LocalDate.now());
        dish.setName(dishTo.getName());
        dish.setPrice(dishTo.getPrice());

        return dish;
    }

    public static User createNewUserFromTo(UserTo userTo)   {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), EnumSet.of(Role.ROLE_USER));
    }

    public static Restaurant createNewRestaurantFromTo(RestaurantTo restaurantTo)   {
        return new Restaurant(null, restaurantTo.getName());
    }
}