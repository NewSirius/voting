package com.newsirius.voting;

import com.newsirius.voting.model.Restaurant;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {

    public static final int START_SEQ_RESTAURANT = 1000;
    public static final int RESTAURANT1_ID = START_SEQ_RESTAURANT;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "Restaurant1");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT1_ID + 1, "Restaurant2");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT1_ID + 2, "Restaurant3");
    public static final Restaurant RESTAURANT4 = new Restaurant(RESTAURANT1_ID + 3, "Restaurant4");
    public static final Restaurant RESTAURANT5 = new Restaurant(RESTAURANT1_ID + 4, "Restaurant5");
    public static final Restaurant RESTAURANT6 = new Restaurant(RESTAURANT1_ID + 5, "Restaurant6");


    public static final List<Restaurant> RESTAURANTS = Arrays.asList(RESTAURANT1, RESTAURANT2, RESTAURANT3, RESTAURANT4, RESTAURANT5, RESTAURANT6);
    public static final List<Restaurant> RESTAURANTS_CURRENT_DAY = Arrays.asList(RESTAURANT1, RESTAURANT2, RESTAURANT3, RESTAURANT4);

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "voteRating", "voteUserEntity", "dishes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("voteRating", "voteUserEntity", "dishes").isEqualTo(expected);
    }
}
