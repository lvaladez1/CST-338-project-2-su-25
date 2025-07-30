package com.example.cst_338_project_2_su_25;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.cst_338_project_2_su_25.database.ReviewDao;
import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.entities.Review;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ReviewDaoTest {
    private RevuDatabase db;
    private ReviewDao reviewDao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RevuDatabase.class)
                .allowMainThreadQueries()
                .build();
        reviewDao = db.reviewDao();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testInsertReview() {
        Review review = new Review();
        review.setUserId(1);
        review.setTitle("Great Movie");
        review.setRating(4.5f);
        review.setReviewText("I really enjoyed this movie. The plot was engaging and the acting was superb.");
        reviewDao.insert(review);
        List<Review> all = reviewDao.getReviewsForUser(1);
        assertEquals(1, all.size());
    }

    @Test
    public void testUpdateReview() {
        Review review = new Review();
        review.setUserId(1);
        review.setTitle("Sample");
        review.setRating(4f);
        review.setReviewText("Good.");

        long insertedId = reviewDao.insert(review);
        review.setReviewId((int) insertedId);
        review.setReviewText("Updated Sample");
        review.setRating(5f);
        reviewDao.update(review);
        Review updated = reviewDao.getReviewsForUser(1).get(0);
        assertEquals("Updated Sample", updated.getReviewText());
        assertEquals(5f, updated.getRating(), 0.01);

    }

    @Test
    public void testDeleteReview() {
        Review review = new Review();
        review.setUserId(1);
        review.setTitle("Sample Review");
        review.setRating(3f);
        review.setReviewText("Not bad.");
        reviewDao.insert(review);

        Review inserted = reviewDao.getReviewsForUser(1).get(0);
        reviewDao.delete(inserted);
        List<Review> remaining = reviewDao.getReviewsForUser(1);
        assertTrue(remaining.isEmpty());
    }
}
