package com.example.lesson03;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService instance;
    private Retrofit retrofit;

    private NetworkService(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.6:8080/spring_rest_war_exploded/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    public PlaceHolderApi getPlaceHolderApi() {
        return retrofit.create(PlaceHolderApi.class);
    }
}
