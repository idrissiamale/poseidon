package com.nnk.springboot.repositories;

import com.nnk.springboot.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * An interface which provides, by extending JpaRepository, generic CRUD operations to implement on User repository.
 *
 * @see JpaRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
