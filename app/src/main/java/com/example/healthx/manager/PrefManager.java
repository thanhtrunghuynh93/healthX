package com.example.healthx.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.healthx.app.AppController;


/**
 * Created by Ali on 10/25/2017.
 */

public class PrefManager {


    private static PrefManager mInstance;
    protected SharedPreferences.Editor editor;
    protected SharedPreferences sharedPreferences;
    public static final String DROPBOX = "dropbox";

    PrefManager() {
        sharedPreferences = AppController.getAppContext().getSharedPreferences(SharedPreferencesSettings.NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public static synchronized PrefManager getInstance() {
        if (mInstance == null) {
            mInstance = new PrefManager();
        }
        return mInstance;
    }
    public  void writeInteger(Context context, String key, int value) {

        editor.putInt(key, value).commit();

    }


    public  int readInteger(Context context, String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }



    /**
     * Get saved string value
     *
     * @param fieldName
     * @return
     */
    public String getString(String fieldName) {
        return sharedPreferences.getString(fieldName, SharedPreferencesSettings.DEFAULT_VALUE_STRING);
    }
    public void setString(String key,String value) {
        editor.putString(key, value).apply();
        editor.commit();
    }


    /**
     * Get saved Long value
     *
     * @param fieldName
     * @return
     */
    public long getLong(String fieldName) {
        return sharedPreferences.getLong(fieldName, SharedPreferencesSettings.DEFAULT_VALUE_LONG);
    }

    public  void setBoolean(String key, boolean value) {

        editor.putBoolean(key, value).commit();

    }
    /**
     * Get saved boolean
     *
     * @param fieldName
     * @return
     */
    public boolean getBoolean(String fieldName) {
        return sharedPreferences.getBoolean(fieldName, SharedPreferencesSettings.DEFAULT_VALUE_BOOLEAN);
    }

    /**
     * Clear all saved preferences
     */
    public void clearAll() {

        editor.clear();
        editor.commit();

    }


    /*
     * SharedPreferencesSettings
     */
    public static class
    SharedPreferencesSettings {
        public final static int MODE = 0;
        public final static String NAME = "dawn";
        public final static int CUSTOM_VALUE_INT = -1;
        public final static int DEFAULT_VALUE_INT = 0;
        public final static long DEFAULT_VALUE_LONG = 0;
        public final static String DEFAULT_VALUE_STRING = "";
        public final static boolean DEFAULT_VALUE_BOOLEAN = false;
    }
}
