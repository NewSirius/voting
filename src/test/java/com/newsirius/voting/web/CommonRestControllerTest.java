package com.newsirius.voting.web;

import com.newsirius.voting.DishTestData;
import com.newsirius.voting.TestUtil;
import com.newsirius.voting.VoteTestData;
import com.newsirius.voting.model.Restaurant;
import com.newsirius.voting.model.VoteRatingEntity;
import com.newsirius.voting.service.VoteRatingService;
import com.newsirius.voting.util.TimeClock;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.newsirius.voting.RestaurantTestData.*;
import static com.newsirius.voting.UserTestData.USER1;
import static com.newsirius.voting.VoteTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CommonRestControllerTest extends AbstractControllerTest {
    private final static String REST_URL = CommonRestController.REST_URL + "/";

    @Autowired
    VoteRatingService voteRatingService;

    @Autowired
    TimeClock timeClock;

    @Test
    public void getRestaurantsWithRating() throws Exception {
        ResultActions actions = mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(RESTAURANTS_CURRENT_DAY));

        List<VoteRatingEntity> ratings = VoteTestData.getRatings(TestUtil.readFromJsonToList(actions, Restaurant.class));
        VoteTestData.assertMatch(ratings, RATINGS);
    }

    @Test
    public void getRestaurantWithRatingAndMenu() throws Exception {
        ResultActions actions = mockMvc.perform(get(REST_URL + RESTAURANT1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(RESTAURANT1));

        VoteRatingEntity rating = TestUtil.readFromJson(actions, Restaurant.class).getVoteRating().stream().findFirst().get();
        VoteTestData.assertMatch(rating, RATING1);

        Restaurant restaurant = TestUtil.readFromJson(actions, Restaurant.class);
        DishTestData.assertMatch(restaurant.getDishes(), DishTestData.DISH1, DishTestData.DISH2, DishTestData.DISH3);
    }

    @Test
    public void saveVote() throws Exception {
        //We need fake vote time before 11:00 am
        timeClock.setFakeTime(true);
        mockMvc.perform(post(REST_URL + RESTAURANT2.getId() + "/vote")
                .with(TestUtil.userHttpBasic(USER1))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(RATING2_AFTER_INC));

        VoteTestData.assertMatch(voteRatingService.getByRestaurantIdCurrentDate(RESTAURANT3.getId()), RATING3_AFTER_DEC);
        timeClock.setFakeTime(false);
    }
}