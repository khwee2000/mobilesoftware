package com.example.test2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class MealViewModel extends AndroidViewModel {
    private MealRepository repository;

    public MealViewModel(@NonNull Application application) {
        super(application);
        repository = new MealRepository(application);
    }

    public void insert(Meal meal) {
        repository.insert(meal);
    }
}
