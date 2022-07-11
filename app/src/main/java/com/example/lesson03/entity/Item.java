package com.example.lesson03.entity;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private int price;
    private int image;
    private int count = 1;
    private int idForFind; // ЭТО АЙДИ ТОВАРА В БАЗЕ ДАННЫХ, НУЖЕН ДЛЯ ПОИСКА ИСПОЛЬЗУЯ КАРТИНКИ это моя "категория"

    public Item() {
    }

    public Item(String name, int price, int image,int idForFind) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.idForFind = idForFind;
        this.count = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getIdForFind() {
        return idForFind;
    }

    public void setIdForFind(int idForFind) {
        this.idForFind = idForFind;
    }
}
