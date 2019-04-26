package co.decem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import co.decem.RNVeriffModule;

import mobi.lab.veriff.data.VeriffConstants;

public class VeriffStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras.containsKey(VeriffConstants.INTENT_EXTRA_STATUS)) {
            String sessionToken = extras.getString(VeriffConstants.INTENT_EXTRA_SESSION_URL);
            int status = extras.getInt(VeriffConstants.INTENT_EXTRA_STATUS);
            if (status == VeriffConstants.STATUS_UNABLE_TO_ACCESS_CAMERA) {
                RNVeriffModule.sendEvent(0, "camera or permission missing");
            } else if (status == VeriffConstants.STATUS_USER_FINISHED) {
                RNVeriffModule.sendEvent(1, "finished");
            } else if (status == VeriffConstants.STATUS_USER_CANCELED) {
                RNVeriffModule.sendEvent(1, "canceled");
            } else if (status == VeriffConstants.STATUS_SUBMITTED) {
                RNVeriffModule.sendEvent(2, "submitted");
            } else if (status == VeriffConstants.STATUS_ERROR_SESSION) {
                RNVeriffModule.sendEvent(3, "session expired");
            } else if (status == VeriffConstants.STATUS_ERROR_NETWORK) {
                RNVeriffModule.sendEvent(4, "network error");
            } else if (status == VeriffConstants.STATUS_ERROR_SETUP) {
                RNVeriffModule.sendEvent(7, "setup error");
            } else if (status == VeriffConstants.STATUS_ERROR_NO_IDENTIFICATION_METHODS_AVAILABLE) {
                RNVeriffModule.sendEvent(5, "no ident methods available");
            } else if (status == VeriffConstants.STATUS_DONE) {
                RNVeriffModule.sendEvent(6, "done");
            } else if (status == VeriffConstants.STATUS_ERROR_UNKNOWN) {
                RNVeriffModule.sendEvent(7, "unknown");
            }
        }
    }
}
