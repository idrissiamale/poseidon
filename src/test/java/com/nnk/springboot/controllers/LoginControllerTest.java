package com.nnk.springboot.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LoginController()).build();
    }

    @Test
    @DisplayName("Checking that the login page is returned when the user wants to log on the application")
    public void shouldReturnLoginPageView() throws Exception {
        this.mockMvc.perform(get("/app/login").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("login_page"))
                .andDo(print());
    }

    @Test
    @DisplayName("Checking that the user management page is returned when the user makes a GET request to the /app/secure/article-details URL")
    public void shouldReturnUserManagementPageView() throws Exception {
        this.mockMvc.perform(get("/app/secure/article-details").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(redirectedUrl("/user/list"))
                .andDo(print());
    }

    @Test
    @DisplayName("Checking that the access denied page is returned when the user wants to access a page he has not authorization for")
    public void shouldReturnErrorPageView() throws Exception {
        String errorMessage = "You are not authorized for the requested data.";
        this.mockMvc.perform(get("/app/error").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("403"))
                .andExpect(model().attribute("errorMsg", errorMessage))
                .andDo(print());
    }
}
