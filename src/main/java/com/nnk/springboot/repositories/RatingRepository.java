package com.nnk.springboot.repositories;

import com.nnk.springboot.domains.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface which provides, by extending JpaRepository, generic CRUD operations to implement on Rating repository.
 *
 * @see JpaRepository
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
