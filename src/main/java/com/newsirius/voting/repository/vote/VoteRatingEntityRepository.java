package com.newsirius.voting.repository.vote;

import com.newsirius.voting.model.VoteRatingEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface VoteRatingEntityRepository {

    VoteRatingEntity save(int restaurantId, int userId, LocalDateTime dateTime);

    VoteRatingEntity getByRestaurantIdAndLocalDate(int restaurantId, LocalDate date);
}
