package com.example.healthx.utils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toolbar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static String getCurrentDateAndTime(){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy");
        String formattedDate = simpleDateFormat.format(c);
        return formattedDate;
    }
    public static String getSecondFormateDate()
    {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);
        String dayOfTheWeek = (String) DateFormat.format("EEE", startDate); // Thursday
        String day          = (String) DateFormat.format("dd",   startDate); // 20
        String monthString  = (String) DateFormat.format("MMM",  startDate); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   startDate); // 06
        String year         = (String) DateFormat.format("yyyy", startDate); // 2013
        return  dayOfTheWeek + ", " + monthString + " " + day+", "+year;
    }


    public static void hideStatusBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static <T> void startNewActivity(Class<T> activity, Context context) {
        Intent intent = new Intent(context, activity);

        context.startActivity(intent);
    }

    //just for clearing back stack to exit app
    public static <T> void startMainActivity(Class<T> activity, Context context) {
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    public static <T> void startNewActivity(Class<T> activity, Bundle bundle, Context context) {
        Intent intent = new Intent(context, activity);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }




}

