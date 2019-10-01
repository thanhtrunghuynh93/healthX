package com.example.healthx.DropBox;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;
import com.example.healthx.activities.MainActivity;
import com.example.healthx.app.AppController;
import com.example.healthx.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UploadTask extends AsyncTask {

    private DbxClientV2 dbxClient;
    private File file;
    private Context context;

    public UploadTask(DbxClientV2 dbxClient, File file, Context context) {
        this.dbxClient = dbxClient;
        this.file = file;
        this.context = context;
    }

        @Override
        protected Object doInBackground(Object[] params) {
        try {

            // Upload to Dropbox
            InputStream inputStream = new FileInputStream(file);
            dbxClient.files().uploadBuilder("/" + file.getName()) //Path in the user's Dropbox to save the file.
                    .withMode(WriteMode.OVERWRITE) //always overwrite existing file
                    .uploadAndFinish(inputStream);
            Log.d("Upload Status", "Success");
        } catch (DbxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Toast.makeText(context, "uploaded successfully", Toast.LENGTH_SHORT).show();
        Utils.startMainActivity(MainActivity.class,AppController.getCurrentActivity());
    }
}
