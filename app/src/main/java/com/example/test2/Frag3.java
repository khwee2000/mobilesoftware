package com.example.test2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Frag3 extends Fragment {
    private View view;

    private CalendarView calendarView;

    private TextView selected_date;
    private String selectedDate;

    private RecyclerView meal_list;
    private MealViewModel mealViewModel;
    private MealListAdapter adapter;
    private Button search_btn;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3,container,false);


        selected_date = view.findViewById(R.id.selected_date); //TextView 불러오기
        calendarView = view.findViewById(R.id.calendarView2); // 캘린더 뷰 불러오기
        meal_list = view.findViewById(R.id.meal_list);//  리사이클러뷰 불러오기
        meal_list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MealListAdapter(new ArrayList<>()); // 어댑터 초기화
        meal_list.setAdapter(adapter); // 어댑터 설정


        // CalendarView 초기화 및 날짜 선택 이벤트 처리
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 선택된 날짜를 YYYY/MM/DD 형태의 문자열로 변환
                selectedDate = String.format("%04d/%02d/%02d", year, month + 1, dayOfMonth);
                selected_date.setText(selectedDate);
                Toast.makeText(getActivity(),selectedDate,Toast.LENGTH_SHORT).show();
                // 선택된 날짜에 해당하는 식사 데이터를 가져옵니다.
                mealViewModel.getMealsByDate(selectedDate).observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
                    @Override
                    public void onChanged(List<Meal> meals) {
                        // 가져온 데이터를 RecyclerView에 바인딩합니다.
                        adapter.setMeals(meals);
                        Log.d("MealListAdapter", "Number of meals: " + adapter.getItemCount()); // 항목 개수를 로그로 출력
                    }
                });
            }
        });

        return view;
    }
    public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.MealViewHolder> {
        private List<Meal> mealList; // 데이터 리스트

        // ViewHolder 클래스
        public class MealViewHolder extends RecyclerView.ViewHolder {
            public TextView mealName;
            public TextView mealType;
            public TextView mealCost;
            public TextView mealPlace;
            public ImageView iv_meal;

            //리사이클러뷰 id 찾기
            public MealViewHolder(View view) {
                super(view);
                mealName = view.findViewById(R.id.meal_name);
                mealType = view.findViewById(R.id.meal_type);
                mealCost = view.findViewById(R.id.meal_cost);
                mealPlace = view.findViewById(R.id.meal_place);
                iv_meal = view.findViewById(R.id.iv_meal);
            }
        }
        // 리사이클러뷰에 바인딩할 목록
        @Override
        public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
            Meal meal = mealList.get(position);
            holder.mealName.setText(meal.getMeal_name());
            holder.mealType.setText(meal.getMeal_type());
            holder.mealCost.setText(String.valueOf(meal.getMeal_cost()));
            holder.mealPlace.setText(meal.getMeal_place());
            //Glide를 사용하여 이미지 로드(미완성)
            Glide.with(holder.itemView.getContext())
                    .load(meal.getImageUri())
                    .into(holder.iv_meal);
        }
        public MealListAdapter(List<Meal> mealList) {
            this.mealList = mealList;
        }

        // ViewHolder를 생성하고 뷰를 붙여주는 부분
        @NonNull
        @Override
        public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
            return new MealViewHolder(itemView);
        }



        // 리사이클러뷰에 보여줄 데이터 개수를 반환
        @Override
        public int getItemCount() {
            if (mealList != null)
                return mealList.size();
            else
                return 0;
        }

        // 데이터 리스트를 바꿔치기하는 메서드
        public void setMeals(List<Meal> meals) {
            mealList = meals;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}
