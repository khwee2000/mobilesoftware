package com.example.test2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Meal {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String meal_type;
    private String meal_place;
    private String meal_name;
    private int meal_cost;
    private String meal_data;
    private String meal_time;
    private int meal_review;
    private String imageUri;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeal_type() {
        return meal_type;
    }

    public void setMeal_type(String meal_type) {
        this.meal_type = meal_type;
    }

    public String getMeal_place() {
        return meal_place;
    }

    public void setMeal_place(String meal_place) {
        this.meal_place = meal_place;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public int getMeal_cost() {
        return meal_cost;
    }

    public void setMeal_cost(int meal_cost) {
        this.meal_cost = meal_cost;
    }

    public String getMeal_data() {
        return meal_data;
    }

    public void setMeal_data(String meal_data) {
        this.meal_data = meal_data;
    }

    public String getMeal_time() {
        return meal_time;
    }

    public void setMeal_time(String meal_time) {
        this.meal_time = meal_time;
    }

    public int getMeal_review() {
        return meal_review;
    }

    public void setMeal_review(int meal_review) {
        this.meal_review = meal_review;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
