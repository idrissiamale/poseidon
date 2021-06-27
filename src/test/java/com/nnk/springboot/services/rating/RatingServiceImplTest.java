package com.nnk.springboot.services.rating;

import com.nnk.springboot.domains.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
public class RatingServiceImplTest {
    @Mock
    private RatingRepository ratingRepository;
    private RatingServiceImpl ratingServiceImpl;
    private Rating rating;
    private List<Rating> ratings;

    @BeforeEach
    public void setUp() {
        ratingServiceImpl = new RatingServiceImpl(ratingRepository);
        rating = new Rating(1, "Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        Rating rating2 = new Rating(2, "Aaa", "AAA", "AAA", 16);
        ratings = new ArrayList<>();
        ratings.add(rating);
        ratings.add(rating2);

    }

    @Test
    @DisplayName("Checking that the new rating is correctly saved")
    public void shouldReturnNewRatingWhenSaved() {
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating ratingToSave = ratingServiceImpl.save(rating);

        verify(ratingRepository).save(any(Rating.class));
        assertNotNull(ratingToSave);
    }

    @Test
    @DisplayName("Comparing expected fitch rating and actual fitch rating to check that the new rating is correctly saved ")
    public void shouldGetTheSameFitchRatingWhenNewRatingIsCorrectlySaved() {
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating ratingToSave = ratingServiceImpl.save(rating);

        verify(ratingRepository).save(any(Rating.class));
        assertEquals("Fitch Rating", ratingToSave.getFitchRating());
    }

    @Test
    @DisplayName("Checking that the rating is updated with the new order")
    public void shouldReturnRatingWithNewOrderWhenRatingUpdated() {
        rating.setOrderNumber(25);
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating ratingUpdated = ratingServiceImpl.update(1, rating);

        verify(ratingRepository).save(any(Rating.class));
        assertNotNull(ratingUpdated);
    }

    @Test
    @DisplayName("Comparing expected order  and actual order to check that the rating was correctly updated ")
    public void shouldGetTheSameOrderWhenRatingCorrectlyUpdated() {
        rating.setOrderNumber(25);
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating ratingUpdated = ratingServiceImpl.update(1, rating);


        verify(ratingRepository).save(any(Rating.class));
        assertEquals(rating.getOrderNumber(), ratingUpdated.getOrderNumber());
    }

    @Test
    @DisplayName("Checking that the rating is correctly fetched by its id")
    public void shouldFindRatingByItsId() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Rating ratingToFind = ratingServiceImpl.findById(1);

        assertEquals("Moodys Rating", ratingToFind.getMoodysRating());
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when rating's id is null")
    public void shouldThrowExceptionWhenRatingIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> ratingServiceImpl.findById(null));
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when rating's id does not exist")
    public void shouldThrowExceptionWhenRatingIdDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> ratingServiceImpl.findById(7));
    }

    @Test
    @DisplayName("Checking that ratings are correctly fetched")
    public void shouldGetAllRatings() {
        when(ratingRepository.findAll()).thenReturn(ratings);

        List<Rating> ratingList = ratingServiceImpl.findAllRatings();

        verify(ratingRepository).findAll();
        assertEquals(ratings, ratingList);
    }

    @Test
    @DisplayName("Checking that rating, if found by its id, is correctly deleted")
    public void shouldDeleteRatingWhenFoundByItsId() {
        when(ratingRepository.findById(1)).thenReturn(Optional.ofNullable(rating));
        doNothing().when(ratingRepository).delete(rating);

        ratingServiceImpl.delete(rating.getId());

        verify(ratingRepository).delete(rating);
    }
}
