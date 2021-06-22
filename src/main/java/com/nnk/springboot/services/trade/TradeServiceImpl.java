package com.nnk.springboot.services.trade;

import com.nnk.springboot.domains.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public Trade findById(Integer id) throws IllegalArgumentException {
        return tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    }

    @Override
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    @Override
    public Trade update(Integer id, Trade trade) {
        return tradeRepository.findById(id).map(tradeToUpdate -> {
            tradeToUpdate.setAccount(trade.getAccount());
            tradeToUpdate.setType(trade.getType());
            tradeToUpdate.setBuyQuantity(trade.getBuyQuantity());
            return tradeRepository.save(tradeToUpdate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    }

    @Override
    public void delete(Integer id) {
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeRepository.delete(trade);
    }
}
