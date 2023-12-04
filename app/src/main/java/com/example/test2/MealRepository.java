package com.example.test2;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MealRepository {
    private final MealDao mealDao;
    private LiveData<List<Meal>> mealsByDate;
    private List<Meal> mealsAll;
    private LiveData<List<Meal>> recentMeals;
    public MealRepository(Application application) {
        MealDatabase db = MealDatabase.getDatabase(application);
        mealDao = db.mealDao();
        getRecentMeals();
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

    public LiveData<List<Meal>> getRecentMeals() {
        // 현재 날짜에서 1달 전 날짜를 계산
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        String oneMonthAgo = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(calendar.getTime());

        // Room 데이터베이스에서 최근 1달 동안의 식사 정보를 조회
        return mealDao.getRecentMeals(oneMonthAgo);
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
