package com.example.junit.pokemonReview.repository;

import com.example.junit.pokemonReview.models.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//run test cases against testContainers
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void ReviewRepository_SaveAll_ReturnsSavedReview() {
        Review review = Review.builder().title("title").content("content").stars(5).build();

        Review savedReview = reviewRepository.save(review);

        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepostory_GetAll_ReturnsMoreThenOneReview() {
        Review review = Review.builder().title("title").content("content").stars(5).build();
        Review review2 = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);
        reviewRepository.save(review2);

        List<Review> reviewList = reviewRepository.findAll();

        assertThat(reviewList).isNotNull();
        assertThat(reviewList.size()).isEqualTo(2);
    }

    @Test
    public void ReviewRepository_FindById_ReturnsSavedReview() {
        Review review = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);

        Review reviewReturn = reviewRepository.findById(review.getId()).get();

        assertThat(reviewReturn).isNotNull();
    }

    @Test
    public void ReviewRepository_UpdateReview_ReturnReview() {
        Review review = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);

        Review reviewSave = reviewRepository.findById(review.getId()).get();
        reviewSave.setTitle("title");
        reviewSave.setContent("content");
        Review udpatedPokemon = reviewRepository.save(reviewSave);

        assertThat(udpatedPokemon.getTitle()).isNotNull();
        assertThat(udpatedPokemon.getContent()).isNotNull();
    }

    @Test
    public void ReviewRepository_ReviewDelete_ReturnReviewIsEmpty() {
        Review review = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);

        reviewRepository.deleteById(review.getId());
        Optional<Review> reviewReturn = reviewRepository.findById(review.getId());

        assertThat(reviewReturn).isEmpty();
    }
}
