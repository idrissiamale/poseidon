package com.nnk.springboot.repositories;

import com.nnk.springboot.domains.Trade;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
