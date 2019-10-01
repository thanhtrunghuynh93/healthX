package com.example.healthx.fragments;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import com.example.healthx.R;
import com.example.healthx.activities.MainActivity;
import com.example.healthx.app.AppController;
import com.example.healthx.utils.AlertHelper;
import com.example.healthx.utils.AlertUtils;

public class BaseFragment extends Fragment {

    AlertHelper dialog;
   // private Dialog dialog;
    private MainActivity activity;
    protected LinearLayout llBack;
    protected CoordinatorLayout parentLayout;


    protected void initViews(View view) {
    //llBack = view.findViewById(R.id.llBack);
    parentLayout = view.findViewById(R.id.parentLayout); }

    protected void setListeners() {}

    protected void setValues() {}

    protected void hideDialog() {
        dialog.dismissProgress();
    }

    protected void onBackPressed() { activity.onBackPressed(); }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (MainActivity) context;
    }

    protected void switchFragment(Fragment fragment, Bundle bundle) {
        MainActivity activity = (MainActivity) AppController.getCurrentActivity();

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        if (activity != null) {
            activity.switchFragmentWithBackStack(fragment);
        }
    }

    protected void switchFragment(Fragment fragment) {

        MainActivity activity = (MainActivity) AppController.getCurrentActivity();
        if (activity != null) {
            activity.switchFragmentWithBackStack(fragment);
        }

    }

    protected void setTitle(String title)
    {
//        ((MainActivity) AppController.getCurrentActivity()).setToolBarTitle(title);
    }

    protected void clearAllFragments()
    {
        MainActivity activity = (MainActivity) AppController.getCurrentActivity();
        if (activity != null) {
            activity.clearAllFragments();
        }

    }
    public void showAlert(Context context, String message) {

        AlertUtils.showAlert(context, "Alert!", message, (dialog, which) -> {
            dialog.dismiss();
            activity.finish();
        });

    }

    public MainActivity getParentActivity() {
        return activity;
    }


    protected <T> void startNewActivity(Class<T> activity) {
        Intent intent = new Intent(getActivity(), activity);
        startActivity(intent);
    }

    protected <T> void startNewActivity(Class<T> activity, Bundle bundle) {
        Intent intent = new Intent(getActivity(), activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}
