package com.example.healthx.utils;

import android.database.Cursor;
import android.os.Environment;
import android.util.Log;
import com.example.healthx.app.AppController;
import com.example.healthx.constants.Constants;
import com.example.healthx.database.DatabaseHelper;
import java.io.File;
import java.io.FileWriter;
import au.com.bytecode.opencsv.CSVWriter;
import static com.example.healthx.constants.Constants.Database.Food.DATE;
import static com.example.healthx.constants.Constants.Database.Food.FOOD;
import static com.example.healthx.constants.Constants.Database.Food.ID;
import static com.example.healthx.constants.Constants.Database.Food.LOCATION;
import static com.example.healthx.constants.Constants.Database.Food.MEAL;
import static com.example.healthx.constants.Constants.Database.Food.TIME;

public class DataBaseToCVS {

    public static DataBaseToCVS mInstance;
    public static File mFile;

    public static synchronized DataBaseToCVS getInstance() {
        if (mInstance == null) {
            mInstance = new DataBaseToCVS();
        }
        return mInstance;
    }

    /**
     * This is used to create CSV file from database. and save that file in local storage.
     * @return
     */
    public static boolean exportDB() {

        DatabaseHelper databaseHelper;


        File storageDir = AppController.getAppContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        mFile = new File(storageDir, Constants.CSV_FILE);
        try {
            CSVWriter csvWrite = new CSVWriter(new FileWriter(mFile));
            databaseHelper = DatabaseHelper.getInstance(AppController.getCurrentActivity(), null, null);
            Cursor curCSV = databaseHelper.getAllRecord();
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {

                if (curCSV.moveToFirst()) {
                    do {

                        String id = curCSV.getString(curCSV.getColumnIndex(ID));
                        String date = curCSV.getString(curCSV.getColumnIndex(DATE));
                        String time = curCSV.getString(curCSV.getColumnIndex(TIME));
                        String meal = curCSV.getString(curCSV.getColumnIndex(MEAL));
                        String food = curCSV.getString(curCSV.getColumnIndex(FOOD));
                        String location = curCSV.getString(curCSV.getColumnIndex(LOCATION));
                        String newDate = date.replace("-", "/");
                        //Which column you want to export

                        String arrStr[] = {id, newDate, time, meal, food, location};
                        csvWrite.writeNext(arrStr);
                    } while (curCSV.moveToNext());
                }
            }

            csvWrite.close();
            curCSV.close();
            return true;
        } catch (Exception sqlEx) {

            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
        return false;
    }
}
