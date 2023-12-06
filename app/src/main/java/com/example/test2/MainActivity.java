package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;


import android.os.Bundle;
import android.view.MenuItem;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MealDao mMealDado;

    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;//프래그먼트 메니저
    private FragmentTransaction ft;
    private Frag1 frag1;
    private Frag2 frag2;
    private Frag3 frag3;
    private Frag4 frag4;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MealDatabase database = Room.databaseBuilder(getApplicationContext(),MealDatabase.class,"meal_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build(); //DB 생성

        mMealDado = database.mealDao(); //인터페이스 객체 할당





        bottomNavigationView = findViewById(R.id.navigationVar);
        frag1 = new Frag1();
        frag2 = new Frag2();
        frag3 = new Frag3();
        frag4 = new Frag4();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    setFrag(0);
                } else if (itemId == R.id.action_write) {
                    setFrag(1);
                } else if (itemId == R.id.action_after) {
                    setFrag(2);
                } else if (itemId == R.id.action_analysis) {
                    setFrag(3);
                }
                return true;
            }
        });

        setFrag(0); //첫 프래그 화면
    }

    //fragment 교체
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (n){
            case 0:
                ft.replace(R.id.main_frame, frag1);
                break;
            case 1:
                ft.replace(R.id.main_frame, frag2);
                break;
            case 2:
                ft.replace(R.id.main_frame, frag3);
                break;
            case 3:
                ft.replace(R.id.main_frame, frag4);
                break;
        }
        ft.commit();
    }

}