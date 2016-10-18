package com.josue.flickr;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrServiceHTTP {
    @GET("services/rest?method=flickr.photos.search&safe_search=1&per_page=5&format=json&nojsoncallback=1")
    Call <FlickrPhotosResponse> getPhotos(@Query("tags") String query, @Query("api_key") String queryAPI);
}
