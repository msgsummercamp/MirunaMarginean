package com.example.backend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.backend.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IRepository<User> {

    private List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void deleteById(Long id) {
        users.removeIf(u -> u.getId().equals(id));
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = users.stream()
            .filter(u -> u.getId().equals(id))
            .findFirst();
        return user.orElse(null);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }
}