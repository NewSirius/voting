package com.newsirius.voting.service.impl;

import com.newsirius.voting.model.VoteRatingEntity;
import com.newsirius.voting.repository.vote.VoteRatingEntityRepository;
import com.newsirius.voting.service.VoteRatingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class VoteRatingServiceImpl implements VoteRatingService {

    private final VoteRatingEntityRepository voteRatingEntityRepository;

    public VoteRatingServiceImpl(VoteRatingEntityRepository voteRatingEntityRepository) {
        this.voteRatingEntityRepository = voteRatingEntityRepository;
    }

    @Override
    public VoteRatingEntity save(int restaurantId, int userId, LocalDateTime dateTime) {
        return voteRatingEntityRepository.save(restaurantId, userId, dateTime);
    }

    @Override
    public VoteRatingEntity getByRestaurantIdCurrentDate(int restaurantId) {
        return voteRatingEntityRepository.getByRestaurantIdAndLocalDate(restaurantId, LocalDate.now());
    }
}
