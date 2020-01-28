package cordova.plugin.raqmiyat.camerapreview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import android.support.v4.content.ContextCompat;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;


public class CameraPreview extends CordovaPlugin {

    CallbackContext callback;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Context context = cordova.getActivity().getApplicationContext();
        callback = callbackContext;
        if (action.equals("openCamera")) {

            ActivityCompat.requestPermissions(cordova.getActivity(), new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 123);

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(cordova.getActivity(), new String[]{Manifest.permission.CAMERA}, 123);
            }else if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(cordova.getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            }else if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(cordova.getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            } else{
                Intent intent = new Intent(context, CameraActivity.class);
                cordova.setActivityResultCallback (this);
                cordova.getActivity().startActivityForResult(intent, 1);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                String result = data.getStringExtra("ActivityResult");
                if (resultCode == cordova.getActivity().RESULT_CANCELED) {
                    Toast.makeText(cordova.getActivity().getApplicationContext(), result, Toast.LENGTH_LONG).show();
                } else if (resultCode == cordova.getActivity().RESULT_OK) {
                    PluginResult resultA = new PluginResult(PluginResult.Status.OK, result);
                    callback.sendPluginResult(resultA);
                }
                break;
            }
        }
    }
}
