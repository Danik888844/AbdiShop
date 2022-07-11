package com.example.lesson03.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PenBox implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("brand")
    private String brand;

    @SerializedName("color")
    private String color;

    @SerializedName("clasp_type")
    private String clasp_type;

    @SerializedName("material")
    private String material;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getClasp_type() {
        return clasp_type;
    }

    public void setClasp_type(String clasp_type) {
        this.clasp_type = clasp_type;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
