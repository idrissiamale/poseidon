package com.nnk.springboot.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HomeControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new HomeController()).build();
    }

    @Test
    @DisplayName("Checking that the login page is returned when the user wants to log on the application")
    public void shouldReturnHomePageView() throws Exception {
        this.mockMvc.perform(get("/").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andDo(print());
    }

    @Test
    @DisplayName("Checking that the login page is returned when the user wants to log on the application")
    public void shouldReturnAdminHomePageView() throws Exception {
        this.mockMvc.perform(get("/admin/home").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(redirectedUrl("/bidList/list"))
                .andDo(print());
    }
}
