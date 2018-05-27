package com.newsirius.voting.service;

import com.newsirius.voting.model.Role;
import com.newsirius.voting.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumSet;
import java.util.List;

import static com.newsirius.voting.UserTestData.*;

public class UserServiceTest  extends  AbstractBaseServiceTest  {

    @Autowired
    UserService userService;

    @Test
    public void save() {
        User expected = new User("Павел", "pavel@email.com", "12345", EnumSet.of(Role.ROLE_USER));
        User user = userService.save(expected);
        expected.setId(user.getId());
        assertMatch(userService.getAll(), ADMIN, USER1, USER2, USER3, USER4, USER5, USER6, expected);
    }

    @Test
    public void delete() {
        userService.delete(1000);
        assertMatch(userService.getAll(), ADMIN, USER2, USER3, USER4, USER5, USER6);
    }

    @Test
    public void get() {
        User actual = userService.get(1000);
        assertMatch(actual, USER1);
    }

    @Test
    public void getByEmail() {
        User actual = userService.getByEmail("admin@gmail.com");
        assertMatch(actual, ADMIN);
    }

    @Test
    public void getAll() {
        List<User> actual = userService.getAll();
        assertMatch(actual, ADMIN, USER1, USER2, USER3, USER4, USER5, USER6);
    }
}