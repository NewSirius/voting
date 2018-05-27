package com.newsirius.voting.service;

import com.newsirius.voting.model.VoteRatingEntity;

import java.time.LocalDateTime;

public interface VoteRatingService {

    VoteRatingEntity save(int restaurantId, int userId, LocalDateTime dateTime);

    VoteRatingEntity getByRestaurantIdCurrentDate(int restaurantId);
}
