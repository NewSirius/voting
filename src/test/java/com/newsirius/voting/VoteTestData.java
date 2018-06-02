package com.newsirius.voting;

import com.newsirius.voting.model.Restaurant;
import com.newsirius.voting.model.VoteRatingEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.newsirius.voting.RestaurantTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteTestData {

    public static final int START_SEQ_VOTERATING = 1000;
    public static final int VOTERATING1_ID = START_SEQ_VOTERATING;
    public static final VoteRatingEntity RATING1 = new VoteRatingEntity(VOTERATING1_ID, RESTAURANT1, 0, LocalDate.now());
    public static final VoteRatingEntity RATING2 = new VoteRatingEntity(VOTERATING1_ID + 1, RESTAURANT2, 0, LocalDate.now());
    public static final VoteRatingEntity RATING3 = new VoteRatingEntity(VOTERATING1_ID  + 2, RESTAURANT3, 3, LocalDate.now());
    public static final VoteRatingEntity RATING4 = new VoteRatingEntity(VOTERATING1_ID  + 3, RESTAURANT4, 2, LocalDate.now());
    public static final VoteRatingEntity RATING5 = new VoteRatingEntity(VOTERATING1_ID  + 4, RESTAURANT4, 1, LocalDate.now().minusDays(1));

    public static final VoteRatingEntity RATING2_AFTER_INC = new VoteRatingEntity(VOTERATING1_ID  + 1, RESTAURANT2, 1, LocalDate.now());
    public static final VoteRatingEntity RATING3_AFTER_DEC = new VoteRatingEntity(VOTERATING1_ID  + 2, RESTAURANT3, 2, LocalDate.now());


    public static final LocalDateTime VOTE_TIME = LocalDateTime.of(LocalDate.now(), LocalTime.of(10,15,0));

    public static final List<VoteRatingEntity> RATINGS = Arrays.asList(RATING1, RATING2, RATING3, RATING4);


    public static void assertMatch(VoteRatingEntity actual, VoteRatingEntity expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<VoteRatingEntity> actual, VoteRatingEntity... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<VoteRatingEntity> actual, Iterable<VoteRatingEntity> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static List<VoteRatingEntity> getRatings(List<Restaurant> restaurants)   {
        return restaurants.stream()
                .map(Restaurant::getVoteRating)
                .flatMap(Set::stream).distinct().collect(Collectors.toList());
    }
}
