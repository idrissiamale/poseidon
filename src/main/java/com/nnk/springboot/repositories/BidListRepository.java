package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface which provides, by extending JpaRepository, generic CRUD operations to implement on BidList repository.
 *
 * @see JpaRepository
 */
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
