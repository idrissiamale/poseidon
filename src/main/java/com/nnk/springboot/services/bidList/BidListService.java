package com.nnk.springboot.services.bidList;

import com.nnk.springboot.domain.BidList;

import java.util.List;

/**
 * An interface which provides some CRUD methods to implement on BidList service class.
 */
public interface BidListService {
    /**
     * Retrieves a bid by its id.
     *
     * @param id - must not be null.
     * @return the BidList entity with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException  if id is null or not found.
     * @see com.nnk.springboot.repositories.BidListRepository
     */
    BidList findById(Integer id) throws IllegalArgumentException;

    /**
     * Retrieves all bids.
     *
     * @return all BidList entities.
     * @see com.nnk.springboot.repositories.BidListRepository
     */
    List<BidList> findAll();

    /**
     * Saves a bid.
     *
     * @param bid - must not be null.
     * @return the saved bid.
     * @throws IllegalArgumentException if BidList entity is null.
     * @see com.nnk.springboot.repositories.BidListRepository
     */
    BidList save(BidList bid) throws IllegalArgumentException;

    /**
     * Updates bid's data.
     *
     * @param id - bid's id. Must not be null.
     * @param bid - must not be null.
     * @return the updated bid.
     * @throws IllegalArgumentException if BidList entity/id is null or not found.
     * @see com.nnk.springboot.repositories.BidListRepository
     */
    BidList update(Integer id, BidList bid) throws IllegalArgumentException;

    /**
     * Deletes a bid.
     *
     * @param id - bid's id. Must not be null.
     * @throws IllegalArgumentException if bid's id is null or not found.
     * @see com.nnk.springboot.repositories.BidListRepository
     */
    void delete(Integer id) throws IllegalArgumentException;
}
