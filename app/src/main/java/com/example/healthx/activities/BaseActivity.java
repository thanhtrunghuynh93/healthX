package com.example.healthx.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthx.R;
import com.example.healthx.app.AppController;
import com.example.healthx.constants.Constants;
import com.example.healthx.fragments.HomeFragment;
import com.example.healthx.utils.AlertUtils;

import java.util.Stack;

import static com.example.healthx.constants.Constants.ALERT;


public abstract class BaseActivity extends AppCompatActivity  {
    //region Variables
    protected Stack<Fragment> fragmentsStack;
    protected CoordinatorLayout parentLayout;
    //endregion

    protected void initViews() {
        fragmentsStack = new Stack<>();
        parentLayout = findViewById(R.id.parentLayout);
    }


    protected void setValues() {}

    protected void setListeners() {}


    protected void setUpHomeFragment(Fragment fragment) {
        fragmentsStack.push(fragment);
        switchFragment(fragment, Constants.FragmentAnimation.SLIDE_LEFT);
    }

    protected void setUpHomeFragment(Fragment fragment, Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentsStack.push(fragment);
        switchFragment(fragment, Constants.FragmentAnimation.NONE);
    }


    public void switchFragment(Fragment fragment, Constants.FragmentAnimation fragmentAnimation) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        try {

            if (fragmentAnimation == Constants.FragmentAnimation.SLIDE_RIGHT) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_right);
            } else if (fragmentAnimation == Constants.FragmentAnimation.SLIDE_LEFT) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left);
            }

            fragmentTransaction.replace(R.id.parentLayout, fragment).commit();
        } catch (IllegalStateException e) {
            fragmentTransaction.replace(R.id.parentLayout, fragment).commitAllowingStateLoss();
        }
    }

    /**
     * Clear all fragments and make the home fragment visible
     */
    public void clearAllFragments() {

        fragmentsStack.clear();
        setUpHomeFragment(new HomeFragment());
    }

    public void clearStack() {

        fragmentsStack.clear();
    }


    public void pushFragment() {

        fragmentsStack.push(new HomeFragment());

    }


    public void switchSelectedMenuFragment(Fragment fragment) {
        fragmentsStack.clear();
        fragmentsStack.push(new HomeFragment());
        fragmentsStack.push(fragment);
        switchFragment(fragment, Constants.FragmentAnimation.NONE);


    }

    public void switchFragmentWithBackStack(Fragment fragment) {
        fragmentsStack.push(fragment);
        switchFragment(fragment, Constants.FragmentAnimation.SLIDE_LEFT);

    }

    public void switchFragmentWithBackStack(Fragment fragment, Bundle bundle) {
        fragmentsStack.push(fragment);
        switchFragment(fragment, Constants.FragmentAnimation.SLIDE_LEFT);
        fragment.setArguments(bundle);
    }

    /**
     * if the size is 5 then get the index of second last fragment i.e, 5-2 = 3 (Array indices are 0-4)
     */
    public void popFragment() {
        int fragmentIndex = fragmentsStack.size() - 2;
        switchFragment(fragmentsStack.elementAt(fragmentIndex), Constants.FragmentAnimation.SLIDE_RIGHT);
        fragmentsStack.pop();

    }

    protected void onResume() {
        super.onResume();
        AppController.setCurrentActivity(this);
    }

    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    private void clearReferences() {
        Activity currentActivity = AppController.getCurrentActivity();

        if (this.equals(currentActivity)) {
            AppController.setCurrentActivity(null);
        }
    }

    /**
     * Check if there is any fragment on stack to move back to that fragment
     */

    @Override
    public void onBackPressed() {

        if (fragmentsStack != null && fragmentsStack.size() > 1) {
            popFragment();
        }
        else {
            finish();
        }
    }


    public Fragment getCurrentFragment() {
        return fragmentsStack.lastElement();
    }


    protected <T> void startNewActivity(Class<T> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    protected <T> void startNewActivity(Class<T> activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void showExitAlert(String message) {

        AlertUtils.showAlert(this, ALERT, message, (dialog, which) -> {

            ActivityCompat.finishAffinity(BaseActivity.this);
            dialog.dismiss();
            finish();
        });

    }


}


