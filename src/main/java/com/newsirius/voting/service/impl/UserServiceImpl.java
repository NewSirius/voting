package com.newsirius.voting.service.impl;

import com.newsirius.voting.AuthorizedUser;
import com.newsirius.voting.model.User;
import com.newsirius.voting.repository.users.UserRepository;
import com.newsirius.voting.service.UserService;
import com.newsirius.voting.util.ValidationUtil;
import com.newsirius.voting.util.exception.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return ValidationUtil.checkNotFoundWithId(userRepository.save(user), user.getId());
    }

    @Override
    public void delete(int id) throws NotFoundException {
      ValidationUtil.checkNotFoundWithId(userRepository.delete(id), id);
    }

    @Override
    public User get(int id) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(userRepository.get(id), id);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        return ValidationUtil.checkNotFound(userRepository.getByEmail(email), email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email);
        if  (user == null)  {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }
        return new AuthorizedUser(user);
    }
}
