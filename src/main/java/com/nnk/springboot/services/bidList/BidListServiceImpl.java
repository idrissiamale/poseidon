package com.nnk.springboot.services.bidList;

import com.nnk.springboot.domains.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListServiceImpl implements BidListService {
    @Autowired
    private BidListRepository bidListRepository;

    @Override
    public BidList findById(Integer id) throws IllegalArgumentException {
        return bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
    }

    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList save(BidList bid) {
        return bidListRepository.save(bid);
    }

    @Override
    public BidList update(Integer id, BidList bid) {
        return bidListRepository.findById(id).map(bidToUpdate -> {
            bidToUpdate.setAccount(bid.getAccount());
            bidToUpdate.setType(bid.getType());
            bidToUpdate.setBidQuantity(bid.getBidQuantity());
            return bidListRepository.save(bidToUpdate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
    }

    @Override
    public void delete(Integer id) {
        BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        bidListRepository.delete(bid);
    }
}
