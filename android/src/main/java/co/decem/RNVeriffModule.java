
package co.decem;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import mobi.lab.veriff.data.ColorSchema;
import mobi.lab.veriff.data.Veriff;
import mobi.lab.veriff.data.VeriffConstants;

public class RNVeriffModule extends ReactContextBaseJavaModule {

  private static ReactApplicationContext reactContext;
  private static Veriff.Builder veriffSDK;
  private static String sessionToken;

  private static final int REQUEST_VERIFF = 8000;
  private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
  private static final String E_FAILED_TO_LUNCH_VERIFF = "E_FAILED_TO_LUNCH_VERIFF";

  public static void sendEvent(int statusCode, String description) {
    WritableMap result = Arguments.createMap();
    result.putInt("code", statusCode);
    result.putString("description", description);

    WritableMap response = Arguments.createMap();
    response.putMap("result", result);
    response.putString("sessionToken", sessionToken);

    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit("onSession", response);
  }

  public RNVeriffModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @ReactMethod
  public void initialize(String sessionToken, String sessionUrl, final Promise promise) {
    veriffSDK = new Veriff.Builder(sessionUrl, sessionToken);
    this.sessionToken = sessionToken;

    promise.resolve("Successfully initialized.");
  }

  @ReactMethod
  public void startAuthentication(Promise promise) {

      Activity currentActivity = getCurrentActivity();

      if (currentActivity == null) {
          promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist.");
          return;
      }

      try {
          veriffSDK.launch(currentActivity, REQUEST_VERIFF);
      } catch (Exception e) {
          promise.reject(E_FAILED_TO_LUNCH_VERIFF, e);
      }
  }

  @Override
  public String getName() {
    return "RNVeriff";
  }
}