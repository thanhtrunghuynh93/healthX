package com.example.healthx.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import com.example.healthx.R;
import com.example.healthx.constants.Constants;
import com.example.healthx.database.DatabaseHelper;
import com.example.healthx.fragments.HomeFragment;
import com.example.healthx.fragments.StoredFoodFragment;
import com.example.healthx.utils.AlertUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends BaseActivity implements View.OnClickListener ,DatePickerDialog.OnDateSetListener{
   public ImageView ivMenu, ivCalender;
    TextView tvTitle;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setValues();
        setListeners();
        setUpHomeFragment(new HomeFragment());
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivMenu = findViewById(R.id.ivMenu);
        ivCalender = findViewById(R.id.ivCalender);
        tvTitle = findViewById(R.id.tvTitle);
    }

    @Override
    protected void setValues() {
        super.setValues();
        initDataBase();
    }

    @Override
    protected void setListeners() {
        ivMenu.setOnClickListener(this);
        ivCalender.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivMenu:
            {
                startNewActivity(SettingActivity.class);
                //switchFragmentWithBackStack(SettingFragment.getInstance());
                break;
            }
            case R.id.ivCalender: {
                datePicker();
                break;
            }
        }
    }

    /**
     * Initialize database connection to store data.
     */
    private void initDataBase() {
        DatabaseHelper dbBM = DatabaseHelper.getInstance(this, null, null);
        try {
            dbBM.createDataBase(this);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void setToolbarTitle(String title) {
        tvTitle.setText(title);
    }


    public void datePicker() {
        Calendar now = Calendar.getInstance();
        /**
         * Initial day, month and year selection
         */

            datePickerDialog = DatePickerDialog.newInstance(
                    MainActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)

            );
            datePickerDialog.show(getSupportFragmentManager(), Constants.DATE_PICKER);

    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        /**
         * This is call back of selected date that we will use next to find data from database.
         */
        String date = dayOfMonth + "-" + (monthOfYear +1)+ "-" + year;
        Bundle bundle = new Bundle();
        bundle.putString(Constants.DATE,date);
        switchFragmentWithBackStack(StoredFoodFragment.getInstance(),bundle);

    }


    @Override
    public void onBackPressed() {
        if (fragmentsStack != null && fragmentsStack.size() > 1) {
            popFragment();
        }
        else {
            showExitAlert(getResources().getString(R.string.app_close));

        }
    }
    public void showExitAlert(String message) {

        AlertUtils.showAlert(this, Constants.ALERT, message, (dialog, which) -> {

            ActivityCompat.finishAffinity(MainActivity.this);
            dialog.dismiss();
            finish();
        });

    }
}
