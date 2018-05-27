package com.newsirius.voting.repository.vote;

import com.newsirius.voting.model.VoteRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional(readOnly = true)
public interface CrudVoteRatingEntityRepository extends JpaRepository<VoteRatingEntity, Integer> {

    @Override
    @Transactional
    VoteRatingEntity save(VoteRatingEntity entity);


    @Query("SELECT v FROM VoteRatingEntity v WHERE v.restaurant.id=:restaurantId and v.localDate=:date")
    VoteRatingEntity getByRestaurantIdAndLocalDate(@Param("restaurantId") int restaurantId, @Param("date") LocalDate date);

    @Modifying
    @Transactional
    @Query("UPDATE VoteRatingEntity v set v.rating = v.rating + 1 WHERE v.restaurant.id = :id AND v.localDate = :date")
    int incrementRating(@Param("id") int restaurantId, @Param("date") LocalDate date);

    @Modifying
    @Transactional
    @Query("UPDATE VoteRatingEntity v set v.rating = v.rating - 1 WHERE v.restaurant.id = :id AND v.localDate = :date")
    int decrementRating(@Param("id") int restaurantId, @Param("date") LocalDate date);
}
