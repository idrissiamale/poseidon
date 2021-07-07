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

/**
 * Implementation of the UserService interface.
 *
 * @see UserService
 */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(Integer id) throws IllegalArgumentException {
        logger.info("User was successfully fetched.");
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        logger.info("User was successfully fetched.");
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found:" + username));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAllUsers() {
        logger.info("Users were successfully fetched.");
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public User update(Integer id, User user) throws IllegalArgumentException {
        return userRepository.findById(id).map(userToUpdate -> {
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            logger.info("User was updated successfully.");
            return userRepository.save(userToUpdate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) throws IllegalArgumentException {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        logger.info("User was deleted successfully.");
        userRepository.delete(user);
    }

    /**
     * Returns whether a user with the given username exists.
     *
     * @param username - must not be null.
     * @return true if a user with the given username exists, false otherwise.
     * @throws IllegalArgumentException if the given username is null.
     * @see com.nnk.springboot.repositories.UserRepository
     */
    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
