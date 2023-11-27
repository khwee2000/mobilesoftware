package com.example.test2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Frag1 extends Fragment {
    private View view;
    private CalendarView calendarView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag1, container, false);
        calendarView = view.findViewById(R.id.calendarView);

        // 현재 날짜를 설정
        Calendar currentDate = Calendar.getInstance();
        calendarView.setDate(currentDate.getTimeInMillis());

        // 선택 가능한 날짜 제한: 현재 날짜부터 7일 후까지
        calendarView.setMinDate(currentDate.getTimeInMillis());
        currentDate.add(Calendar.DAY_OF_MONTH, 7);
        calendarView.setMaxDate(currentDate.getTimeInMillis());

        // 캘린더뷰에 리스너 추가
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                // 선택된 날짜의 주 출력
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
                String formattedDate = sdf.format(selectedDate.getTime());

                Toast.makeText(requireContext(), "Selected date: " + formattedDate, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

