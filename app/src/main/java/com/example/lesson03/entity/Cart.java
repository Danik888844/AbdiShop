package com.example.lesson03.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {

    private int red;
    private int green;
    private int blue;
    private ArrayList<Item> items = new ArrayList<>();

    public Cart() {
    }

    public int getRed() {
        return red = (int) (Math.random() * 255);
    }

    public int getGreen() {
        return green = (int) (Math.random() * 255);
    }

    public int getBlue() {
        return blue = (int) (Math.random() * 255);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

}