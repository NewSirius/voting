package com.newsirius.voting.service.impl;

import com.newsirius.voting.model.Dish;
import com.newsirius.voting.repository.dishes.DishRepository;
import com.newsirius.voting.service.DishService;
import com.newsirius.voting.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public Dish save(Dish dish, int restaurantId) {
        return ValidationUtil.checkNotFoundWithId(dishRepository.save(dish, restaurantId), dish.getId());
    }

    @Override
    public List<Dish> getMenuCurrentDay(int restaurantId) {
        return dishRepository.getMenu(restaurantId, LocalDate.now());
    }
}
