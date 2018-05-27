package com.newsirius.voting.service;

import com.newsirius.voting.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    void delete(int id);

    User get(int id);

    User getByEmail(String email);

    List<User> getAll();
}
