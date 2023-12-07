package com.example.test2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Frag4 extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private MealAdapter mealAdapter;
    private MealViewModel viewModel;
    private TextView analysis_cost_tv,total_cal_tv;
    private Button analysis_btn,cal_analysis_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag4,container,false);
        viewModel = new ViewModelProvider(this).get(MealViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        analysis_cost_tv = view.findViewById(R.id.analysis_up_tv);
        total_cal_tv = view.findViewById(R.id.total_cal_tv);

        analysis_btn = view.findViewById(R.id.meal_analysis_btn);
        cal_analysis_btn = view.findViewById(R.id.cal_analysis_btn);


        analysis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("식사 유형 선택");

                String[] mealTypes = {"조식", "중식", "석식", "음료"};
                builder.setSingleChoiceItems(mealTypes, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedMealType = mealTypes[which];
                        viewModel.getCostsByMealType(selectedMealType).observe(getViewLifecycleOwner(), new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer cost) {
                                analysis_cost_tv.setText(String.valueOf(cost));
                            }
                        });
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        cal_analysis_btn.setOnClickListener(new View.OnClickListener() {
            private int totalCalories = 0;
            @Override
            public void onClick(View v) {
                String[] mealTypes = {"조식", "중식", "석식", "음료"};
                int[] caloriesPerMeal = {400, 500, 600, 100};

                for (int i = 0; i < mealTypes.length; i++) {
                    final int index = i;
                    viewModel.getCountByMealType(mealTypes[i]).observe(getViewLifecycleOwner(), new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer count) {
                            totalCalories += count * caloriesPerMeal[index];
                            total_cal_tv.setText(String.valueOf(totalCalories));
                            Log.d("MealListAdapter", "Meal type: "+mealTypes[index]+"  "+caloriesPerMeal[index] * count);
                        }
                    });
                }
            }

        });


        // 최근 1달 동안의 식사 정보를 가져오는 메서드를 호출하고 그 결과를 관찰합니다.
        viewModel.getRecentMeals().observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                mealAdapter = new MealAdapter(meals);
                recyclerView.setAdapter(mealAdapter);
            }
        });

        return view;
    }
}





