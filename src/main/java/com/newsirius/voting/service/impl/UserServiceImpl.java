package com.newsirius.voting.service.impl;

import com.newsirius.voting.model.User;
import com.newsirius.voting.repository.users.UserRepository;
import com.newsirius.voting.service.UserService;
import com.newsirius.voting.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return ValidationUtil.checkNotFoundWithId(userRepository.save(user), user.getId());
    }

    @Override
    public void delete(int id) {
      ValidationUtil.checkNotFoundWithId(userRepository.delete(id), id);
    }

    @Override
    public User get(int id) {
        return ValidationUtil.checkNotFoundWithId(userRepository.get(id), id);
    }

    @Override
    public User getByEmail(String email) {
        return ValidationUtil.checkNotFound(userRepository.getByEmail(email), email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }
}
