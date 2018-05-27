package com.newsirius.voting;

import com.newsirius.voting.model.Dish;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class DishTestData {

    public static final int START_SEQ_DISHES = 1000;
    public static final int DISH1_ID = START_SEQ_DISHES;
    public static final Dish DISH1 = new Dish(DISH1_ID, "Борщ", BigDecimal.valueOf(200), LocalDate.now());
    public static final Dish DISH2 = new Dish(DISH1_ID + 1, "Цезарь", BigDecimal.valueOf(300), LocalDate.now());
    public static final Dish DISH3 = new Dish(DISH1_ID + 2, "Компот", BigDecimal.valueOf(99), LocalDate.now());
    public static final Dish DISH4 = new Dish(DISH1_ID + 3, "Рыбный суп", BigDecimal.valueOf(200), LocalDate.now());
    public static final Dish DISH5 = new Dish(DISH1_ID + 4, "Чай", BigDecimal.valueOf(50), LocalDate.now());
    public static final Dish DISH6 = new Dish(DISH1_ID + 5, "Оливье", BigDecimal.valueOf(190), LocalDate.now());
    public static final Dish DISH7 = new Dish(DISH1_ID + 6, "Сборная солянка", BigDecimal.valueOf(250), LocalDate.now());
    public static final Dish DISH8 = new Dish(DISH1_ID + 7, "Макароны", BigDecimal.valueOf(60), LocalDate.now());
    public static final Dish DISH9 = new Dish(DISH1_ID + 8, "Курица", BigDecimal.valueOf(70), LocalDate.now());
    public static final Dish DISH10 = new Dish(DISH1_ID + 9, "Сок", BigDecimal.valueOf(90), LocalDate.now());
    public static final Dish DISH11 = new Dish(DISH1_ID + 10, "Оливье", BigDecimal.valueOf(190), LocalDate.now().minusDays(1));
    public static final Dish DISH12 = new Dish(DISH1_ID + 11, "Компот", BigDecimal.valueOf(90), LocalDate.now().minusDays(1));
    public static final Dish DISH13 = new Dish(DISH1_ID + 12, "Цезарь", BigDecimal.valueOf(350), LocalDate.now().minusDays(1));
    public static final Dish DISH14 = new Dish(DISH1_ID + 13, "Сок", BigDecimal.valueOf(90), LocalDate.now().minusDays(1));

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }
}
