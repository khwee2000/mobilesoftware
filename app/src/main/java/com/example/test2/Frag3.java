package com.example.test2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import org.w3c.dom.Text;

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
            public ImageView mealImage;

            //리사이클러뷰 id 찾기
            public MealViewHolder(View view) {
                super(view);
                mealName = view.findViewById(R.id.meal_name);
                mealType = view.findViewById(R.id.meal_type);
                mealCost = view.findViewById(R.id.meal_cost);
                mealPlace = view.findViewById(R.id.meal_place);
            }
        }
        // 리사이클러뷰에 바인딩할 목록(리사이클러뷰에 보여줄 목록)
        @Override
        public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
            Meal meal = mealList.get(position);
            holder.mealName.setText(meal.getMeal_name());
            holder.mealType.setText(meal.getMeal_type());
            holder.mealCost.setText(String.valueOf(meal.getMeal_cost()));
            holder.mealPlace.setText(meal.getMeal_place());


            //리사이클러뷰를 클릭했을 때 보여지는 커스텀 다이얼로그 창
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Dialog 객체를 생성합니다.
                    Dialog dialog = new Dialog(v.getContext());

                    String uriString = meal.getImageUri();
                    Uri uri = Uri.parse(uriString);

                    // 커스텀 뷰를 설정합니다.
                    dialog.setContentView(R.layout.meal_dialog);

                    TextView meal_name = dialog.findViewById(R.id.meal_name);
                    TextView meal_type = dialog.findViewById(R.id.meal_type);
                    TextView meal_cost = dialog.findViewById(R.id.meal_cost);
                    TextView meal_place = dialog.findViewById(R.id.meal_place);
                    TextView meal_data = dialog.findViewById(R.id.meal_data);
                    TextView meal_time = dialog.findViewById(R.id.meal_time);
                    TextView meal_review = dialog.findViewById(R.id.meal_review);
                    TextView meal_cal = dialog.findViewById(R.id.meal_calorie);

                    ImageView meal_img = dialog.findViewById(R.id.iv_meal);
                    Glide.with(getActivity()).load(uri).into(meal_img);

                    meal_name.setText(meal.getMeal_name());
                    meal_type.setText(meal.getMeal_type());
                    meal_cost.setText(String.valueOf(meal.getMeal_cost()));
                    meal_place.setText(meal.getMeal_place());
                    meal_data.setText(meal.getMeal_data());
                    meal_time.setText(meal.getMeal_time());
                    meal_review.setText(String.valueOf(meal.getMeal_review()));

                    // meal_type에 따라 meal_cal 값을 설정합니다.
                    String type = meal.getMeal_type();
                    if (type.equals("조식")) {
                        meal_cal.setText("400 kcal");
                    } else if (type.equals("중식")) {
                        meal_cal.setText("500 kcal");
                    } else if (type.equals("석식")) {
                        meal_cal.setText("600 kcal");
                    } else if (type.equals("음료")) {
                        meal_cal.setText("100 kcal");
                    }

                    // 다이얼로그의 크기와 위치를 조정합니다.
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    // 다이얼로그를 보여줍니다.
                    dialog.show();
                }

            });
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
