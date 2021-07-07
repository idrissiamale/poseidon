package com.nnk.springboot.services.user;

import com.nnk.springboot.domains.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * An interface which provides some CRUD methods to implement on User service class.
 */
public interface UserService {
    /**
     * Retrieves a user by its id.
     *
     * @param id - must not be null.
     * @return the User entity with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException  if id is null or not found.
     * @see com.nnk.springboot.repositories.UserRepository
     */
    User findById(Integer id) throws IllegalArgumentException;

    /**
     * Retrieves a user by its username.
     *
     * @param username - must not be null.
     * @return the User entity with the given username or Optional#empty() if none found.
     * @throws UsernameNotFoundException  if username is null or not found.
     * @see com.nnk.springboot.repositories.UserRepository
     */
    User findByUsername(String username) throws UsernameNotFoundException;

    /**
     * Retrieves all users.
     *
     * @return all User entities.
     * @see com.nnk.springboot.repositories.UserRepository
     */
    List<User> findAllUsers();

    /**
     * Saves a user.
     *
     * @param user - must not be null.
     * @return the saved user.
     * @throws IllegalArgumentException if User entity is null or already exists.
     * @see com.nnk.springboot.repositories.UserRepository
     */
    User save(User user) throws IllegalArgumentException;

    /**
     * Updates user's data.
     *
     * @param id - user's id. Must not be null.
     * @param user - must not be null.
     * @return the updated user.
     * @throws IllegalArgumentException if User entity/id is null or not found.
     * @see com.nnk.springboot.repositories.UserRepository
     */
    User update(Integer id, User user) throws IllegalArgumentException;

    /**
     * Deletes a user.
     *
     * @param id - user's id. Must not be null.
     * @throws IllegalArgumentException if user's id is null or not found.
     * @see com.nnk.springboot.repositories.UserRepository
     */
    void delete(Integer id) throws IllegalArgumentException;
}
