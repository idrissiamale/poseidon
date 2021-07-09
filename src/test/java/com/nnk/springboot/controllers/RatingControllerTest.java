package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.rating.RatingService;
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
public class RatingControllerTest {
    private MockMvc mockMvc;
    private Rating rating;
    private Rating ratingUpdated;
    private List<Rating> ratings;

    @Mock
    RatingService ratingService;

    @BeforeEach
    public void setup() {
        RatingController ratingController = new RatingController(ratingService);
        rating = new Rating(1, "Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        ratingUpdated = new Rating(1, "Moodys Rating", "Sand PRating", "Fitch Rating", 20);
        Rating rating2 = new Rating(2, "Aaa", "AAA", "AAA", 16);
        ratings = new ArrayList<>();
        ratings.add(rating);
        ratings.add(rating2);
        this.mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
    }

    @Test
    @DisplayName("Checking that the rating/list page is returned when the user makes a GET request to the /rating/list URL")
    public void shouldReturnRatingListView() throws Exception {
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
                                hasProperty("id", is(2)),
                                hasProperty("moodysRating", is("Aaa")),
                                hasProperty("sandPRating", is("AAA")),
                                hasProperty("fitchRating", is("AAA")),
                                hasProperty("orderNumber", is(16))
                        )
                )));

        verify(ratingService).findAllRatings();
    }

    @Test
    @DisplayName("Checking that the rating/add page is returned when the user makes a GET request to the /rating/add URL")
    public void shouldReturnRatingAddPageView() throws Exception {
        this.mockMvc.perform(get("/rating/add").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @DisplayName("Checking that the user is redirected to the rating/list page when new rating is correctly saved")
    public void shouldReturnRatingListPageViewWhenNewRatingIsCorrectlySaved() throws Exception {
        when(ratingService.save(any(Rating.class))).thenReturn(rating);

        this.mockMvc.perform(post("/rating/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", "Moodys Rating")
                .param("sandPRating", "Sand PRating")
                .param("fitchRating", "Fitch Rating")
                .param("orderNumber", "10")
                .sessionAttr("rating", rating)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

        verify(ratingService).save(any(Rating.class));
        assertEquals("Moodys Rating", rating.getMoodysRating());
    }

    @Test
    @DisplayName("Checking that the 'Add New Rating' form (rating/add page) is returned with error message when there are errors on the form fields")
    public void shouldReturnAddNewRatingFormViewWhenErrorsOnMoodysRatingAndOrderFields() throws Exception {
        String moodysRating = " ";
        Integer orderNumber = 0;

        this.mockMvc.perform(post("/rating/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", moodysRating)
                .param("sandPRating", "Sand PRating")
                .param("fitchRating", "Fitch Rating")
                .param("orderNumber", String.valueOf(orderNumber))
                .sessionAttr("rating", rating)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeHasFieldErrors("rating", "moodysRating"))
                .andExpect(model().attributeHasFieldErrors("rating", "orderNumber"))
                .andExpect(model().attribute("rating", hasProperty("moodysRating", is(moodysRating))))
                .andExpect(model().attribute("rating", hasProperty("orderNumber", is(orderNumber))))
                .andDo(print());

        verifyZeroInteractions(ratingService);
    }

    @Test
    @DisplayName("Checking that the 'Update Rating' form (rating/update page) is returned when the user makes a GET request to the /rating/update/{id} URL")
    public void shouldReturnUpdateRatingFormView() throws Exception {
        when(ratingService.findById(1)).thenReturn(rating);

        this.mockMvc.perform(get("/rating/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("rating", rating))
                .andDo(print());

        verify(ratingService).findById(1);
    }

    @Test
    @DisplayName("Checking that the user is redirected to the rating/list page when rating's data are correctly updated")
    public void shouldReturnRatingListPageViewWhenRatingDataAreCorrectlyUpdated() throws Exception {
        Integer orderNumber = 20;
        when(ratingService.update(anyInt(), any(Rating.class))).thenReturn(ratingUpdated);

        this.mockMvc.perform(post("/rating/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", "Moodys Rating")
                .param("sandPRating", "Sand PRating")
                .param("fitchRating", "Fitch Rating")
                .param("orderNumber", String.valueOf(ratingUpdated.getOrderNumber()))
                .sessionAttr("rating", ratingUpdated)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

        verify(ratingService).update(anyInt(), any(Rating.class));
        assertEquals(orderNumber, ratingUpdated.getOrderNumber());
    }

    @Test
    @DisplayName("Checking that the 'Update Rating' form (rating/update page) is returned with error message when there are errors on the form fields")
    public void shouldReturnUpdateRatingFormViewWhenErrorsOnOrderField() throws Exception {
        Integer orderNumber = null;

        this.mockMvc.perform(post("/rating/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", "Moodys Rating")
                .param("sandPRating", "Sand PRating")
                .param("fitchRating", "Fitch Rating")
                .param("orderNumber", String.valueOf(orderNumber))
                .sessionAttr("rating", ratingUpdated)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeHasFieldErrors("rating", "orderNumber"))
                .andExpect(model().attribute("rating", hasProperty("orderNumber", is(orderNumber))))
                .andDo(print());

        verifyZeroInteractions(ratingService);
    }

    @Test
    @DisplayName("Checking that the user is redirected to the rating/list page when rating's data are correctly deleted")
    public void shouldReturnRatingListPageViewWhenRatingDataAreCorrectlyDeleted() throws Exception {
        doNothing().when(ratingService).delete(1);

        this.mockMvc.perform(get("/rating/delete/{id}", 1))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(model().attribute("rating", nullValue()))
                .andDo(print());

        verify(ratingService).delete(1);
    }
}
