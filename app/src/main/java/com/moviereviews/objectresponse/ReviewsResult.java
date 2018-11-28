package com.moviereviews.objectresponse;

import java.util.List;

public class ReviewsResult {
    private boolean has_more;
    private List<Review> reviews;

    public ReviewsResult(boolean has_more, List<Review> reviews) {
        this.has_more = has_more;
        this.reviews = reviews;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
