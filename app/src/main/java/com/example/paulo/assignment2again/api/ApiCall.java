package com.example.paulo.assignment2again.api;


import com.example.paulo.assignment2again.Model.MuscianBig;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface ApiCall {

    @GET("/search?amp;media=music&amp;entity=song&amp;limit=50")
    Call<MuscianBig> getMuscians(@Query("term") String term);


}

