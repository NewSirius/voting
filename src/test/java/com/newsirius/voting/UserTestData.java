package com.newsirius.voting;

import com.newsirius.voting.model.Role;
import com.newsirius.voting.model.User;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.EnumSet;

import static com.newsirius.voting.web.json.JsonUtil.writeIgnoreProps;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class UserTestData {
    public static final int USERS_START_SEQ = 1000;
    public static final int USER_ID = USERS_START_SEQ;
    public static final int ADMIN_ID = USERS_START_SEQ + 1;

    public static final User USER1 = new User(USER_ID, "User1", "user1@yandex.ru", "password", EnumSet.of(Role.ROLE_USER));
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", EnumSet.of(Role.ROLE_ADMIN, Role.ROLE_USER));
    public static final User USER2 = new User(USER_ID + 2, "User2", "user2@yandex.ru", "password", EnumSet.of(Role.ROLE_USER));
    public static final User USER3 = new User(USER_ID + 3, "User3", "user3@yandex.ru", "password", EnumSet.of(Role.ROLE_USER));
    public static final User USER4 = new User(USER_ID + 4, "User4", "user4@yandex.ru", "password", EnumSet.of(Role.ROLE_USER));
    public static final User USER5 = new User(USER_ID + 5, "User5", "user5@yandex.ru", "password", EnumSet.of(Role.ROLE_USER));
    public static final User USER6 = new User(USER_ID + 6, "User6", "user6@yandex.ru", "password", EnumSet.of(Role.ROLE_USER));

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "voteUserEntity", "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "voteUserEntity", "password").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User expected) {
        return content().json(writeIgnoreProps(expected, "registered", "password"));
    }

    public static ResultMatcher contentJson(User... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "registered", "password"));
    }

}