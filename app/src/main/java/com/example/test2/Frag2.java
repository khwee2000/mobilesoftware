package com.example.test2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.Calendar;

public class Frag2 extends Fragment {
    private View view;
    Dialog dialog01;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;

    private TextView meal_type_tv; //텍스트뷰 선언
    private TextView place_tv;
    private TextView food_name_tv;
    private TextView meal_cost_tv;
    private TextView meal_date_tv;
    private TextView meal_time_tv;
    private TextView food_review_tv;
    private MealViewModel viewModel;

    private String uriString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2,container,false);


        Button meal_type_btn = view.findViewById(R.id.meal_type_btn);//버튼 선언 및 find
        Button place_btn = view.findViewById(R.id.place_btn);
        Button food_name_btn = view.findViewById(R.id.food_name_btn);
        Button meal_cost_btn = view.findViewById(R.id.meal_cost_btn);
        Button meal_date_btn = view.findViewById(R.id.meal_date_btn);
        Button meal_time_btn = view.findViewById(R.id.meal_time_btn);
        Button picture_btn = view.findViewById(R.id.picture_btn);
        Button register_btn = view.findViewById(R.id.register_btn);


        meal_type_tv = view.findViewById(R.id.meal_type_tv);//텍스트뷰 선언 및 find
        place_tv = view.findViewById(R.id.place_tv);
        food_name_tv = view.findViewById(R.id.food_name_tv);
        meal_cost_tv = view.findViewById(R.id.meal_cost_tv);
        meal_date_tv = view.findViewById(R.id.meal_date_tv);
        meal_time_tv = view.findViewById(R.id.meal_time_tv2);
        food_review_tv = view.findViewById(R.id.food_review_tv);

        RatingBar ratingBar = view.findViewById(R.id.ratingBar);//RatingBar
        imageView = view.findViewById(R.id.meal_img); // 이미지 뷰





        meal_type_btn.setOnClickListener(new View.OnClickListener() {
            final String[] mealtype = new String[]{"조식","중식","석식","음료"}; //식사 종류
            final int[] selectedIndex = {0};
            @Override
            public void onClick(View view) {AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("식사 종류 선택").setSingleChoiceItems(mealtype, 0, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        selectedIndex[0] = i;
                    }
                }).setPositiveButton("확인", new DialogInterface.OnClickListener() { //확인 했을때, String 형태로 저장
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String result = mealtype[selectedIndex[0]].toString();
                        meal_type_tv.setText(result);

                    }

                });
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() { //취소 했을때
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show(); //최종 보여주기
            }
        }); //식사 종류 선택
        place_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("장소 입력");
                final EditText place = new EditText(getActivity());
                dialog.setView(place);
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() { // 확인했을때, String 형태로 저장
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String result = place.getText().toString();
                        place_tv.setText(result);
                    }
                });

                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show(); //최종 보여주기
            }
        });//식사 장소 입력
        food_name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("식사 이름 입력");
                final EditText foodName = new EditText(getActivity());
                dialog.setView(foodName);
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() { // 확인했을때, String 형태로 저장
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String result = foodName.getText().toString();
                        food_name_tv.setText(result);
                    }
                });

                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show(); //최종 보여주기
            }
        });//식사 이름 입력


        meal_cost_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("식사 가격을 입력하십시오.");
                final EditText mealCost = new EditText(getActivity());
                dialog.setView(mealCost);
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String result = mealCost.getText().toString();
                        int mealCostValue = 0;
                        try {
                            mealCostValue = Integer.parseInt(result);
                            meal_cost_tv.setText(String.valueOf(mealCostValue));
                        } catch (NumberFormatException e) {
                            Toast.makeText(getActivity(), "잘못된 값이 입력되어 0원으로 설정됩니다.", Toast.LENGTH_SHORT).show();
                            meal_cost_tv.setText(String.valueOf(mealCostValue));
                        }
                    }
                });
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show(); //최종 보여주기
            }
        });//식사 가격 입력

        meal_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        String formattedDate = String.format("%04d/%02d/%02d", year, month + 1, dayOfMonth);
                        meal_date_tv.setText(formattedDate);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }

        }); //식사 날짜 입력
        meal_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR);
                int mMinute = c.get(Calendar.MINUTE);


            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                    meal_time_tv.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        }); //식사 시간 입력
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String ratingText = String.valueOf((int)rating);
                food_review_tv.setText(ratingText);
            }

        });
        picture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요"), PICK_IMAGE_REQUEST);

            }

        }); //사진 입력

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            uriString = uri.toString();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } //사진 입력 함수

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MealViewModel.class);
    } //DB이용
    private void insertData() {
        String mealType = meal_type_tv.getText().toString();
        String mealPlace = place_tv.getText().toString();
        String mealName = food_name_tv.getText().toString();
        String mealCost = meal_cost_tv.getText().toString();
        String mealData = meal_date_tv.getText().toString();
        String mealTime = meal_time_tv.getText().toString();
        String mealReview = food_review_tv.getText().toString();

        if (mealType.isEmpty() || mealPlace.isEmpty() || mealName.isEmpty() ||
                mealCost.isEmpty() || mealData.isEmpty() || mealTime.isEmpty() ||
                mealReview.isEmpty() || uriString == null) {
            Toast.makeText(getActivity(), "전부 입력해주십시오", Toast.LENGTH_SHORT).show();
        } else {
            Meal meal = new Meal();
            meal.setMeal_type(mealType);
            meal.setMeal_place(mealPlace);
            meal.setMeal_name(mealName);
            meal.setMeal_cost(Integer.parseInt(mealCost));
            meal.setMeal_data(mealData);
            meal.setMeal_time(mealTime);
            meal.setMeal_review(Integer.parseInt(mealReview));
            meal.setImageUri(uriString);
            viewModel.insert(meal);

            Toast.makeText(getActivity(),"등록이 완료되었습니다!",Toast.LENGTH_SHORT).show();

            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigationVar);
            bottomNavigationView.setSelectedItemId(R.id.action_home);
            // Frag1으로 화면 전환
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, new Frag1());
            fragmentTransaction.commit();


        } //데베 Insert 함수
    }



    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}
