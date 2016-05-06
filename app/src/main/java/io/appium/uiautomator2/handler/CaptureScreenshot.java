package io.appium.uiautomator2.handler;

import android.os.Environment;

import org.json.JSONException;

import java.io.File;

import io.appium.uiautomator2.http.AppiumResponse;
import io.appium.uiautomator2.http.IHttpRequest;
import io.appium.uiautomator2.server.WDStatus;
import io.appium.uiautomator2.utils.Device;
import io.appium.uiautomator2.utils.Logger;

public class CaptureScreenshot extends SafeRequestHandler {

    public CaptureScreenshot(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) throws JSONException {
        Logger.info("Capture screenshot command");
        boolean isActionPerformed;
        String actionMsg = "";
        final File screenshot = new File(Environment.getExternalStorageDirectory() + File.separator + "screenshot.png");
        try {
            screenshot.getParentFile().mkdirs();
            if (screenshot.exists()) {
                screenshot.delete();
            }
            isActionPerformed = Device.getUiDevice().takeScreenshot(screenshot);
            if (isActionPerformed) {
                actionMsg = "Captured Screen Successfully";
                Logger.info("ScreenShot captured at location: " + screenshot, actionMsg);
            } else {
                actionMsg = "Failed to capture Screen Shot";
            }

        } catch (final Exception e) {
            Logger.error("Unable to Capture Screen Shot", e);
            return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, e);
        }
        return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, actionMsg);
    }
}
