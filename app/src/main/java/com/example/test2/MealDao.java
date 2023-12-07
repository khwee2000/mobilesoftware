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

    @Query("SELECT * FROM Meal WHERE meal_data >= :oneMonthAgo ORDER BY meal_data DESC")
    LiveData<List<Meal>> getRecentMeals(String oneMonthAgo);

    @Query("SELECT SUM(meal_cost) FROM Meal WHERE meal_data >= :oneMonthAgo AND meal_type = :mealType")
    LiveData<Integer> getCostsByMealType(String oneMonthAgo, String mealType);

    @Query("SELECT COUNT(*) FROM Meal WHERE meal_type = :mealType")
    LiveData<Integer> getCountByMealType(String mealType);
}
