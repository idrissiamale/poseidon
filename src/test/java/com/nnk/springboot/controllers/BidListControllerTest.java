package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.BidList;
import com.nnk.springboot.domains.User;
import com.nnk.springboot.services.bidList.BidListService;
import com.nnk.springboot.services.bidList.BidListServiceImpl;
import com.nnk.springboot.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(MockitoExtension.class)
public class BidListControllerTest {
    private MockMvc mockMvc;
    private BidList bidList;
    private BidList bidListUpdated;
    private List<BidList> bids;

    @Mock
    BidListService bidListService;

    @BeforeEach
    public void setUp() {
        BidListController bidListController = new BidListController(bidListService);
        bidList = new BidList(1, "account name", "type name", 55.75);
        bidListUpdated = new BidList(1, "account", "type name", 55.75);
        BidList bidList2 = new BidList(2, "account name 2", "type name 2", 35.75);
        bids = new ArrayList<>();
        bids.add(bidList);
        bids.add(bidList2);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bidListController).build();
    }

    @Test
    @DisplayName("Checking that the bidList/list page is returned when the user makes a GET request to the /bidList/list URL")
    public void shouldReturnBidListView() throws Exception {
        when(bidListService.findAll()).thenReturn(bids);

        this.mockMvc.perform(get("/bidList/list").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("bids", bids))
                .andExpect(model().attribute("bids", hasSize(2)))
                .andExpect(model().attribute("bids", hasItem(
                        allOf(
                                hasProperty("bidListId", is(1)),
                                hasProperty("account", is("account name")),
                                hasProperty("type", is("type name")),
                                hasProperty("bidQuantity", is(55.75))
                        )
                )))
                .andExpect(model().attribute("bids", hasItem(
                        allOf(
                                hasProperty("bidListId", is(2)),
                                hasProperty("account", is("account name 2")),
                                hasProperty("type", is("type name 2")),
                                hasProperty("bidQuantity", is(35.75))
                        )
                )));

        verify(bidListService).findAll();
    }

    @Test
    @DisplayName("Checking that the bidList/add page is returned when the user makes a GET request to the /bidList/add URL")
    public void shouldReturnBidListAddPageView() throws Exception {
        this.mockMvc.perform(get("/bidList/add").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @DisplayName("Checking that the user is redirected to the bidList/list page when bid's data are correctly saved")
    public void shouldReturnBidListPageViewWhenBidDataAreCorrectlySaved() throws Exception {
        when(bidListService.save(any(BidList.class))).thenReturn(bidList);

        this.mockMvc.perform(post("/bidList/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "account name")
                .param("type", "type name")
                .param("bidQuantity", String.valueOf(55.75))
                .sessionAttr("bidList", bidList)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

        verify(bidListService).save(any(BidList.class));
        assertEquals("account name", bidList.getAccount());
    }

    @Test
    @DisplayName("Checking that the 'Add New Bid' form (bidList/add page) is returned with error message when there are errors on the form fields")
    public void shouldReturnAddNewBidFormViewWhenErrorsOnAccountAndBidQuantityFields() throws Exception {
        String account = " ";
        Double bidQuantity = null;

        this.mockMvc.perform(post("/bidList/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", account)
                .param("type", "type name")
                .param("bidQuantity", String.valueOf(bidQuantity))
                .sessionAttr("bidList", bidList)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeHasFieldErrors("bidList", "account"))
                .andExpect(model().attributeHasFieldErrors("bidList", "bidQuantity"))
                .andExpect(model().attribute("bidList", hasProperty("account", is(account))))
                .andExpect(model().attribute("bidList", hasProperty("bidQuantity", is(bidQuantity))))
                .andDo(print());

        verifyZeroInteractions(bidListService);
    }

    @Test
    @DisplayName("Checking that the 'Update Bid' form (bidList/update page) is returned when the user makes a GET request to the /bidList/update/{id} URL")
    public void shouldReturnUpdateBidFormView() throws Exception {
        when(bidListService.findById(1)).thenReturn(bidList);

        this.mockMvc.perform(get("/bidList/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attribute("bidList", bidList))
                .andDo(print());

        verify(bidListService).findById(1);
    }

    @Test
    @DisplayName("Checking that the user is redirected to the bidList/list page when bid's data are correctly updated")
    public void shouldReturnBidListPageViewWhenBidDataAreCorrectlyUpdated() throws Exception {
        when(bidListService.update(anyInt(), any(BidList.class))).thenReturn(bidListUpdated);

        this.mockMvc.perform(post("/bidList/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "account")
                .param("type", "type name")
                .param("bidQuantity", String.valueOf(55.75))
                .sessionAttr("bidList", bidListUpdated)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

        verify(bidListService).update(anyInt(), any(BidList.class));
        assertEquals("account", bidListUpdated.getAccount());
    }

    @Test
    @DisplayName("Checking that the 'Update Bid' form (bidList/update page) is returned with error message when there are errors on the form fields")
    public void shouldReturnUpdateBidFormViewWhenErrorsOnAccountField() throws Exception {
        String account = "the name of this account is really really long";

        this.mockMvc.perform(post("/bidList/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", account)
                .param("type", "type name")
                .param("bidQuantity", String.valueOf(55.75))
                .sessionAttr("bidList", bidListUpdated)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeHasFieldErrors("bidList", "account"))
                .andExpect(model().attribute("bidList", hasProperty("account", is(account))))
                .andDo(print());

        verifyZeroInteractions(bidListService);
    }

    @Test
    @DisplayName("Checking that the user is redirected to the bidList/list page when bid's data are correctly deleted")
    public void shouldReturnBidListPageViewWhenBidDataAreCorrectlyDeleted() throws Exception {
        doNothing().when(bidListService).delete(1);

        this.mockMvc.perform(get("/bidList/delete/{id}", 1))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().attribute("bidList", nullValue()))
                .andDo(print());

        verify(bidListService).delete(1);
    }
}
