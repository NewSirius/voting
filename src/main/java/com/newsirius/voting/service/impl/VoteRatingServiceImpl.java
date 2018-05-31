package com.newsirius.voting.service.impl;

import com.newsirius.voting.model.VoteRatingEntity;
import com.newsirius.voting.model.VoteUserEntity;
import com.newsirius.voting.repository.restaurants.CrudRestaurantRepository;
import com.newsirius.voting.repository.users.CrudUserRepository;
import com.newsirius.voting.repository.vote.CrudVoteRatingEntityRepository;
import com.newsirius.voting.repository.vote.CrudVoteUserEntityRepository;
import com.newsirius.voting.service.VoteRatingService;
import com.newsirius.voting.util.exception.IllegalRequestDataException;
import com.newsirius.voting.util.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.newsirius.voting.util.ValidationUtil.*;

@Service
public class VoteRatingServiceImpl implements VoteRatingService {

    private final CrudVoteRatingEntityRepository voteRatingRepository;

    private final CrudVoteUserEntityRepository voteUserRepository;

    private final CrudUserRepository userRepository;

    private final CrudRestaurantRepository restaurantRepository;

    public VoteRatingServiceImpl(CrudVoteRatingEntityRepository voteRatingRepository,
                                 CrudVoteUserEntityRepository voteUserRepository,
                                 CrudUserRepository userRepository,
                                 CrudRestaurantRepository restaurantRepository) {
        this.voteRatingRepository = voteRatingRepository;
        this.voteUserRepository = voteUserRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    @Transactional
    public VoteRatingEntity saveUserVoteAndRestaurantRating(int restaurantId, int userId, LocalDateTime dateTime) {
        VoteUserEntity userEntity = new VoteUserEntity();
        userEntity.setLocalDateTime(dateTime);
        userEntity.setUser(userRepository.getOne(userId));
        userEntity.setRestaurant(restaurantRepository.getOne(restaurantId));

        //get voteUserEntity for today if exist and decrement rating for old restaurant
        VoteUserEntity voteInDb = voteUserRepository.getBetween(userEntity.getUser().getId(), START_DATETIME_FOR_VOTE, END_DATETIME_FOR_VOTE);
        if (voteInDb != null) {
            userEntity.setId(voteInDb.getId());

            //throw exception if revote for same restaurant
            if (voteInDb.getRestaurant().getId().equals(userEntity.getRestaurant().getId())) {
                throw new IllegalRequestDataException("You already voted for this restaurant");
            }

            //https://stackoverflow.com/questions/30143594/spring-jpa-and-hibernate-how-to-increment-a-counter-without-concurrency-issu
            if (voteRatingRepository.decrementRating(voteInDb.getRestaurant().getId(), LocalDate.now()) == 0) {
                throw new NotFoundException("Rating for restaurant with id " + voteInDb.getRestaurant().getId() + " is not found");
            }
        }
        //Increment rating for new restaurant
        if (voteRatingRepository.incrementRating(restaurantId, LocalDate.now()) == 0) {
            throw new NotFoundException("Rating for restaurant with id " + restaurantId + " is not found");
        }

        voteUserRepository.save(userEntity);
        return checkNotFound(voteRatingRepository.getByRestaurantIdAndLocalDate(restaurantId, dateTime.toLocalDate()),"restaurantId " + restaurantId);
    }

    @Override
    public VoteRatingEntity getByRestaurantIdCurrentDate(int restaurantId) {
        return checkNotFound(voteRatingRepository.getByRestaurantIdAndLocalDate(restaurantId, LocalDate.now()), "restaurantId " + restaurantId);
    }
}
