package com.example.healthx.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.healthx.constants.Constants;
import com.example.healthx.manager.PrefManager;

import java.io.File;
import java.io.IOException;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "healthx.db";
    public static final String VERSION = "db_version";
    public static final String TABLE_ADD_FOODS = "eaten_foods";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteDatabase database;
    private static DatabaseHelper instance;
    String TAG = "TAG";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DatabaseHelper getInstance(Context context, String name,
                                             SQLiteDatabase.CursorFactory factory) {
        if (instance == null) {
            instance = new DatabaseHelper(context, DATABASE_NAME, factory, DATABASE_VERSION);
        }
        return instance;
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        upgradeDataBase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        upgradeDataBase(db);
    }

    /**
     * Database Queries
     *
     * @param db
     */
    private void upgradeDataBase(SQLiteDatabase db) {


        /**
         * Database Table (Query)for Save Offline Menu Info.
         */
        db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_ADD_FOODS + " (" +
                Constants.Database.Food.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                Constants.Database.Food.DATE + " TEXT, " +
                Constants.Database.Food.TIME + " TEXT, " +
                Constants.Database.Food.MEAL + " TEXT, " +
                Constants.Database.Food.FOOD + " TEXT, " +
                Constants.Database.Food.LOCATION + " TEXT);"
        );


    }

    /**
     * Creates a empty database on the system
     */
    public void createDataBase(Context context) throws IOException {
        boolean dbExist = doesDatabaseExist(context, DATABASE_NAME);
        if (!dbExist) {
            // By calling this method and empty database will be created into
            // the default system path
            PrefManager.getInstance().writeInteger(context, VERSION, DATABASE_VERSION);
            try {
                database = this.getReadableDatabase();
            } catch (SQLiteException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public synchronized void close() {
        if (database != null)
            database.close();
        super.close();
    }

    /**
     * Save eaten food into database
     *
     * @param date
     * @param food
     * @param time
     **/
    public boolean insertFoodRecord(String date, String time, String mealType, String food, String location) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.Database.Food.DATE, date);
        values.put(Constants.Database.Food.TIME, time);
        values.put(Constants.Database.Food.MEAL, mealType);
        values.put(Constants.Database.Food.FOOD, food);
        values.put(Constants.Database.Food.LOCATION, location);
        long result = database.insert(TABLE_ADD_FOODS, null, values);

        Log.d(TAG, "insertFoodRecord" + result);

        if (result == -1) {
            Log.d(TAG, "Not Created");
            return false;
        } else {
            Log.d(TAG, "Created");
            return true;
        }
    }

    /**
     * Save eaten food into database
     *
     * @param date
     * @param food
     * @param time
     **/
    public boolean updateFoodRecord(String id, String date, String time, String mealType, String food, String location) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.Database.Food.DATE, date);
        values.put(Constants.Database.Food.TIME, time);
        values.put(Constants.Database.Food.MEAL, mealType);
        values.put(Constants.Database.Food.FOOD, food);
        values.put(Constants.Database.Food.LOCATION, location);
        long result = database.update(TABLE_ADD_FOODS, values, "ID=" + id, null);
        Log.d(TAG, "updateFoodRecord" + result);
        if (result == -1) {
            Log.d(TAG, "Not Updated");
            return false;
        } else {
            Log.d(TAG, "Updated");
            return true;
        }
    }

    /**
     * get all store record. this method is used for listing.
     * @return
     */
    public Cursor getAllRecord() {
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor res = db.rawQuery("select * from " + TABLE_ADD_FOODS + " where ID = " + Constants.Database.Food.ID, null);
        Cursor res = db.rawQuery("select * from " + TABLE_ADD_FOODS, null);
        return res;
    }

    /**
     * get all store record. this method is used for listing.
     * @return
     */
    public Cursor getSelectedDateRecord(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_ADD_FOODS + " where date =" + "\"" + date + "\"", null);
        //    Cursor res = database.rawQuery("select * from eaten_foods  where date =\"12-9-2019\"", null);
        return res;
    }

    /**
     * this method is used to deleted a selected record.
     * @param id
     */
    public void softDeleteRecord(String id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_ADD_FOODS, "ID=?", new String[]{id});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void deleteAllRecord() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ADD_FOODS, null, null);

    }


}
