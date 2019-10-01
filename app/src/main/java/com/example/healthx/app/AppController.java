package com.example.healthx.app;


import android.app.Activity;
import android.app.Application;
import android.content.Context;



public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private static Context context;
    private static AppController mInstance;
    private static Activity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AppController.context = getApplicationContext();


    }


    public static synchronized AppController getInstance() {
        return mInstance;
    }
    public static Context getAppContext() {
        return AppController.context;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity activity) {
        currentActivity = activity;
    }


}