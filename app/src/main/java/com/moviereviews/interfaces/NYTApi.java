package com.moviereviews.interfaces;

import com.moviereviews.objectresponse.Critics;
import com.moviereviews.objectresponse.Reviews;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NYTApi {

    String apiKey = "b4d57bcd057d49f1bb5419da52469f95";

    @GET("reviews/search.json?api-key=" + apiKey)
    Observable<Reviews> getReviewsObservable(@Query("offset") int offset, @Query("query") String title, @Query("publication-date") String publication_date);

    @GET("critics/{name}.json?api-key="+apiKey)
    Observable<Critics> getCritics(@Path("name") String name);


    @GET("reviews/search.json?api-key="+apiKey)
    Observable<Reviews> getCriticReviews( @Query("offset") int offset, @Query("reviewer") String reviewer);
}
