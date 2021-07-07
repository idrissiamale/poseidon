package com.nnk.springboot.services.trade;

import com.nnk.springboot.domains.Trade;

import java.util.List;

/**
 * An interface which provides some CRUD methods to implement on Trade service class.
 */
public interface TradeService {
    /**
     * Retrieves a trade by its id.
     *
     * @param id - must not be null.
     * @return the Trade entity with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException  if id is null or not found.
     * @see com.nnk.springboot.repositories.TradeRepository
     */
    Trade findById(Integer id) throws IllegalArgumentException;

    /**
     * Retrieves all trades.
     *
     * @return all Trade entities.
     * @see com.nnk.springboot.repositories.TradeRepository
     */
    List<Trade> findAll();

    /**
     * Saves a trade.
     *
     * @param trade - must not be null.
     * @return the saved trade.
     * @throws IllegalArgumentException if Trade entity is null.
     * @see com.nnk.springboot.repositories.TradeRepository
     */
    Trade save(Trade trade) throws IllegalArgumentException;

    /**
     * Updates trade's data.
     *
     * @param id - trade's id. Must not be null.
     * @param trade - must not be null.
     * @return the updated trade.
     * @throws IllegalArgumentException if Trade entity/id is null or not found.
     * @see com.nnk.springboot.repositories.TradeRepository
     */
    Trade update(Integer id, Trade trade) throws IllegalArgumentException;

    /**
     * Deletes a trade.
     *
     * @param id - trade's id. Must not be null.
     * @throws IllegalArgumentException if trade's id is null or not found.
     * @see com.nnk.springboot.repositories.TradeRepository
     */
    void delete(Integer id) throws IllegalArgumentException;
}
