package com.example.test2;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MealRepository {
    private final MealDao mealDao;
    private LiveData<List<Meal>> mealsByDate;
    private List<Meal> mealsAll;

    public MealRepository(Application application) {
        MealDatabase db = MealDatabase.getDatabase(application);
        mealDao = db.mealDao();

    }
    public List<Meal> getMealAll(){
        mealsAll = mealDao.getMealAll();
        return  mealsAll;
    }
    public LiveData<List<Meal>> getMealsByDate(String date) {
        Log.d("Meal", "Getting meals for date: " + date);
        mealsByDate = mealDao.getMealsByDate(date);
        return mealsByDate;
    }
    public void insert(Meal meal) {
        new insertAsyncTask(mealDao).execute(meal);
    }


    private static class insertAsyncTask extends AsyncTask<Meal, Void, Void> {
        private MealDao asyncTaskDao;

        insertAsyncTask(MealDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Meal... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
