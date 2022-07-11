package com.example.lesson03;

import com.example.lesson03.entity.Chalk;
import com.example.lesson03.entity.ColoredPaper;
import com.example.lesson03.entity.Diary;
import com.example.lesson03.entity.Item;
import com.example.lesson03.entity.Order;
import com.example.lesson03.entity.PenBox;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PlaceHolderApi {

    @GET("chalks/{id}")
    Call<Chalk>getChalkById(@Path("id") int id);

    @GET("chalks")
    Call<List<Chalk>>getAllChalks();

    @GET("colored_papers/{id}")
    Call<ColoredPaper>getColoredPaperById(@Path("id") int id);

    @GET("colored_papers")
    Call<List<ColoredPaper>>getAllColoredPapers();

    @GET("diaries/{id}")
    Call<Diary>getDiaryById(@Path("id") int id);

    @GET("diaries")
    Call<List<Diary>>getAllDiaries();

    @GET("pen_boxes/{id}")
    Call<PenBox>getPenBoxById(@Path("id") int id);

    @GET("pen_boxes")
    Call<List<PenBox>>getAllPenBoxes();

    @POST("orders")
    Call<Order> postOrder(@Body Order order);
}
