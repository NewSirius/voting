package com.newsirius.voting.repository.dishes;

import com.newsirius.voting.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @Override
    @Transactional
    Dish save(Dish entity);

    List<Dish> getByRestaurant_IdAndDate(int restaurantId, LocalDate date);
}
