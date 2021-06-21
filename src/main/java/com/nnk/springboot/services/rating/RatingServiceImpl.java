package com.nnk.springboot.services.rating;

import com.nnk.springboot.domains.Rating;
import com.nnk.springboot.domains.User;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating findById(Integer id) throws IllegalArgumentException {
        return ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    @Override
    public List<Rating> findAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating update(Integer id, Rating rating) {
        return ratingRepository.findById(id).map(ratingToUpdate -> {
            ratingToUpdate.setMoodysRating(rating.getMoodysRating());
            ratingToUpdate.setSandPRating(rating.getSandPRating());
            ratingToUpdate.setFitchRating(rating.getFitchRating());
            ratingToUpdate.setOrderNumber(rating.getOrderNumber());
            return ratingRepository.save(ratingToUpdate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    @Override
    public void delete(Integer id) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        ratingRepository.delete(rating);
    }
}
