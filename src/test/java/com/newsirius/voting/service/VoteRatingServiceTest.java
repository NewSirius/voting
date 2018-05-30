package com.newsirius.voting.service;

import com.newsirius.voting.model.VoteRatingEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static com.newsirius.voting.RestaurantTestData.RESTAURANT1_ID;
import static com.newsirius.voting.RestaurantTestData.RESTAURANT4;
import static com.newsirius.voting.UserTestData.USER_ID;
import static com.newsirius.voting.VoteTestData.RATING1;
import static com.newsirius.voting.VoteTestData.VOTE_TIME;
import static com.newsirius.voting.VoteTestData.assertMatch;

public class VoteRatingServiceTest extends  AbstractBaseServiceTest  {

    @Autowired
    private VoteRatingService voteRatingService;

    @Test
    public void saveUserVoteAndRestaurantRating() {
        VoteRatingEntity expected = new VoteRatingEntity(null, RESTAURANT4, 3, VOTE_TIME.toLocalDate());
        VoteRatingEntity actual = voteRatingService.saveUserVoteAndRestaurantRating(RESTAURANT4.getId(), USER_ID, VOTE_TIME);
        expected.setId(actual.getId());
        assertMatch(actual, expected);
    }

    @Test
    public void getByRestaurantIdCurrentDate()  {
        VoteRatingEntity actual = voteRatingService.getByRestaurantIdCurrentDate(RESTAURANT1_ID);
        assertMatch(actual, RATING1);
    }
}