package com.newsirius.voting.web;

import com.newsirius.voting.AuthorizedUser;
import com.newsirius.voting.model.Restaurant;
import com.newsirius.voting.model.VoteRatingEntity;
import com.newsirius.voting.service.RestaurantService;
import com.newsirius.voting.service.VoteRatingService;
import com.newsirius.voting.util.TimeClock;
import com.newsirius.voting.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping(CommonRestController.REST_URL)
public class CommonRestController {
    static final String REST_URL = "/rest/restaurants";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteRatingService voteRatingService;

    @Autowired
    // Class with real and fake(for the tests) time
    private TimeClock timeClock;

    //Get restaurants with rating for current date (sort by rating and name fields)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getRestaurantsWithRating() {
        log.info("get list restaurants with rating for current date");
        return restaurantService.getAllWithRatingCurrentDay();
    }

    //Get restaurant with rating and menu for current day
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurantWithRatingAndMenu(@PathVariable("id") int restaurantId) {
        log.info("get restaurant with id {} ", restaurantId);
        return restaurantService.getByIdWithRatingAndMenu(restaurantId);
    }

    //Vote for the restaurant's menu
    @PostMapping(value = "/{id}/vote", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public VoteRatingEntity saveVote(@PathVariable("id") int restaurantId) {
        log.info("vote for restaurant with id {} and userId {}", restaurantId, AuthorizedUser.id());
        LocalDateTime voteLocalDateTime = timeClock.getLocalDateTime();

        if (ValidationUtil.checkVoteTime(voteLocalDateTime)) {
            int userId = AuthorizedUser.id();
           return voteRatingService.save(restaurantId, userId, voteLocalDateTime);
        } else {
            throw new IllegalStateException("Time is up");
        }
    }
}
