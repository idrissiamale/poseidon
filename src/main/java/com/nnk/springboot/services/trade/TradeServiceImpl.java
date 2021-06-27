package com.nnk.springboot.services.trade;

import com.nnk.springboot.domains.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {
    private static final Logger logger = LogManager.getLogger("TradeServiceImpl");
    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public Trade findById(Integer id) throws IllegalArgumentException {
        logger.info("Trade was successfully fetched.");
        return tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    }

    @Override
    public List<Trade> findAll() {
        logger.info("Trade data were successfully fetched.");
        return tradeRepository.findAll();
    }

    @Override
    public Trade save(Trade trade) {
        logger.info("Trade was saved successfully.");
        return tradeRepository.save(trade);
    }

    @Override
    public Trade update(Integer id, Trade trade) {
        return tradeRepository.findById(id).map(tradeToUpdate -> {
            tradeToUpdate.setAccount(trade.getAccount());
            tradeToUpdate.setType(trade.getType());
            tradeToUpdate.setBuyQuantity(trade.getBuyQuantity());
            logger.info("Trade data were updated successfully.");
            return tradeRepository.save(tradeToUpdate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    }

    @Override
    public void delete(Integer id) {
        Trade trade = findById(id);
        logger.info("Trade data were deleted successfully.");
        tradeRepository.delete(trade);
    }
}
