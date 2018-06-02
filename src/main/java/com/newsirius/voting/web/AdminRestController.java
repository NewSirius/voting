package com.newsirius.voting.web;

import com.newsirius.voting.model.Dish;
import com.newsirius.voting.model.Restaurant;
import com.newsirius.voting.model.User;
import com.newsirius.voting.service.DishService;
import com.newsirius.voting.service.RestaurantService;
import com.newsirius.voting.service.UserService;
import com.newsirius.voting.to.DishTo;
import com.newsirius.voting.to.RestaurantTo;
import com.newsirius.voting.to.UserTo;
import com.newsirius.voting.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.newsirius.voting.util.Utils.createNewRestaurantFromTo;


@RestController
@RequestMapping(AdminRestController.REST_URL)
public class AdminRestController {
    final static String REST_URL = "/rest/admin";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private DishService dishService;

    @Autowired
    RestaurantService restaurantService;


    //RESTAURANTS
    @GetMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllRestaurants() {
        log.info("get all restaurants");
        return restaurantService.getAll();
    }

    @PostMapping(value = "/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody RestaurantTo restaurantTo) {
        log.info("create restaurant {}", restaurantTo);
        Restaurant created = restaurantService.save(createNewRestaurantFromTo(restaurantTo));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/restaurants/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "/restaurants/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable("id") int restaurantId) {
        log.info("delete restaurant with id {}", restaurantId);
        restaurantService.delete(restaurantId);
    }

    //DISHES
    @PostMapping(value = "/restaurants/{id}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDishCurrentDay(@PathVariable("id") int restaurantId, @Valid @RequestBody DishTo dishTo) {
        log.info("created Dish {} for restaurant_id {}", dishTo, restaurantId);
        Dish created = dishService.saveWithCheckRating(Utils.createNewDishFromTo(dishTo, LocalDate.now()), restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/restaurants/{id}/dishes")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }


    //USERS
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        log.info("get users");
        return userService.getAll();
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable("id") int userId) {
        log.info("get User with id {}", userId);
        return userService.get(userId);
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@Valid @RequestBody UserTo userTo) {
        User created = userService.save(Utils.createNewUserFromTo(userTo));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/users/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "/users/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") int userId) {
        userService.delete(userId);
    }
}
