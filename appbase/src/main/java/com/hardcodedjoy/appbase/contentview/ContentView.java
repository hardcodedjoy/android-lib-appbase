/*

MIT License

Copyright © 2025 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

*/

package com.hardcodedjoy.appbase.contentview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.hardcodedjoy.appbase.LanguageUtil;
import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.SettingsBase;
import com.hardcodedjoy.appbase.StringUtil;
import com.hardcodedjoy.appbase.activity.ActivityResultTask;
import com.hardcodedjoy.appbase.activity.ActivityUtil;
import com.hardcodedjoy.appbase.activity.SingleActivity;
import com.hardcodedjoy.appbase.gui.ThemeUtil;
import com.hardcodedjoy.appbase.popup.Popup;
import com.hardcodedjoy.appbase.popup.PopupAsk;
import com.hardcodedjoy.appbase.popup.PopupError;
import com.hardcodedjoy.appbase.popup.PopupInfo;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

@SuppressLint("ViewConstructor")
public class ContentView extends LinearLayout {

    static private SingleActivity activity;
    static private Display display;
    static private LayoutInflater inflater;
    static private String appLanguageCode;

    static private String logTag = "";
    static private long logMillis;

    protected ContentView cvPrevious;

    private boolean showAlreadyCalled;

    static public void setActivity(SingleActivity activity) {
        ContentView.activity = activity;

        if(android.os.Build.VERSION.SDK_INT >= 30) {
            ContentView.display = activity.getDisplay();
        } else {
            ContentView.display = activity.getWindow().getWindowManager().getDefaultDisplay();
        }

        ContentView.inflater = activity.getLayoutInflater();
        SettingsBase settings = SettingsBase.getInstance();
        String langCode = settings.getAppLanguageCode();
        setAppLanguage(langCode);
    }
    static public SingleActivity getActivity() { return activity; }
    static public LayoutInflater getInflater() { return inflater; }

    public ContentView() { super(activity); }

    public void inflate(int resId) { inflater.inflate(resId, this); }

    public void show() {

        getActivity().runOnUiThread(() -> {
            if(!showAlreadyCalled) { // first time
                cvPrevious = getActivity().getContentView();
                showAlreadyCalled = true;
            }
            getActivity().setContentView(ContentView.this);
        });
    }

    public void onPause() {}
    public void onResume() {}

    public int getDisplayWidth() {
        Point displaySize = new Point();
        display.getSize(displaySize);
        return displaySize.x;
    }

    public int getDisplayHeight() {
        Point displaySize = new Point();
        display.getSize(displaySize);
        return displaySize.y;
    }

    @SuppressWarnings("unused")
    public boolean isPortrait() { return ( getDisplayHeight() > getDisplayWidth()); }

    @SuppressWarnings("unused")
    public boolean isLandscape() { return ( getDisplayHeight() < getDisplayWidth()); }

    // to be overridden by apps that can be started by intents other than Intent.ACTION_MAIN
    // @NonNull intent, @NonNull intent.getAction()
    @SuppressWarnings("unused")
    public void processIntent(Intent intent) {}

    public boolean onBackPressed() {
        // no previous -> backPressed not consumed by this ContentView
        if(cvPrevious == null) { return false; }

        // else -> go to previous if any:
        cvPrevious.show();
        return true; // consumed
    }

    static public String getString(int resId) { return getActivity().getString(resId); }

    @SuppressWarnings("unused")
    static public void startActivityForResult(Class<?> c, ActivityResultTask onResultOK) {
        ActivityUtil.startActivityForResult(getActivity(), c, onResultOK);
    }

    @SuppressWarnings("unused")
    static public void startActivityForResult(Class<?> c, Runnable onResultOK) {
        ActivityUtil.startActivityForResult(getActivity(), c, onResultOK);
    }

    @SuppressWarnings("unused")
    static public void startActivityForResult(Intent intent, ActivityResultTask onResultOK) {
        ActivityUtil.startActivityForResult(getActivity(), intent, onResultOK);
    }

    static public void runOnUiThread(Runnable r) { activity.runOnUiThread(r); }
    static public void showPopUp(Popup popup) { activity.showPopup(popup); }
    static public void removePopUp(Popup popup) { activity.removePopup(popup); }

    @SuppressWarnings("unused")
    static public boolean isShowingPopUp(Popup popup) { return activity.isShowingPopup(popup); }


    static public void showError(String error, Runnable onCancel) {
        runOnUiThread(() -> new PopupError(error) {
            @Override
            public void onCancel() { if(onCancel != null) { onCancel.run(); } }
        }.show());
    }

    @SuppressWarnings("unused")
    static public void showError(String error) { showError(error, null); }
    static public void showError(int errorId, Runnable onCancel) {
        showError(getString(errorId), onCancel);
    }
    static public void showError(final int errorId) { showError(errorId, null); }


    static public void showInfo(String info, Runnable onCancel) {
        runOnUiThread(() -> new PopupInfo(info) {
            @Override
            public void onCancel() { if(onCancel != null) { onCancel.run(); } }
        }.show());
    }

    static public void showInfo(final String info) { showInfo(info, null); }
    static public void showInfo(final int infoId, final Runnable onCancel) {
        showInfo(getString(infoId), onCancel);
    }
    static public void showInfo(final int infoId) { showInfo(infoId, null); }

    @SuppressWarnings("unused")
    static public int getColor(int id) {
        if(android.os.Build.VERSION.SDK_INT >= 23) {
            return activity.getResources().getColor(id, null);
        }
        //noinspection deprecation
        return activity.getResources().getColor(id);
    }

    @SuppressWarnings("unused")
    static public int getThemeColor(int androidRAttrColorId) {
        return ThemeUtil.getColor(activity, androidRAttrColorId);
    }

    static public void setAppLanguage(String languageCode) {
        appLanguageCode = LanguageUtil.getAvailableLanguageCode(languageCode);
        // appLanguageCode is available
        Locale locale = new Locale(appLanguageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        StringUtil.initDf1();
    }

    static public String getAppLanguage() { return appLanguageCode; }

    static public void makeToast(String st) {
        activity.runOnUiThread(() -> Toast.makeText(activity, st, Toast.LENGTH_LONG).show());
    }

    @SuppressWarnings("unused")
    static public void makeToast(int stringId) { makeToast(getString(stringId)); }

    @SuppressWarnings("unused")
    static public void setLogTag(String logTag) { ContentView.logTag = logTag; }

    @SuppressWarnings("unused")
    static public void log(String msg) { Log.d(logTag, msg); }

    @SuppressWarnings("unused")
    static public void logTick() { logMillis = System.currentTimeMillis(); }

    @SuppressWarnings("unused")
    static public void logTock(String st) {
        long ms = System.currentTimeMillis() - logMillis;
        log(st + " " + ms + " ms");
    }

    static public InputStream openInputStream(Uri uri) throws FileNotFoundException {
        return getActivity().getContentResolver().openInputStream(uri);
    }

    static public OutputStream openOutputStream(Uri uri) throws FileNotFoundException {
        return getActivity().getContentResolver().openOutputStream(uri);
    }

    static public OutputStream openOutputStream(Uri uri, String mode) throws FileNotFoundException {
        return getActivity().getContentResolver().openOutputStream(uri, mode);
    }

    static public void showPopupThisIsPremiumFeature(Runnable onMoreDetails) {
        String title = ContentView.getString(R.string.title_premium_feature);
        String message = ContentView.getString(R.string.this_is_a_premium_feature);
        String positive = ContentView.getString(R.string.btn_more_details);
        String negative = ContentView.getString(R.string.btn_ok);
        PopupAsk popupAsk = new PopupAsk(title, message, positive, negative) {
            @Override
            public void onOK() {
                if(onMoreDetails != null) { onMoreDetails.run(); }
            }
        };
        popupAsk.enableDismissByOutsideClick();
        popupAsk.show();
    }

    static public void showPopupFreeVersionLimitation(String message, Runnable onMoreDetails) {
        String title = ContentView.getString(R.string.title_free_version);
        String positive = ContentView.getString(R.string.btn_more_details);
        String negative = ContentView.getString(R.string.btn_ok);
        PopupAsk popupAsk = new PopupAsk(title, message, positive, negative) {
            @Override
            public void onOK() {
                if(onMoreDetails != null) { onMoreDetails.run(); }
            }
        };
        popupAsk.enableDismissByOutsideClick();
        popupAsk.show();
    }

    static public void showPopupFreeVersionLimitation(int messageStringId, Runnable onMoreDetails) {
        String message = getString(messageStringId);
        showPopupFreeVersionLimitation(message, onMoreDetails);
    }

    static public void goFullscreen(boolean fullscreen) {
        goFullscreen(fullscreen, 0xFF000000);
    }

    static public void goFullscreen(boolean fullscreen, int bgColor) {
        Window window = getActivity().getWindow();
        window.getDecorView().setBackgroundColor(bgColor);

        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(window, window.getDecorView());

        if(fullscreen) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            controller.setSystemBarsBehavior(
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            controller.hide(WindowInsetsCompat.Type.statusBars());
            controller.hide(WindowInsetsCompat.Type.navigationBars());
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            controller.setSystemBarsBehavior(
                    WindowInsetsControllerCompat.BEHAVIOR_DEFAULT);
            controller.show(WindowInsetsCompat.Type.statusBars());
            controller.show(WindowInsetsCompat.Type.navigationBars());
        }
    }
}