package dritanx.niarp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Translucent activity handles the execution of permission prompt as well as response.
 * Non-invasive method for requesting permissions. No need to manually override
 * onRequestPermissionsResult() and figure out which permissions were granted and which weren't since
 * this activity will automatically notify any components that requested to prompt for any permissions.
 *
 * Created by dritan-x on 1/31/17.
 */

public class PermissionActivityHandler extends AppCompatActivity {
    private static final String REQUEST_PERMISSIONS = "request_permissions";
    private static final int PERMISSION_REQUEST_ID = 1421;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_activity_handler);
        Intent intent = getIntent();
        String [] permissions = intent.getStringArrayExtra(REQUEST_PERMISSIONS);
        if (permissions.length>0){
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_ID);
        } else {
            finish(); // we have no work to do if not prompt the user for permissions
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_ID){
            PermissionManager.instance().onRequestPermissionsResult(permissions, grantResults);
            finish(); // end activity
        }
    }

}
