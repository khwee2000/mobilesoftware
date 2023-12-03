package com.example.test2;

import android.app.Application;
import android.os.AsyncTask;

public class MealRepository {
    private MealDao mealDao;

    public MealRepository(Application application) {
        MealDatabase db = MealDatabase.getDatabase(application);
        mealDao = db.mealDao();
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
