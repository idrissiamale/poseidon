package com.nnk.springboot.repositories;

import com.nnk.springboot.domains.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
