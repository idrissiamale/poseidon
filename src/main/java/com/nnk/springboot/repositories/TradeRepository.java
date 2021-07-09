package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface which provides, by extending JpaRepository, generic CRUD operations to implement on Trade repository.
 *
 * @see JpaRepository
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
