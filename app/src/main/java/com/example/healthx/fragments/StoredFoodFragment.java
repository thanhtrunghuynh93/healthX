package com.example.healthx.fragments;


import android.database.Cursor;
import android.os.Bundle;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthx.R;
import com.example.healthx.adapters.FoodsListAdapter;
import com.example.healthx.constants.Constants;
import com.example.healthx.database.DatabaseHelper;
import com.example.healthx.interfaces.ClickCallBack;
import com.example.healthx.adapters.SwipeToDeleteCallback;
import com.example.healthx.models.FoodListModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.healthx.constants.Constants.Database.Food.DATE;
import static com.example.healthx.constants.Constants.Database.Food.FOOD;
import static com.example.healthx.constants.Constants.Database.Food.ID;
import static com.example.healthx.constants.Constants.Database.Food.LOCATION;
import static com.example.healthx.constants.Constants.Database.Food.MEAL;
import static com.example.healthx.constants.Constants.Database.Food.TIME;
import static com.example.healthx.constants.Constants.EMPTY;
import static com.example.healthx.constants.Constants.NO_BUNDLE;
import static com.example.healthx.constants.Constants.SPACE;

public class StoredFoodFragment extends BaseFragment implements ClickCallBack {

    DatabaseHelper databaseHelper;
    RecyclerView rvFoodList;
    FoodsListAdapter adapter;
    TextView tvTitle;
    public static StoredFoodFragment getInstance() {
        return new StoredFoodFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stored_food, container, false);
        initViews(view);
        setValues();
        setListeners();
        return view;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        rvFoodList = view.findViewById(R.id.rvFoodList);
        tvTitle = view.findViewById(R.id.tvTitle);
    }

    @Override
    protected void setValues() {
        super.setValues();

        if (getArguments() != NO_BUNDLE)
        {
            String date = getArguments().getString(Constants.DATE);

            //Fix bug missing 0 in date (e.g. 01-10-2019 instead of 1-10-2019)
            if(date.split("-")[0].length() < 2){
                date = "0" + date;
            }

            tvTitle.setText(new StringBuilder().append(getResources().getString(R.string.food_list)).append(SPACE).append(date).toString());

            getFoodsResult(true,date);

        }
        else {
            getFoodsResult(false,EMPTY);
        }
    }

    @Override
    protected void setListeners() {
        super.setListeners();
    }

    private void getFoodsResult(boolean isSpecificRecord, String selectedDate) {
        try {

            Log.d("Input_Date", selectedDate);

            List<FoodListModel.ResultBean> list = new ArrayList<>();
            databaseHelper = DatabaseHelper.getInstance(getActivity(), null, null);
            Cursor cursor = null;
            if (isSpecificRecord)
            {

                cursor = databaseHelper.getSelectedDateRecord(selectedDate);
//                cursor = databaseHelper.getAllRecord();
            }
            else if (!isSpecificRecord)
            {
                cursor = databaseHelper.getAllRecord();
            }

            if (cursor.getCount() == 0) {
                Toast.makeText(requireActivity(), getResources().getString(R.string.no_record), Toast.LENGTH_SHORT).show();
                clearAllFragments();
            } else {
                while (cursor.moveToNext()) {

                    if (cursor.moveToFirst()) {
                        do {

                            String id = cursor.getString(cursor.getColumnIndex(ID));
                            String date = cursor.getString(cursor.getColumnIndex(DATE));
                            Log.d("Stored_Date:", date);
                            String time = cursor.getString(cursor.getColumnIndex(TIME));
                            String meal = cursor.getString(cursor.getColumnIndex(MEAL));
                            String food = cursor.getString(cursor.getColumnIndex(FOOD));
                            String location = cursor.getString(cursor.getColumnIndex(LOCATION));
                            FoodListModel.ResultBean resultBean = new FoodListModel.ResultBean(id, date, time, meal, food, location);
                            list.add(resultBean);
                            setFavListAdapter(list);
                        } while (cursor.moveToNext());
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void setFavListAdapter(List<FoodListModel.ResultBean> data) {

        try {

            rvFoodList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            adapter = new FoodsListAdapter(getActivity(), this, data);
            rvFoodList.setAdapter(adapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
            itemTouchHelper.attachToRecyclerView(rvFoodList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void callBack(Object _model, int position) {
        FoodListModel.ResultBean model=(FoodListModel.ResultBean) _model;
        Bundle bundle=new Bundle();
        bundle.putBoolean(Constants.IS_UPDATE,true);
        bundle.putString(ID,model.getId());
        bundle.putString(TIME,model.getTime());
        bundle.putString(DATE,model.getDate());
        bundle.putString(MEAL,model.getMeal());
        bundle.putString(FOOD,model.getFood());
        bundle.putString(LOCATION,model.getLocation());
        getParentActivity().switchFragmentWithBackStack(AddFoodRecordFragment.getInstance(),bundle);
    }
}
