package com.example.healthx.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;

import com.example.healthx.R;
import com.example.healthx.constants.Constants;
import com.example.healthx.utils.DataBaseToCVS;
import com.example.healthx.utils.Permissions;

import java.io.File;

import static com.example.healthx.constants.Constants.GMAIL;
import static com.example.healthx.constants.Constants.GOOGLE_DRIVE;

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    LinearLayout llGoogleDrive, llDropBox, llEmail;
    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
        setValues();
        setListeners();
    }
    @Override
    protected void initViews() {
        super.initViews();
        llGoogleDrive = findViewById(R.id.llGoogleDrive);
        llDropBox = findViewById(R.id.llDropBox);
        llEmail = findViewById(R.id.llEmail);
        ivBack = findViewById(R.id.ivBack);
    }

    @Override
    protected void setValues() {
        super.setValues();
        /**
         * checking storage permission to store file in mobile.
         */
        if (!Permissions.checkPermissions()) {
            Permissions.requestPermission(SettingActivity.this);
        }
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        llGoogleDrive.setOnClickListener(this);
        llDropBox.setOnClickListener(this);
        llEmail.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llGoogleDrive: {

                if (DataBaseToCVS.exportDB()) {
                    shareGoogleDrive();
                } else {

                    Toast.makeText(SettingActivity.this, getResources().getString(R.string.could_not_found_file), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.llEmail: {
                if (DataBaseToCVS.exportDB()) {
                    if (isAppInstalled(SettingActivity.this, GMAIL)) {
                        shareGmail();
                    } else {
                        shareFile();
                    }
                } else {

                    Toast.makeText(SettingActivity.this, getResources().getString(R.string.could_not_found_file), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.llDropBox: {
                startNewActivity(DropboxActivity.class);
                break;
            }
            case R.id.ivBack: {
                finish();
                break;
            }
        }
    }


    private void necessaryForFileSharing() {
        /**
         * Used for share file chooser with Strict Mode
         */
        try {

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    /**
     * share file with many options like gmail,whatsapp,blutooth etc.
     */
    private void shareFile() {
        try {


            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setType(Constants.SHARE_TYPE);
            sendIntent.setAction(Intent.ACTION_SEND);

            if (getFileUri() != null) {

                sendIntent.putExtra(Intent.EXTRA_STREAM, getFileUri());
            }
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(sendIntent, Constants.SHARE_TEXT_TITLE));
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    private void shareGoogleDrive() {
        try {
            if (getFileUri() != null) {
                Intent shareIntent = ShareCompat.IntentBuilder.from(SettingActivity.this)
                        .setText(Constants.SHARE_TEXT_TITLE)
                        .setType(Constants.SHARE_TYPE)
                        .setStream(getFileUri())
                        .getIntent()
                        .setPackage(GOOGLE_DRIVE);
                startActivity(shareIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    /**
     * getting CSV file path that is stored in local storage
     * @return
     */
    private Uri getFileUri() {
        File mFile = DataBaseToCVS.mFile;
        if (mFile != null) {
            Uri uri = FileProvider.getUriForFile(SettingActivity.this,
                    Constants.FILE_PROVIDER_AUTH, mFile);
            return uri;
        }
        return null;
    }

    private void shareGmail() {
        try {


            if (getFileUri() != null) {
                Intent shareIntent = ShareCompat.IntentBuilder.from(SettingActivity.this)
                        .setText(Constants.SHARE_TEXT_TITLE)
                        .setType(Constants.SHARE_TYPE)
                        .setStream(getFileUri())
                        .setSubject(Constants.SHARE_SUBJECT)
                        .getIntent()
                        .setPackage(Constants.GMAIL);
                startActivity(shareIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    /**
     * Checking that user have install this app in mobile or not.
     * @param context
     * @param packageName
     * @return
     */
    private boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
