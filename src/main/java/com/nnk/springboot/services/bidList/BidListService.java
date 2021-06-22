package com.nnk.springboot.services.bidList;

import com.nnk.springboot.domains.BidList;

import java.util.List;

public interface BidListService {
    BidList findById(Integer id) throws IllegalArgumentException;

    List<BidList> findAll();

    BidList save(BidList bid);

    BidList update(Integer id, BidList bid);

    void delete(Integer id);
}
