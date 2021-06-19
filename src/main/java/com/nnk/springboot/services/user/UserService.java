package com.nnk.springboot.services.user;

import com.nnk.springboot.domains.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    User findById(Integer id) throws IllegalArgumentException;

    User findByUsername(String username) throws UsernameNotFoundException;

    List<User> findAllUsers();

    User save(User user);

    User update(Integer id, User user);

    void delete(Integer id);
}
