package com.example.healthx.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.healthx.R;


public class AlertHelper {

    private Dialog progressDialog;
    private static final String ALERT_TAG = "alert";

    public static synchronized AlertHelper showProgress(Activity activity, boolean isDialog) {
        return new AlertHelper(activity, isDialog);
    }
    public static synchronized AlertHelper showProgress(Context activity, boolean isDialog) {
        return new AlertHelper(activity, isDialog);
    }
    public AlertHelper(Activity activity, boolean isDialog) {
        try {
            setUpProgress(activity, isDialog);
        } catch (Exception ex) {
            Log.d(ALERT_TAG, "AlertHelper: " + ex.getMessage());
        }
    }
    public AlertHelper(Context activity, boolean isDialog) {
        try {
            setUpProgress(activity, isDialog);
        } catch (Exception ex) {
            Log.d(ALERT_TAG, "AlertHelper: " + ex.getMessage());
        }
    }
    private void setUpProgress(Context activity, boolean isDialog) {

        try {

            if (isDialog) {

                progressDialog = new Dialog(activity);
                progressDialog.setCancelable(false);
                progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                progressDialog.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void setUpProgress(Activity activity, boolean isDialog) {

        try {

            if (isDialog) {

                progressDialog = new Dialog(activity);
                progressDialog.setCancelable(false);
                progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                progressDialog.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public boolean isShowingProgress() {
        return progressDialog != null && progressDialog.isShowing();
    }


    public void dismissProgress() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception ex) {
            Log.d(ALERT_TAG, "dismissProgress: " + ex.getMessage());

        }
    }


    public static void showAlert(Context context, String title, String message) {


        if (context == null) {
            Log.d(ALERT_TAG, "Null context");
            return;
        }

        AlertDialog dialog = new AlertDialog.Builder(context, R.style.DialogNormal)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false).create();

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showAlert(Context context, String title, String message, DialogInterface.OnClickListener onDoneClick) {

        if (context == null) {
            Log.d(ALERT_TAG, "Null context");
            return;
        }
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.DialogNormal)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(android.R.string.yes, onDoneClick).setCancelable(false).create();

        try {
            dialog.show();
        } catch (IllegalStateException | IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    public static void showAlert(Context context, String title, String message, DialogInterface.OnClickListener
            onDoneClick, final boolean requireCancel) {

        if (context == null) {
            Log.d(ALERT_TAG, "Null context");
            return;
        }
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.DialogNormal)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (requireCancel)
                            dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(android.R.string.yes, onDoneClick).setCancelable(false).create();

        try {
            dialog.show();
        } catch (IllegalStateException | IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

}
