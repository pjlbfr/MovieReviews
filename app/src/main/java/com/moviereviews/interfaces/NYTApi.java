package com.moviereviews.interfaces;

import com.moviereviews.objectresponse.Critics;
import com.moviereviews.objectresponse.Reviews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NYTApi {

    String apiKey = "b4d57bcd057d49f1bb5419da52469f95";

    @GET("reviews/all.json?api-key=" + apiKey)
    Call<Reviews> getReviews(@Query("offset") int offset);

    @GET("reviews/search.json?api-key="+apiKey)
    Call<Reviews> getSearchTitleReview(@Query("query") String title);

    @GET("reviews/search.json?api-key="+apiKey)
    Call<Reviews> getSearchPublicationDateReview(@Query("publication-date") String publication_date);

    @GET("critics/all.json?api-key="+apiKey)
    Call<Critics> getCritics();

    @GET("critics/{name}.json?api-key="+apiKey)
    Call<Critics> getSearchNameCritic(@Path("name") String name);

    @GET("reviews/search.json?api-key="+apiKey)
    Call<Reviews> getSearchCriticReviews(@Query("reviewer") String reviewer, @Query("offset") int offset);

}
