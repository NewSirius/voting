package com.newsirius.voting.service;

import com.newsirius.voting.model.Role;
import com.newsirius.voting.model.User;
import com.newsirius.voting.util.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;

import java.util.EnumSet;
import java.util.List;

import static com.newsirius.voting.UserTestData.*;

public class UserServiceTest extends AbstractBaseServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void save() {
        User expected = new User("Павел", "pavel@email.com", "12345", EnumSet.of(Role.ROLE_USER));
        User user = userService.save(expected);
        expected.setId(user.getId());
        assertMatch(userService.getAll(), ADMIN, USER1, USER2, USER3, USER4, USER5, USER6, expected);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() throws Exception {
        userService.save(new User(null, "Duplicate", "USER1@YANDEX.RU", "newPass", EnumSet.of(Role.ROLE_USER)));
    }

    @Test
    public void delete() {
        userService.delete(1000);
        assertMatch(userService.getAll(), ADMIN, USER2, USER3, USER4, USER5, USER6);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        userService.delete(1);
    }

    @Test
    public void get() {
        User actual = userService.get(1000);
        assertMatch(actual, USER1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        userService.get(1);
    }

    @Test
    public void getByEmail() {
        User actual = userService.getByEmail("admin@gmail.com");
        assertMatch(actual, ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void getByEmailNotFound() throws Exception {
        userService.getByEmail("admin11@gmail.com");
    }

    @Test
    public void getAll() {
        List<User> actual = userService.getAll();
        assertMatch(actual, ADMIN, USER1, USER2, USER3, USER4, USER5, USER6);
    }
}