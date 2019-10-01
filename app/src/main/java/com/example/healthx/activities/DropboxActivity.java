package com.example.healthx.activities;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;
import com.example.healthx.DropBox.DropboxClient;
import com.example.healthx.DropBox.UploadTask;
import com.example.healthx.DropBox.UserAccountTask;
import com.example.healthx.R;
import com.example.healthx.constants.Constants;
import com.example.healthx.manager.PrefManager;
import com.example.healthx.utils.DataBaseToCVS;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.io.File;
public class DropboxActivity extends BaseActivity  {

    private String ACCESS_TOKEN;
    ImageView ivProfile,ivBack;
    EditText etEmail, etUserName;
    LinearLayout llProfile,llUpload;
    ProgressBar pbLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox);
        initViews();
        setValues();
        setListeners();
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivProfile = findViewById(R.id.ivProfile);
        etEmail = findViewById(R.id.etEmail);
        etUserName = findViewById(R.id.etUserName);
        llProfile = findViewById(R.id.llProfile);
        llUpload = findViewById(R.id.llUpload);
        pbLoader = findViewById(R.id.pbLoader);
        ivBack = findViewById(R.id.ivBack);
        llProfile.setVisibility(View.GONE);
        etEmail.setEnabled(false);
        etUserName.setEnabled(false);
    }

    @Override
    protected void setValues() {
        super.setValues();
        /**
         * Checking weather user already has login using this app to dropbox or not. for that we have to check token. so we are checking token.
         * if token not exist in shared preference then app will goes to dropbox login and authenticate user.
         */
        if (!tokenExists()) {
            Auth.startOAuth2Authentication(getApplicationContext(), getString(R.string.DROPBOX_APP_KEY));
        }
        else {

            ACCESS_TOKEN = retrieveAccessToken();
            getUserAccount();

        }

    }

    @Override
    protected void setListeners() {
        super.setListeners();
        llUpload.setOnClickListener(v -> {
            try {
                /**
                 * this method convert database data to CSV file. if that convert file with data then it will return true, if its true then it will goes to store file in dropbox.
                 */
                if (DataBaseToCVS.exportDB()) {
                    uploadToDropBox();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ivBack.setOnClickListener(v -> finish());
    }

    /**
     * uploading csv file to dropbox
     */
    private void uploadToDropBox() {
        pbLoader.setVisibility(View.VISIBLE);

        File file = DataBaseToCVS.mFile;
        if (file != null) {
            //Initialize UploadTask
            new UploadTask(DropboxClient.getClient(ACCESS_TOKEN), file, getApplicationContext()).execute();
        }
        else {

            pbLoader.setVisibility(View.GONE);
            Toast.makeText(this, getResources().getString(R.string.could_not_found_file), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAccessToken();
    }

    /**
     * //getting access token from dropbox and storing in sharedPrefrences.
     */

    public void getAccessToken() {
        String accessToken = Auth.getOAuth2Token(); //generate Access Token
        if (accessToken != null) {
            PrefManager.getInstance().setString(PrefManager.DROPBOX, accessToken);

            ACCESS_TOKEN = retrieveAccessToken();
            getUserAccount();
        }
    }

    /**
     * getting access token from shared preferences.
     * @return
     */
    private String retrieveAccessToken() {
        String accessToken = PrefManager.getInstance().getString(PrefManager.DROPBOX);
        if (accessToken == null) {
            Log.d("AccessToken Status", "No token found");
            return null;
        } else {
            //accessToken already exists
            Log.d("AccessToken Status", "Token exists");
            return accessToken;
        }
    }
    /**
     * checking token exist or not.
     * @return
     */
    private boolean tokenExists() {
        String accessToken = PrefManager.getInstance().getString(PrefManager.DROPBOX);
        if (!accessToken.equals(Constants.EMPTY) && accessToken!=null)
        {
            return true;
        }
        return false;
    }



    protected void getUserAccount() {
        if (ACCESS_TOKEN == null) return;
        new UserAccountTask(DropboxClient.getClient(ACCESS_TOKEN), new UserAccountTask.TaskDelegate() {
            @Override
            public void onAccountReceived(FullAccount account) {
                //Print account's info
                Log.d("User", account.getEmail());
                Log.d("User", account.getName().getDisplayName());
                Log.d("User", account.getAccountType().name());
                updateUI(account);
            }

            @Override
            public void onError(Exception error) {
                pbLoader.setVisibility(View.GONE);
                Log.d("User", "Error receiving account details.");
            }
        }).execute();
    }

    private void updateUI(FullAccount account) {


        pbLoader.setVisibility(View.GONE);
        llProfile.setVisibility(View.VISIBLE);
        llProfile.setFocusable(true);
        etEmail.setText(account.getName().getDisplayName());
        etUserName.setText(account.getEmail());
        Picasso.with(this)
                .load(account.getProfilePhotoUrl())
                .resize(200, 200)
                .into(ivProfile);
    }


}
