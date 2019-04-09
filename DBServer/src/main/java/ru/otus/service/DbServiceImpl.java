package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.User;
import ru.otus.jpa.UserRepository;

import java.util.List;

@Service
public class DbServiceImpl implements DbService {
    private final UserRepository userRepository;

    public DbServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.findById(user.getId()).ifPresent(userRepository::delete);
    }

}
