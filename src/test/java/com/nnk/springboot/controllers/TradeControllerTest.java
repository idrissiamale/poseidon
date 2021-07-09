package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.trade.TradeService;
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
public class TradeControllerTest {
    private MockMvc mockMvc;
    private Trade trade;
    private Trade tradeUpdated;
    private List<Trade> tradeList;

    @Mock
    TradeService tradeService;

    @BeforeEach
    public void setUp() {
        TradeController tradeController = new TradeController(tradeService);
        trade = new Trade(1, "account name", "type name", 55.75);
        tradeUpdated = new Trade(1, "account", "type name", 55.75);
        Trade trade2 = new Trade(2, "account name 2", "type name 2", 35.75);
        tradeList = new ArrayList<>();
        tradeList.add(trade);
        tradeList.add(trade2);
        this.mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
    }

    @Test
    @DisplayName("Checking that the trade/list page is returned when the user makes a GET request to the /trade/list URL")
    public void shouldReturnTradeListView() throws Exception {
        when(tradeService.findAll()).thenReturn(tradeList);

        this.mockMvc.perform(get("/trade/list").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attribute("tradeList", tradeList))
                .andExpect(model().attribute("tradeList", hasSize(2)))
                .andExpect(model().attribute("tradeList", hasItem(
                        allOf(
                                hasProperty("tradeId", is(1)),
                                hasProperty("account", is("account name")),
                                hasProperty("type", is("type name")),
                                hasProperty("buyQuantity", is(55.75))
                        )
                )))
                .andExpect(model().attribute("tradeList", hasItem(
                        allOf(
                                hasProperty("tradeId", is(2)),
                                hasProperty("account", is("account name 2")),
                                hasProperty("type", is("type name 2")),
                                hasProperty("buyQuantity", is(35.75))
                        )
                )));

        verify(tradeService).findAll();
    }

    @Test
    @DisplayName("Checking that the trade/add page is returned when the user makes a GET request to the /trade/add URL")
    public void shouldReturnTradeAddPageView() throws Exception {
        this.mockMvc.perform(get("/trade/add").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @DisplayName("Checking that the user is redirected to the trade/list page when trade's data are correctly saved")
    public void shouldReturnTradeListPageViewWhenTradeDataAreCorrectlySaved() throws Exception {
        when(tradeService.save(any(Trade.class))).thenReturn(trade);

        this.mockMvc.perform(post("/trade/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "account name")
                .param("type", "type name")
                .param("buyQuantity", String.valueOf(55.75))
                .sessionAttr("trade", trade)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

        verify(tradeService).save(any(Trade.class));
        assertEquals("account name", trade.getAccount());
    }

    @Test
    @DisplayName("Checking that the 'Add New Trade' form (trade/add page) is returned with error message when there are errors on the form fields")
    public void shouldReturnAddNewTradeFormViewWhenErrorsOnAccountAndBuyQuantityFields() throws Exception {
        String account = " ";
        Double buyQuantity = null;

        this.mockMvc.perform(post("/trade/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", account)
                .param("type", "type name")
                .param("bidQuantity", String.valueOf(buyQuantity))
                .sessionAttr("trade", trade)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeHasFieldErrors("trade", "account"))
                .andExpect(model().attributeHasFieldErrors("trade", "buyQuantity"))
                .andExpect(model().attribute("trade", hasProperty("account", is(account))))
                .andExpect(model().attribute("trade", hasProperty("buyQuantity", is(buyQuantity))))
                .andDo(print());

        verifyZeroInteractions(tradeService);
    }

    @Test
    @DisplayName("Checking that the 'Update Trade' form (trade/update page) is returned when the user makes a GET request to the /trade/update/{id} URL")
    public void shouldReturnUpdateTradeFormView() throws Exception {
        when(tradeService.findById(1)).thenReturn(trade);

        this.mockMvc.perform(get("/trade/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attribute("trade", trade))
                .andDo(print());

        verify(tradeService).findById(1);
    }

    @Test
    @DisplayName("Checking that the user is redirected to the trade/list page when trade's data are correctly updated")
    public void shouldReturnTradeListPageViewWhenTradeDataAreCorrectlyUpdated() throws Exception {
        when(tradeService.update(anyInt(), any(Trade.class))).thenReturn(tradeUpdated);

        this.mockMvc.perform(post("/trade/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "account")
                .param("type", "type name")
                .param("buyQuantity", String.valueOf(55.75))
                .sessionAttr("trade", tradeUpdated)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

        verify(tradeService).update(anyInt(), any(Trade.class));
        assertEquals("account", tradeUpdated.getAccount());
    }

    @Test
    @DisplayName("Checking that the 'Update Trade' form (trade/update page) is returned with error message when there are errors on the form fields")
    public void shouldReturnUpdateTradeFormViewWhenErrorsOnAccountField() throws Exception {
        String account = "the name of this account is really really long";

        this.mockMvc.perform(post("/trade/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", account)
                .param("type", "type name")
                .param("buyQuantity", String.valueOf(55.75))
                .sessionAttr("trade", tradeUpdated)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeHasFieldErrors("trade", "account"))
                .andExpect(model().attribute("trade", hasProperty("account", is(account))))
                .andDo(print());

        verifyZeroInteractions(tradeService);
    }

    @Test
    @DisplayName("Checking that the user is redirected to the trade/list page when trade's data are correctly deleted")
    public void shouldReturnTradeListPageViewWhenTradeDataAreCorrectlyDeleted() throws Exception {
        doNothing().when(tradeService).delete(1);

        this.mockMvc.perform(get("/trade/delete/{id}", 1))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(model().attribute("trade", nullValue()))
                .andDo(print());

        verify(tradeService).delete(1);
    }
}
