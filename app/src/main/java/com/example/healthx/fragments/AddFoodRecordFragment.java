package com.example.healthx.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.healthx.R;
import com.example.healthx.database.DatabaseHelper;
import com.example.healthx.utils.Utils;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import java.util.ArrayList;
import java.util.Calendar;
import static com.example.healthx.constants.Constants.BREAKFAST;
import static com.example.healthx.constants.Constants.DATE;
import static com.example.healthx.constants.Constants.DATE_PICKER;
import static com.example.healthx.constants.Constants.DINNER;
import static com.example.healthx.constants.Constants.DRINK;
import static com.example.healthx.constants.Constants.Database.Food.FOOD;
import static com.example.healthx.constants.Constants.Database.Food.ID;
import static com.example.healthx.constants.Constants.Database.Food.LOCATION;
import static com.example.healthx.constants.Constants.Database.Food.MEAL;
import static com.example.healthx.constants.Constants.Database.Food.TIME;
import static com.example.healthx.constants.Constants.EMPTY;
import static com.example.healthx.constants.Constants.IS_UPDATE;
import static com.example.healthx.constants.Constants.LUNCH;
import static com.example.healthx.constants.Constants.NO_BUNDLE;
import static com.example.healthx.constants.Constants.UPDATE_RECORD;

public class AddFoodRecordFragment extends BaseFragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    String id, date, time, mealType = EMPTY;
    EditText etDate, etFood, etLocation;
    TextView tvAddRecord;
    TimePickerDialog timePickerDialog;
    LinearLayout llAddNew;
    private AppCompatAutoCompleteTextView tvMeal;
    ArrayList meals = new ArrayList<>();
    DatabaseHelper databaseHelper;
    private int isUpdate = 0;

    public static AddFoodRecordFragment getInstance() {
        return new AddFoodRecordFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_food_record, container, false);
        initViews(view);
        setValues();
        setListeners();
        return view;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        etDate = view.findViewById(R.id.etDate);
        etFood = view.findViewById(R.id.etFood);
        etLocation = view.findViewById(R.id.etLocation);
        tvMeal = view.findViewById(R.id.actvMeal);
        llAddNew = view.findViewById(R.id.llAddNew);
        tvAddRecord = view.findViewById(R.id.tvAddRecord);
    }

    @Override
    protected void setValues() {
        super.setValues();

        if (getArguments() != NO_BUNDLE) {
            date = getArguments().getString(DATE);
            if (getArguments().getBoolean(IS_UPDATE)) {
                id = getArguments().getString(ID);
                time = (getArguments().getString(TIME));
                mealType = (getArguments().getString(MEAL));
                etDate.setText(time);
                tvMeal.setText(mealType);
                etFood.setText(getArguments().getString(FOOD));
                etLocation.setText(getArguments().getString(LOCATION));
                tvAddRecord.setText(UPDATE_RECORD);
                llAddNew.setEnabled(false);
                isUpdate = 1;
            }
        } else {
            date = String.valueOf(Utils.getCurrentDateAndTime());
            Log.d("Input date", date);
            getParentActivity().setToolbarTitle(Utils.getSecondFormateDate());
            isUpdate = 0;
        }
        mealTypes();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void setListeners() {
        super.setListeners();
        llAddNew.setOnClickListener(this);
        tvAddRecord.setOnClickListener(this);
        etDate.setOnTouchListener((v, event) -> {
            timePicker();
            return true;
        });
    }

    @Override
    public void onClick(View v) {

        if (isUpdate == 0){
            addFoodRecord();
        }else{
            updateFoodRecord();
        }

//        switch (v.getId()) {
//            case R.id.llAddNew: {
//                addFoodRecord();
//                break;
//            }
//            case R.id.tvAddRecord: {
//                updateFoodRecord();
//                break;
//            }
//
//        }

    }
    private void clearEditTexts()
    {
        etFood.setText(EMPTY);
        etDate.setText(EMPTY);
        etLocation.setText(EMPTY);
        tvMeal.setText(EMPTY);
    }

    /**
     * This method is used to store data in database.
     */
    private void addFoodRecord() {

        try {

            databaseHelper = DatabaseHelper.getInstance(getActivity(), null, null);
            if (databaseHelper.insertFoodRecord(date, time, mealType, etFood.getText().toString(), etLocation.getText().toString())) {
                Toast.makeText(requireActivity(), getResources().getString(R.string.food_stored), Toast.LENGTH_LONG).show();
                clearEditTexts();
                clearAllFragments();
            } else {
                Toast.makeText(requireActivity(), getResources().getString(R.string.food_not_stored), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateFoodRecord() {

        try {

            databaseHelper = DatabaseHelper.getInstance(getActivity(), null, null);
            if (databaseHelper.updateFoodRecord(id, date, time, mealType, etFood.getText().toString(), etLocation.getText().toString())) {
                Toast.makeText(requireActivity(), getResources().getString(R.string.food_stored), Toast.LENGTH_LONG).show();
                clearEditTexts();
                timePickerDialog=null;
                clearAllFragments();
            } else {

                Toast.makeText(requireActivity(), getResources().getString(R.string.food_not_stored), Toast.LENGTH_LONG).show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mealTypes() {
        meals.add(BREAKFAST);
        meals.add(DINNER);
        meals.add(DRINK);
        meals.add(LUNCH);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (requireActivity(), android.R.layout.select_dialog_item, meals);
        tvMeal.setThreshold(1); //will start working from first character
        tvMeal.setAdapter(adapter);
        tvMeal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("beforeTextChanged", String.valueOf(s));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("onTextChanged", String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged", String.valueOf(s));
                mealType = String.valueOf(s);
            }
        });
    }


    private void timePicker() {
        Calendar now = Calendar.getInstance();
        if (timePickerDialog==null) {
            timePickerDialog = TimePickerDialog.newInstance(this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    now.get(Calendar.SECOND), true);
            timePickerDialog.show(requireFragmentManager(), DATE_PICKER);
        }


    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        time = hourOfDay + ":" + minute;
        etDate.setText(time);
    }

    @Override
    protected void onBackPressed() {
        getParentActivity().setToolbarTitle(getResources().getString(R.string.app_name));
        super.onBackPressed();
    }
}
