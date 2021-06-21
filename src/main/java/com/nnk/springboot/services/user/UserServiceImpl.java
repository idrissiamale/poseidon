package com.nnk.springboot.services.user;

import com.nnk.springboot.domains.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User findById(Integer id) throws IllegalArgumentException {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found:" + username));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        User userToSave = new User();
        userToSave.setFullname(user.getFullname());
        userToSave.setUsername(user.getUsername());
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        userToSave.setRole(user.getRole());
        return userRepository.save(userToSave);
    }

    @Override
    public User update(Integer id, User user) {
        return userRepository.findById(id).map(userToUpdate -> {
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(userToUpdate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    @Override
    public void delete(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
    }
}
