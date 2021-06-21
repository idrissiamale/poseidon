package com.nnk.springboot.services.rating;

import com.nnk.springboot.domains.Rating;
import com.nnk.springboot.domains.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface RatingService {
    Rating findById(Integer id) throws IllegalArgumentException;

    List<Rating> findAllRatings();

    Rating save(Rating rating);

    Rating update(Integer id, Rating rating);

    void delete(Integer id);
}
