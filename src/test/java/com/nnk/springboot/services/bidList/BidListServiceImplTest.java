package com.nnk.springboot.services.bidList;

import com.nnk.springboot.domains.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidListServiceImplTest {
    @Mock
    private BidListRepository bidListRepository;
    private BidListServiceImpl bidListServiceImpl;
    private BidList bidList;
    private List<BidList> bids;

    @BeforeEach
    public void setUp() {
        bidListServiceImpl = new BidListServiceImpl(bidListRepository);
        bidList = new BidList(1, "account name", "type name", 55.75);
        BidList bidList2 = new BidList(2, "account name 2", "type name 2", 35.75);
        bids = new ArrayList<>();
        bids.add(bidList);
        bids.add(bidList2);
    }

    @Test
    @DisplayName("Checking that the new bidList is correctly saved")
    public void shouldReturnNewBidListWhenSaved() {
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList bidListToSave = bidListServiceImpl.save(bidList);

        verify(bidListRepository).save(any(BidList.class));
        assertNotNull(bidListToSave);
    }

    @Test
    @DisplayName("Comparing expected and actual account to check that the new bidList is correctly saved ")
    public void shouldGetTheSameAccountWhenNewBidListIsCorrectlySaved() {
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList bidListToSave = bidListServiceImpl.save(bidList);

        verify(bidListRepository).save(any(BidList.class));
        assertEquals(bidList.getAccount(),bidListToSave.getAccount());
    }

    @Test
    @DisplayName("Checking that the bidList is updated with the new type")
    public void shouldReturnBidListWithNewTypeWhenBidListUpdated() {
        bidList.setType("type");
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList bidListUpdated = bidListServiceImpl.update(1, bidList);

        verify(bidListRepository).save(any(BidList.class));
        assertNotNull(bidListUpdated);
    }

    @Test
    @DisplayName("Comparing expected and actual type to check that the bidList was correctly updated ")
    public void shouldGetTheSameTypeWhenBidListUpdated() {
        bidList.setType("type");
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList bidListUpdated = bidListServiceImpl.update(1, bidList);

        verify(bidListRepository).save(any(BidList.class));
        assertEquals("type", bidListUpdated.getType());
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when the bidList we want to update is not found")
    public void shouldThrowExceptionWhenBidListToUpdateIsNotFound() {
        doThrow(new IllegalArgumentException()).when(bidListRepository).findById(7);

        assertThrows(IllegalArgumentException.class, () -> bidListServiceImpl.update(7, bidList));
        verify(bidListRepository).findById(7);
    }

    @Test
    @DisplayName("Checking that the bidList is correctly fetched by its id")
    public void shouldFindBidListByItsId() {
        when(bidListRepository.findById(1)).thenReturn(Optional.ofNullable(bidList));

        BidList bidListToFind = bidListServiceImpl.findById(1);

        assertEquals("account name", bidListToFind.getAccount());
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when the id of bidList is null")
    public void shouldThrowExceptionWhenBidListIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> bidListServiceImpl.findById(null));
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when the id of bidList does not exist")
    public void shouldThrowExceptionWhenBidListIdDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> bidListServiceImpl.findById(7));
    }

    @Test
    @DisplayName("Checking that bids are correctly fetched")
    public void shouldGetAllBids() {
        when(bidListRepository.findAll()).thenReturn(bids);

        List<BidList> list = bidListServiceImpl.findAll();

        verify(bidListRepository).findAll();
        assertEquals(bids, list);
    }

    @Test
    @DisplayName("Checking that bidList, if found by its id, is correctly deleted")
    public void shouldDeleteBidListWhenFoundByItsId() {
        when(bidListRepository.findById(1)).thenReturn(Optional.ofNullable(bidList));
        doNothing().when(bidListRepository).delete(bidList);

        bidListServiceImpl.delete(bidList.getBidListId());

        verify(bidListRepository).delete(bidList);
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when bidList we want to delete is not found")
    public void shouldThrowExceptionWhenBidListToDeleteIsNotFound() {
        doThrow(new IllegalArgumentException()).when(bidListRepository).findById(7);

        assertThrows(IllegalArgumentException.class, () -> bidListServiceImpl.delete(7));
        verify(bidListRepository).findById(7);
    }
}
