package com.nnk.springboot.services.trade;

import com.nnk.springboot.domains.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceImplTest {
    @Mock
    private TradeRepository tradeRepository;
    private TradeServiceImpl tradeServiceImpl;
    private Trade trade;
    private List<Trade> trades;

    @BeforeEach
    public void setUp() {
        tradeServiceImpl = new TradeServiceImpl(tradeRepository);
        trade = new Trade(1, "account name", "type name", 55.75);
        Trade trade2 = new Trade(2, "account name 2", "type name 2", 35.75);
        trades = new ArrayList<>();
        trades.add(trade);
        trades.add(trade2);
    }

    @Test
    @DisplayName("Checking that the new trade is correctly saved")
    public void shouldReturnNewTradeWhenSaved() {
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade tradeToSave = tradeServiceImpl.save(trade);

        verify(tradeRepository).save(any(Trade.class));
        assertNotNull(tradeToSave);
    }

    @Test
    @DisplayName("Comparing expected and actual account to check that the new trade is correctly saved ")
    public void shouldGetTheSameAccountWhenNewTradeIsCorrectlySaved() {
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade tradeToSave = tradeServiceImpl.save(trade);

        verify(tradeRepository).save(any(Trade.class));
        assertEquals(trade.getAccount(), tradeToSave.getAccount());
    }

    @Test
    @DisplayName("Checking that the trade is updated with the new type")
    public void shouldReturnTradeWithNewTypeWhenTradeUpdated() {
        trade.setType("type");
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade tradeUpdated = tradeServiceImpl.update(1, trade);

        verify(tradeRepository).save(any(Trade.class));
        assertNotNull(tradeUpdated);
    }

    @Test
    @DisplayName("Comparing expected and actual type to check that the trade was correctly updated ")
    public void shouldGetTheSameTypeWhenTradeUpdated() {
        trade.setType("type");
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade tradeUpdated = tradeServiceImpl.update(1, trade);

        verify(tradeRepository).save(any(Trade.class));
        assertEquals("type", tradeUpdated.getType());
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when the trade we want to update is not found")
    public void shouldThrowExceptionWhenTradeToUpdateIsNotFound() {
        doThrow(new IllegalArgumentException()).when(tradeRepository).findById(7);

        assertThrows(IllegalArgumentException.class, () -> tradeServiceImpl.update(7, trade));
        verify(tradeRepository).findById(7);
    }

    @Test
    @DisplayName("Checking that the trade is correctly fetched by its id")
    public void shouldFindTradeByItsId() {
        when(tradeRepository.findById(1)).thenReturn(Optional.ofNullable(trade));

        Trade tradeToFind = tradeServiceImpl.findById(1);

        assertEquals("account name", tradeToFind.getAccount());
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when the trade's id is null")
    public void shouldThrowExceptionWhenBidListIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> tradeServiceImpl.findById(null));
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when the trade's id does not exist")
    public void shouldThrowExceptionWhenTradeIdDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> tradeServiceImpl.findById(7));
    }

    @Test
    @DisplayName("Checking that trades are correctly fetched")
    public void shouldGetAllTrades() {
        when(tradeRepository.findAll()).thenReturn(trades);

        List<Trade> tradeList = tradeServiceImpl.findAll();

        verify(tradeRepository).findAll();
        assertEquals(trades, tradeList);
    }

    @Test
    @DisplayName("Checking that trade, if found by its id, is correctly deleted")
    public void shouldDeleteTradeWhenFoundByItsId() {
        when(tradeRepository.findById(1)).thenReturn(Optional.ofNullable(trade));
        doNothing().when(tradeRepository).delete(trade);

        tradeServiceImpl.delete(trade.getTradeId());

        verify(tradeRepository).delete(trade);
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when the trade we want to delete is not found")
    public void shouldThrowExceptionWhenTradeToDeleteIsNotFound() {
        doThrow(new IllegalArgumentException()).when(tradeRepository).findById(7);

        assertThrows(IllegalArgumentException.class, () -> tradeServiceImpl.delete(7));
        verify(tradeRepository).findById(7);
    }
}
