package com.newsirius.voting.web;

import com.newsirius.voting.DishTestData;
import com.newsirius.voting.TestUtil;
import com.newsirius.voting.UserTestData;
import com.newsirius.voting.model.Dish;
import com.newsirius.voting.model.Restaurant;
import com.newsirius.voting.model.User;
import com.newsirius.voting.service.DishService;
import com.newsirius.voting.service.RestaurantService;
import com.newsirius.voting.service.UserService;
import com.newsirius.voting.to.DishTo;
import com.newsirius.voting.to.UserTo;
import com.newsirius.voting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static com.newsirius.voting.DishTestData.DISH1;
import static com.newsirius.voting.DishTestData.DISH2;
import static com.newsirius.voting.DishTestData.DISH3;
import static com.newsirius.voting.RestaurantTestData.*;
import static com.newsirius.voting.UserTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminRestController.REST_URL + "/";

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    DishService dishService;

    @Autowired
    UserService userService;

    @Test
    public void getAllRestaurants() throws Exception {
        mockMvc.perform(get(REST_URL + "restaurants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(RESTAURANTS));
    }

    @Test
    public void createRestaurant() throws Exception {
        Restaurant expected = new Restaurant(null, "Restaurant7");
        ResultActions actions = mockMvc.perform(post(REST_URL + "restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andDo(print());

        Restaurant restaurant = TestUtil.readFromJson(actions, Restaurant.class);
        expected.setId(restaurant.getId());

        assertMatch(restaurantService.getAll(), RESTAURANT1, RESTAURANT2, RESTAURANT3, RESTAURANT4, RESTAURANT5, RESTAURANT6, expected);
    }

    @Test
    public void deleteRestaurant() throws Exception {
        mockMvc.perform(delete(REST_URL + "restaurants/" + RESTAURANT1_ID))
                .andExpect(status().isNoContent());

        assertMatch(restaurantService.getAll(), RESTAURANT2, RESTAURANT3, RESTAURANT4, RESTAURANT5, RESTAURANT6);
    }

    @Test
    public void createDishCurrentDay() throws Exception {
        DishTo expected = new DishTo("Супчик", BigDecimal.valueOf(299));
        ResultActions actions = mockMvc.perform(post(REST_URL + "restaurants/" + RESTAURANT1_ID + "/dishes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)));

        Dish created = JsonUtil.readValue(TestUtil.getContent(actions), Dish.class);

        DishTestData.assertMatch(dishService.getMenuCurrentDay(RESTAURANT1_ID), DISH1, DISH2, DISH3, created);
    }

    @Test
    public void getUsers() throws Exception {
        mockMvc.perform(get(REST_URL + "/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.contentJson(ADMIN, USER1, USER2, USER3, USER4, USER5, USER6));
    }

    @Test
    public void getUser() throws Exception {
        mockMvc.perform(get(REST_URL + "/users/" + USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.contentJson(USER1))
                .andDo(print());
    }

    @Test
    public void createUser() throws Exception {
        UserTo expected = new UserTo("Vasya", "vasya@google.com", "12345");

        ResultActions actions = mockMvc.perform(post(REST_URL + "users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)));

        User created = TestUtil.readFromJson(actions, User.class);
        UserTestData.assertMatch(userService.get(created.getId()), created);
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete(REST_URL + "users/" + USER1.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(userService.getAll(),ADMIN, USER2, USER3, USER4, USER5, USER6 );
    }
}