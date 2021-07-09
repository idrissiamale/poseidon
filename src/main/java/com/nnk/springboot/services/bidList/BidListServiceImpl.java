package com.nnk.springboot.services.bidList;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the BidListService interface.
 *
 * @see BidListService
 */
@Service
public class BidListServiceImpl implements BidListService {
    private static final Logger logger = LogManager.getLogger("BidListServiceImpl");
    private BidListRepository bidListRepository;

    @Autowired
    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BidList findById(Integer id) throws IllegalArgumentException {
        logger.info("BidList was successfully fetched.");
        return bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BidList> findAll() {
        logger.info("BidList data were successfully fetched.");
        return bidListRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BidList save(BidList bid) throws IllegalArgumentException {
        logger.info("BidList was saved successfully.");
        return bidListRepository.save(bid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BidList update(Integer id, BidList bid) throws IllegalArgumentException {
        return bidListRepository.findById(id).map(bidToUpdate -> {
            bidToUpdate.setAccount(bid.getAccount());
            bidToUpdate.setType(bid.getType());
            bidToUpdate.setBidQuantity(bid.getBidQuantity());
            logger.info("BidList was updated successfully.");
            return bidListRepository.save(bidToUpdate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) throws IllegalArgumentException {
        BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        logger.info("BidList was deleted successfully.");
        bidListRepository.delete(bid);
    }
}
