package com.example.healthx.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.example.healthx.R;


/**
 * Created by Aleem on 10/22/2017.
 */

public class AlertUtils {



    public static void showAlert(Context context, String title, String message) {


        if (context == null) {
            Log.d("JayEat", "Null context");
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
            Log.d("JayEat", "Null context");
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
            Log.d("JayEat", "Null context");
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
