package com.nnk.springboot.services.trade;

import com.nnk.springboot.domains.Trade;

import java.util.List;

public interface TradeService {
    Trade findById(Integer id) throws IllegalArgumentException;

    List<Trade> findAll();

    Trade save(Trade trade);

    Trade update(Integer id, Trade trade);

    void delete(Integer id);
}
