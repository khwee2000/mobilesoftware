package com.example.test2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<Meal> meals;

    public MealAdapter(List<Meal> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealName.setText(meal.getMeal_name());
        holder.mealType.setText(meal.getMeal_type());
        holder.mealCost.setText(String.valueOf(meal.getMeal_cost()));
        holder.mealPlace.setText(meal.getMeal_place());
        // meal 이미지 설정하는 방법은 Meal 객체의 구조에 따라 달라집니다.
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMeal;
        TextView mealName;
        TextView mealType;
        TextView mealCost;
        TextView mealPlace;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMeal = itemView.findViewById(R.id.iv_meal);
            mealName = itemView.findViewById(R.id.meal_name);
            mealType = itemView.findViewById(R.id.meal_type);
            mealCost = itemView.findViewById(R.id.meal_cost);
            mealPlace = itemView.findViewById(R.id.meal_place);
        }
    }
}
