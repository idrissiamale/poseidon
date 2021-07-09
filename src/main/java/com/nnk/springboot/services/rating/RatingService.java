package com.nnk.springboot.services.rating;

import com.nnk.springboot.domain.Rating;

import java.util.List;

/**
 * An interface which provides some CRUD methods to implement on Rating service class.
 */
public interface RatingService {
    /**
     * Retrieves a rating by its id.
     *
     * @param id - must not be null.
     * @return the Rating entity with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException  if id is null or not found.
     * @see com.nnk.springboot.repositories.RatingRepository
     */
    Rating findById(Integer id) throws IllegalArgumentException;

    /**
     * Retrieves all ratings.
     *
     * @return all Rating entities.
     * @see com.nnk.springboot.repositories.RatingRepository
     */
    List<Rating> findAllRatings();

    /**
     * Saves a rating.
     *
     * @param rating - must not be null.
     * @return the saved rating.
     * @throws IllegalArgumentException if Rating entity is null.
     * @see com.nnk.springboot.repositories.RatingRepository
     */
    Rating save(Rating rating) throws IllegalArgumentException;

    /**
     * Updates rating's data.
     *
     * @param id - rating's id. Must not be null.
     * @param rating - must not be null.
     * @return the updated rating.
     * @throws IllegalArgumentException if Rating entity/id is null or not found.
     * @see com.nnk.springboot.repositories.RatingRepository
     */
    Rating update(Integer id, Rating rating) throws IllegalArgumentException;

    /**
     * Deletes a rating.
     *
     * @param id - rating's id. Must not be null.
     * @throws IllegalArgumentException if rating's id is null or not found.
     * @see com.nnk.springboot.repositories.RatingRepository
     */
    void delete(Integer id) throws IllegalArgumentException;
}
