package com.newsirius.voting.service;

import com.newsirius.voting.model.VoteRatingEntity;

import java.time.LocalDateTime;

public interface VoteRatingService {

    VoteRatingEntity saveUserVoteAndRestaurantRating(int restaurantId, int userId, LocalDateTime dateTime);

    VoteRatingEntity getByRestaurantIdCurrentDate(int restaurantId);
}
