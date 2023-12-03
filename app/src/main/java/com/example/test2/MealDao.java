package com.example.test2;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MealDao {

    @Insert
    void insert(Meal meal);
    @Update
    void setUpdateMeal(Meal meal);
    @Delete
    void setDeleteMeal(Meal meal);


    @Query("SELECT * FROM Meal")
    List<Meal> getMealAll();

    @Query("SELECT * FROM Meal WHERE meal_data = :date")
    LiveData<List<Meal>> getMealsByDate(String date);

}
