package com.example.lesson03.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Diary implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("brand")
    private String brand;

    @SerializedName("paper_type")
    private String paper_type;

    @SerializedName("paper_format")
    private String paper_format;

    @SerializedName("paper_count")
    private int paper_count;

    @SerializedName("price")
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPaper_type() {
        return paper_type;
    }

    public void setPaper_type(String paper_type) {
        this.paper_type = paper_type;
    }

    public String getPaper_format() {
        return paper_format;
    }

    public void setPaper_format(String paper_format) {
        this.paper_format = paper_format;
    }

    public int getPaper_count() {
        return paper_count;
    }

    public void setPaper_count(int paper_count) {
        this.paper_count = paper_count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
