package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface which provides, by extending JpaRepository, generic CRUD operations to implement on Curve Point repository.
 *
 * @see JpaRepository
 */
@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
