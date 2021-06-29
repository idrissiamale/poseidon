package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Rating;
import com.nnk.springboot.domains.User;
import com.nnk.springboot.services.rating.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {
    private MockMvc mockMvc;
    private Rating rating;
    private Rating ratingUpdated;
    private Rating rating2;
    private List<Rating> ratings;

    @Mock
    RatingService ratingService;

    @BeforeEach
    public void setup() {
        RatingController ratingController = new RatingController(ratingService);
        rating = new Rating(1, "Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        ratingUpdated = new Rating(1, "Moodys Rating", "Sand PRating", "Fitch Rating", 20);
        rating2 = new Rating(2, "Aaa", "AAA", "AAA", 16);
        ratings = new ArrayList<>();
        ratings.add(rating);
        ratings.add(rating2);
        this.mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
    }

    @Test
    @DisplayName("Checking that the credit page is returned when the logged in user makes a GET request to the /credits URL")
    public void shouldReturnCreditView() throws Exception {
        when(ratingService.findAllRatings()).thenReturn(ratings);

        this.mockMvc.perform(get("/rating/list").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attribute("ratings", ratings))
                .andExpect(model().attribute("ratings", hasSize(2)))
                .andExpect(model().attribute("ratings", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("moodysRating", is("Moodys Rating")),
                                hasProperty("sandPRating", is("Sand PRating")),
                                hasProperty("fitchRating", is("Fitch Rating")),
                                hasProperty("orderNumber", is(10))
                        )
                )))
                .andExpect(model().attribute("ratings", hasItem(
                        allOf(
                                hasProperty("moodysRating", is("Aaa")),
                                hasProperty("sandPRating", is("AAA")),
                                hasProperty("fitchRating", is("AAA")),
                                hasProperty("orderNumber", is(16))
                        )
                )));

        verify(ratingService).findAllRatings();
    }

}
