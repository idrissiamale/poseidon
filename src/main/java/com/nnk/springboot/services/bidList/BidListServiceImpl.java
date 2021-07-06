package com.nnk.springboot.services.bidList;

import com.nnk.springboot.domains.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListServiceImpl implements BidListService {
    private static final Logger logger = LogManager.getLogger("BidListServiceImpl");
    private BidListRepository bidListRepository;

    @Autowired
    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Override
    public BidList findById(Integer id) throws IllegalArgumentException {
        logger.info("BidList was successfully fetched.");
        return bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
    }

    @Override
    public List<BidList> findAll() {
        logger.info("BidList data were successfully fetched.");
        return bidListRepository.findAll();
    }

    @Override
    public BidList save(BidList bid) {
        logger.info("BidList was saved successfully.");
        return bidListRepository.save(bid);
    }

    @Override
    public BidList update(Integer id, BidList bid) {
        return bidListRepository.findById(id).map(bidToUpdate -> {
            bidToUpdate.setAccount(bid.getAccount());
            bidToUpdate.setType(bid.getType());
            bidToUpdate.setBidQuantity(bid.getBidQuantity());
            logger.info("BidList was updated successfully.");
            return bidListRepository.save(bidToUpdate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
    }

    @Override
    public void delete(Integer id) {
        BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        logger.info("BidList was deleted successfully.");
        bidListRepository.delete(bid);
    }
}
