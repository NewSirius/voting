package com.newsirius.voting.service.impl;

import com.newsirius.voting.AuthorizedUser;
import com.newsirius.voting.model.User;
import com.newsirius.voting.repository.users.UserRepository;
import com.newsirius.voting.service.UserService;
import com.newsirius.voting.util.Utils;
import com.newsirius.voting.util.ValidationUtil;
import com.newsirius.voting.util.exception.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public User save(User user) {
        return ValidationUtil.checkNotFoundWithId(userRepository.save(Utils.prepareCreateUser(user, passwordEncoder)), user.getId());
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
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
    @Cacheable("users")
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }
        return new AuthorizedUser(user);
    }
}
