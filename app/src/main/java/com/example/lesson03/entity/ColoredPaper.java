package com.example.lesson03.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ColoredPaper implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("brand")
    private String brand;

    @SerializedName("type")
    private String type;

    @SerializedName("format")
    private String format;

    @SerializedName("color_count")
    private int color_count;

    @SerializedName("papers_count")
    private int papers_count;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getColor_count() {
        return color_count;
    }

    public void setColor_count(int color_count) {
        this.color_count = color_count;
    }

    public int getPapers_count() {
        return papers_count;
    }

    public void setPapers_count(int papers_count) {
        this.papers_count = papers_count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
