package com.nnk.springboot.services.rating;

import com.nnk.springboot.domains.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the RatingService interface.
 *
 * @see RatingService
 */
@Service
public class RatingServiceImpl implements RatingService {
    private static final Logger logger = LogManager.getLogger("RatingServiceImpl");
    private RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rating findById(Integer id) throws IllegalArgumentException {
        logger.info("Rating was successfully fetched.");
        return ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Rating> findAllRatings() {
        logger.info("Ratings were successfully fetched.");
        return ratingRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rating save(Rating rating) throws IllegalArgumentException {
        logger.info("Rating was saved successfully.");
        return ratingRepository.save(rating);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rating update(Integer id, Rating rating) throws IllegalArgumentException {
        return ratingRepository.findById(id).map(ratingToUpdate -> {
            ratingToUpdate.setMoodysRating(rating.getMoodysRating());
            ratingToUpdate.setSandPRating(rating.getSandPRating());
            ratingToUpdate.setFitchRating(rating.getFitchRating());
            ratingToUpdate.setOrderNumber(rating.getOrderNumber());
            logger.info("Rating was updated successfully.");
            return ratingRepository.save(ratingToUpdate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) throws IllegalArgumentException {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        logger.info("Rating was deleted successfully.");
        ratingRepository.delete(rating);
    }
}
