package com.newsirius.voting.repository.restaurants;

import com.newsirius.voting.model.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);

    @Override
    Optional<Restaurant> findById(Integer integer);


    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Override
    List<Restaurant> findAll(Sort sort);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT DISTINCT r " +
            "FROM Restaurant r " +
            "JOIN FETCH r.voteRating ra " +
            "WHERE ra.localDate=current_date order by r.name ASC")
    List<Restaurant> getAllWithRatingCurrentDay();

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT r " +
            "FROM Restaurant r " +
            "JOIN FETCH r.voteRating ra " +
            "JOIN FETCH r.dishes " +
            "WHERE r.id=:id AND ra.localDate=current_date")
    Restaurant getByIdWithRatingAndMenu(@Param("id") int id);
}
