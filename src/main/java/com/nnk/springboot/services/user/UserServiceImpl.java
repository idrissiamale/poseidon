package com.nnk.springboot.services.user;

import com.nnk.springboot.domains.User;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger("UserServiceImpl");
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(Integer id) throws IllegalArgumentException {
        logger.info("User was successfully fetched.");
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        logger.info("User was successfully fetched.");
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found:" + username));
    }

    @Override
    public List<User> findAllUsers() {
        logger.info("Users were successfully fetched.");
        return userRepository.findAll();
    }

    @Override
    public User save(User user) throws IllegalArgumentException {
        User userToSave = new User();
        if (usernameExists(user.getUsername())) {
            throw new IllegalArgumentException("User already exists with that username");
        }
        userToSave.setFullname(user.getFullname());
        userToSave.setUsername(user.getUsername());
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        userToSave.setRole(user.getRole());
        logger.info("User was saved successfully.");
        return userRepository.save(userToSave);
    }

    @Override
    public User update(Integer id, User user) {
        return userRepository.findById(id).map(userToUpdate -> {
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            logger.info("User was updated successfully.");
            return userRepository.save(userToUpdate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    @Override
    public void delete(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        logger.info("User was deleted successfully.");
        userRepository.delete(user);
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
