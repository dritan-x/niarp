package dritanx.niarp;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class callback interface.
 * Upon an event happening, toggle notification event based on the event that happened.
 * Toggling of notifications is executed deep in the response logic.
 * Abstract methods are implemented on interested views/components that need to use permissions.
 *
 * Created by dritan-x on 1/31/17.
 */

public abstract class PermissionResponse {

    public void toggle(@NonNull String[] permissions, @NonNull int[] grantResults) {
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();

        for (int i=0; i<grantResults.length; i++){
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                granted.add(permissions[i]);
            } else {
                denied.add(permissions[i]);
            }
        }

        if (granted.size()>0){
            onGrantedPermissions(granted.toArray(new String[0]));
        }

        if (denied.size()>0){
            onDeniedPermissions(denied.toArray(new String[0]));
        }
    }

    /**
     * Callback issued whenever any permissions were granted.
     * @param permissions array of granted permissions
     */
    public abstract void onGrantedPermissions(@NonNull String[] permissions);

    /**
     * Callback issued whenever any permissions were denied.
     * @param permissions array of denied permissions.
     */
    public abstract void onDeniedPermissions(@NonNull String[] permissions);
}
