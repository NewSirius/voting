package com.newsirius.voting.service;

import com.newsirius.voting.model.VoteRatingEntity;
import com.newsirius.voting.util.exception.IllegalRequestDataException;
import com.newsirius.voting.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static com.newsirius.voting.RestaurantTestData.*;
import static com.newsirius.voting.UserTestData.USER1;
import static com.newsirius.voting.VoteTestData.RATING1;
import static com.newsirius.voting.VoteTestData.VOTE_TIME;
import static com.newsirius.voting.VoteTestData.assertMatch;

public class VoteRatingServiceTest extends  AbstractBaseServiceTest  {

    @Autowired
    private VoteRatingService voteRatingService;

    @Test
    public void saveUserVoteAndRestaurantRating() {
        VoteRatingEntity expected = new VoteRatingEntity(null, RESTAURANT4, 3, VOTE_TIME.toLocalDate());
        VoteRatingEntity actual = voteRatingService.saveUserVoteAndRestaurantRating(RESTAURANT4.getId(), USER1.getId(), VOTE_TIME);
        expected.setId(actual.getId());
        assertMatch(actual, expected);
    }

    @Test(expected = IllegalRequestDataException.class)
    public void revoteSameRestaurant() throws Exception {
        voteRatingService.saveUserVoteAndRestaurantRating(RESTAURANT3.getId(), USER1.getId(), VOTE_TIME);
    }

    @Test(expected = NotFoundException.class)
    public void voteWithNoRating() throws Exception {
        voteRatingService.saveUserVoteAndRestaurantRating(RESTAURANT5.getId(), USER1.getId(), VOTE_TIME);
    }

    @Test
    public void getByRestaurantIdCurrentDate()  {
        VoteRatingEntity actual = voteRatingService.getByRestaurantIdCurrentDate(RESTAURANT1_ID);
        assertMatch(actual, RATING1);
    }
}