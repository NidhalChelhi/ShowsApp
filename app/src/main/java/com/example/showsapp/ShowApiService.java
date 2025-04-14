package com.example.showsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ShowApiService {
    @GET("api/shows")
    Call<List<Show>> getAllShows();

    @GET("api/shows/{id}")
    Call<Show> getShowById(@Path("id") String id);

    @POST("api/reservations")
    Call<Reservation> createReservation(@Body ReservationRequest request);
}