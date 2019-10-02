package com.example.healthx.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.text.format.DateFormat;

import com.example.healthx.R;
import com.example.healthx.constants.Constants;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

import static com.example.healthx.constants.Constants.COMMA_SPACE;
import static com.example.healthx.constants.Constants.DATE;
import static com.example.healthx.constants.Constants.EMPTY;
import static com.example.healthx.constants.Constants.SELECTED_DAY;
import static com.example.healthx.constants.Constants.SPACE;
import static com.example.healthx.constants.Constants.DASH;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    HorizontalCalendar horizontalCalendar;
    int mDate, month, year;
    ImageView ivAddDetail;
    String dayOfTheWeek, selectedDay, monthString, monthNumber, mYear;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        setValues();
        setListeners();
        return view;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);

        ivAddDetail = view.findViewById(R.id.ivAddDetail);
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add((Calendar.MONTH) + 1, -1);
        mDate = startDate.get(Calendar.DATE);
        month = startDate.get(Calendar.MONTH);
        year = startDate.get(Calendar.YEAR);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

    }

    @Override
    protected void setValues() {
        super.setValues();

        getParentActivity().setToolbarTitle(getResources().getString(R.string.app_name));
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        ivAddDetail.setOnClickListener(this);

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {

                month = date.get(Calendar.MONTH) + 1;
                year = date.get(Calendar.YEAR);
                dayOfTheWeek = (String) DateFormat.format(SELECTED_DAY, date); // Thursday
                selectedDay = (String) DateFormat.format(Constants._DATE, date); // 20
                monthString = (String) DateFormat.format(Constants.MONTH_NAME, date); // Jun
                monthNumber = (String) DateFormat.format(Constants.MONTH_NUMBER, date); // 06
                mYear = (String) DateFormat.format(Constants.YEAR, date); //
                getParentActivity().setToolbarTitle(EMPTY + dayOfTheWeek + COMMA_SPACE + monthString + SPACE + selectedDay + COMMA_SPACE + mYear);
                Bundle bundle = new Bundle();
                bundle.putString(DATE, selectedDay + DASH + month + DASH + year);
                getParentActivity().switchFragmentWithBackStack(AddFoodRecordFragment.getInstance(), bundle);
            }
        }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAddDetail: {
                switchFragment(AddFoodRecordFragment.getInstance());
                break;
            }
        }
    }
}
