package dritanx.niarp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

/**
 * Responsible for prompting the user for desired permissions. Passes down the user's responses
 * to view/component that issued the permission request.
 *
 * Created by dritan-x on 1/31/17.
 */
public class PermissionManager {

    private static final String REQUEST_PERMISSIONS = "request_permissions";

    /**** Static Scope ****/
    private static PermissionManager instance;

    /**
     * Initialize this Permission Manager during onCreate() of Application class.
     *
     * @param context
     */
    public static void init(Context context){
        instance = new PermissionManager(context);
    }
    public static PermissionManager instance() {
        return instance;
    }

    /**** Instance Scope ****/

    private final Context mContext;
    private PermissionResponse mPermissionResponseCallback;

    private PermissionManager(Context context) {
        this.mContext = context;
    }

    /**
     * Helper for determining if app has a certain permission.
     * @param manifestPermission the permission text as defined in Manifest.permission.*
     * @return whether or not user has previously granted said permission.
     */
    public boolean hasPermission(String manifestPermission){
        int permissionCheck = ContextCompat.checkSelfPermission(mContext, manifestPermission);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * To be called from any view/component that wants to prompt the user for permissions.
     *
     * @param permissions array of permissions to prompt to user
     * @param callback callback for when user is done granting/denying permissions
     */
    public void requestPermission(String [] permissions, PermissionResponse callback) {
        Intent intent = new Intent(mContext, PermissionActivityHandler.class);
        intent.putExtra(REQUEST_PERMISSIONS, permissions);
        mContext.startActivity(intent);
        this.mPermissionResponseCallback = callback;
    }

    /**
     * Callback from activity responsible for executing permission prompts and then passes user's
     * results here to update interested parties.
     * @param permissions all the dangerous permissions prompted to user
     * @param grantResults all the responses the user gave for each permission
     */
    public void onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
        if (this.mPermissionResponseCallback!=null){
            this.mPermissionResponseCallback.toggle(permissions, grantResults);
        }
    }

}
