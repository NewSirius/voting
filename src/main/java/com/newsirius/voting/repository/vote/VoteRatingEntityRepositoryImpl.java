package com.newsirius.voting.repository.vote;

import com.newsirius.voting.model.VoteRatingEntity;
import com.newsirius.voting.model.VoteUserEntity;
import com.newsirius.voting.repository.restaurants.CrudRestaurantRepository;
import com.newsirius.voting.repository.users.CrudUserRepository;
import com.newsirius.voting.util.exception.IllegalRequestDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.newsirius.voting.util.ValidationUtil.END_DATETIME_FOR_VOTE;
import static com.newsirius.voting.util.ValidationUtil.START_DATETIME_FOR_VOTE;

@Repository
public class VoteRatingEntityRepositoryImpl implements VoteRatingEntityRepository {

    @Autowired
    CrudVoteUserEntityRepository crudVoteUserEntityRepository;

    @Autowired
    CrudVoteRatingEntityRepository crudVoteRatingEntityRepository;

    @Autowired
    CrudUserRepository crudUserRepository;

    @Autowired
    CrudRestaurantRepository crudRestaurantRepository;


    @Transactional
    @Override
    public VoteRatingEntity save(int restaurantId, int userId, LocalDateTime dateTime) {
        VoteUserEntity userEntity = new VoteUserEntity();
        userEntity.setLocalDateTime(dateTime);
        userEntity.setUser(crudUserRepository.getOne(userId));
        userEntity.setRestaurant(crudRestaurantRepository.getOne(restaurantId));

        //get voteUserEntity for today if exist and decrement rating for old restaurant
        VoteUserEntity voteInDb = crudVoteUserEntityRepository.getBetween(userEntity.getUser().getId(), START_DATETIME_FOR_VOTE, END_DATETIME_FOR_VOTE);
        if (voteInDb != null) {
            userEntity.setId(voteInDb.getId());

            //throw exception if revote for same restaurant
            if (voteInDb.getRestaurant().getId().equals(userEntity.getRestaurant().getId())) {
                throw new IllegalRequestDataException("You already voted for this restaurant");
            }

            //https://stackoverflow.com/questions/30143594/spring-jpa-and-hibernate-how-to-increment-a-counter-without-concurrency-issu
            if (crudVoteRatingEntityRepository.decrementRating(voteInDb.getRestaurant().getId(), LocalDate.now()) == 0) {
                return null;
            }
        }
        //Increment rating for new restaurant
        if (crudVoteRatingEntityRepository.incrementRating(restaurantId, LocalDate.now()) == 0) {
            return null;
        }

        crudVoteUserEntityRepository.save(userEntity);
        return crudVoteRatingEntityRepository.getByRestaurantIdAndLocalDate(restaurantId, dateTime.toLocalDate());
    }

    @Override
    public VoteRatingEntity getByRestaurantIdAndLocalDate(int restaurantId, LocalDate date) {
        return crudVoteRatingEntityRepository.getByRestaurantIdAndLocalDate(restaurantId, date);
    }
}
