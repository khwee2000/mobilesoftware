package com.example.test2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MealViewModel extends AndroidViewModel {
    private MealRepository repository;

    public MealViewModel(@NonNull Application application) {
        super(application);
        repository = new MealRepository(application);
    }

    public void insert(Meal meal)
    {
        repository.insert(meal);
    }
    public LiveData<List<Meal>> getMealsByDate(String date) {
        return repository.getMealsByDate(date);
    } //날짜별 찾기

    public List<Meal> getMealAll(){
        return repository.getMealAll();
    } //전부 다 찾기

    public LiveData<List<Meal>> getRecentMeals() {
        return repository.getRecentMeals();
    }//최근 1달 동안의 식사 정보를 가져오기
}
