package com.example.healthx.constants;

import android.os.Bundle;



/**
 * Created by Ali on 12/5/2019.
 */

public class Constants {


    public static final String DATE = "date";
    public static final String IS_UPDATE = "is_update";
    public static final String CSV_FILE = "healthx.csv";
    public static final String SHARE_TEXT_TITLE = "Share HealthX file.";
    public static final String SHARE_TYPE = "text/*";
    public static final String GOOGLE_DRIVE = "com.google.android.apps.docs";
    public static final String GMAIL = "com.google.android.gm";
    public static final String SHARE_SUBJECT ="HealthX Food record" ;
    public final static Bundle NO_BUNDLE = null;
    public static final String FILE_PROVIDER_AUTH = "com.example.healthx.fileprovider";
    public static final String EMPTY ="" ;
    public static final String ALERT = "Alert!";
    public static final String DATE_PICKER = "Datepickerdialog";
    public static final String UPDATE_RECORD ="Update Record" ;
    public static final String BREAKFAST = "Breakfast";
    public static final String DINNER ="Dinner";
    public static final String DRINK ="Drink";
    public static final String LUNCH ="Lunch";
    public static final String COMMA_SPACE =", ";
    public static final String SPACE =" ";
    public static final String DASH ="-";
    public static final String SELECTED_DAY ="EEE" ;
    public static final String _DATE ="dd" ;
    public static final String MONTH_NAME = "MMM";
    public static final String MONTH_NUMBER = "MM";
    public static final String YEAR = "yyyy";


    public enum FragmentAnimation {
        SLIDE_RIGHT, SLIDE_LEFT, NONE
    }
    public static class Database {

        // TABLES PARAMS
        public static class Food {
            public static final String ID = "ID"; //Primary Key
            public static final String DATE = "date";
            public static final String TIME = "time";
            public static final String MEAL = "meal";
            public static final String FOOD = "food";
            public static final String LOCATION = "location";

        }


    }
}
