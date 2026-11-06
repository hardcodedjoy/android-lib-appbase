/*

MIT License

Copyright © 2026 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

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

package com.hardcodedjoy.appbase.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.hardcodedjoy.appbase.FileUtil;
import com.hardcodedjoy.appbase.IntentUtil;
import com.hardcodedjoy.appbase.SoftKeyboardUtil;
import com.hardcodedjoy.appbase.contentview.ContentView;
import com.hardcodedjoy.appbase.SettingsBase;
import com.hardcodedjoy.appbase.gui.ThemeUtil;
import com.hardcodedjoy.appbase.popup.Popup;

import java.util.ArrayList;
import java.util.List;

public class SingleActivity extends Activity {

    // class of cv to be displayed on app start
    static private Class<?> cvInitialClass = null;
    static private ContentView cvCurrent = null;
    static private final List<Popup> popups = new ArrayList<>();

    private OnBackInvokedCallback onBackInvokedCallback;

    public enum WindowDisplayMode {
        NORMAL_BARS_THEMED_FG,
        NORMAL_BARS_THEMED_BG,
        NORMAL_BARS_BLACK,
        NORMAL_BARS_BLACK_HIDE_NOTIFICATIONS,
        IMMERSIVE_FULLSCREEN
    }

    private WindowDisplayMode currentWindowDisplayMode =
            WindowDisplayMode.NORMAL_BARS_THEMED_FG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FileUtil.setActivity(this);
        IntentUtil.setActivity(this);
        PermissionUtil.setActivity(this);
        SettingsBase.setActivity(this);

        // we use our own title bar in "layout_main"
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        SettingsBase settings = SettingsBase.getInstance();

        if(settings.getLightTheme() == null) {
            settings.setLightTheme(ThemeUtil.getThemes(this, true)[0]);
        }
        if(settings.getDarkTheme() == null) {
            settings.setDarkTheme(ThemeUtil.getThemes(this, false)[0]);
        }

        // settings.getTheme needs activity to get the day / night mode of android settings
        ThemeUtil.setTheme(this, settings.getTheme(this));

        if(android.os.Build.VERSION.SDK_INT >= 33) {
            onBackInvokedCallback = SingleActivity.this::handleOnBackPressed;
            getOnBackInvokedDispatcher().registerOnBackInvokedCallback(
                    OnBackInvokedDispatcher.PRIORITY_DEFAULT, onBackInvokedCallback);
        }

        // added for Android 15+ Edge-to-Edge:
        Window window = getWindow();
        int colorDecorViewBG = ThemeUtil.getColor(this,
                android.R.attr.colorForeground);
        window.getDecorView().setBackgroundColor(colorDecorViewBG);

        // Use standard window edge-to-edge flag handling for API 35+
        WindowCompat.setDecorFitsSystemWindows(window, false);

        /*ViewCompat.setOnApplyWindowInsetsListener(window.getDecorView(),
                (v, insets) -> {
                    Insets bars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                                    | WindowInsetsCompat.Type.displayCutout()
                    );
                    v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
                    return WindowInsetsCompat.CONSUMED;
                });*/


        ViewCompat.setOnApplyWindowInsetsListener(window.getDecorView(),
                (v, insets) -> {
                    // Check if we are currently in immersive fullscreen mode
                    boolean isImmersive = (currentWindowDisplayMode == WindowDisplayMode.IMMERSIVE_FULLSCREEN);

                    // In immersive mode, we drop the cutout type so padding is 0 around the camera
                    int insetTypes = isImmersive
                            ? WindowInsetsCompat.Type.systemBars()
                            : (WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());

                    Insets bars = insets.getInsets(insetTypes);
                    v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
                    return WindowInsetsCompat.CONSUMED;
                });

        // 1. RESTORE STATE FIRST (before any layout or view initialization calls)
        if(savedInstanceState != null) {
            int savedModeOrdinal = savedInstanceState.getInt("current_viewer_mode", -1);
            if(savedModeOrdinal >= 0 && savedModeOrdinal < WindowDisplayMode.values().length) {
                currentWindowDisplayMode = WindowDisplayMode.values()[savedModeOrdinal];
            }
        }

        // Apply the mode once layout is ready
        setWindowDisplayMode(currentWindowDisplayMode);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_viewer_mode", currentWindowDisplayMode.ordinal());
    }

    @Override
    protected void onDestroy() {
        if(android.os.Build.VERSION.SDK_INT >= 33) {
            if(onBackInvokedCallback != null) {
                getOnBackInvokedDispatcher()
                        .unregisterOnBackInvokedCallback(onBackInvokedCallback);
            }
        }
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) { processIntent(intent); }

    public void processIntent(Intent intent) {
        if(intent == null) { return; }
        String action = intent.getAction();
        if(action == null) { return; }
        ContentView cv = cvCurrent;
        if(cv == null) { return; }
        cv.processIntent(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ContentView cv = cvCurrent;
        if(cv != null) { cv.onPause(); }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ContentView.setActivity(this);

        ContentView cv = cvCurrent;
        if(cv == null) {
            try {
                cv = (ContentView)cvInitialClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
        if(cv == null) { return; }

        cv.show();
        cv.onResume();

        processIntent(getIntent());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(!hasFocus) { return; }
        ContentView cv = cvCurrent;
        if(cv == null) { return; }
        cv.invalidate();
    }

    public void setContentView(ContentView cv) {

        // first remove from parent if any:
        ViewParent parent = cv.getParent();
        if(parent != null) { ((ViewGroup) parent).removeView(cv); }

        int n = popups.size();
        Popup popup;
        for(int i=0; i<n; i++) {
            popup = popups.get(i);
            parent = popup.getParent();
            if(parent != null) { ((ViewGroup) parent).removeView(popup); }
        }

        // now put on screen:
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.addView(cv);
        for(int i=0; i<n; i++) { frameLayout.addView(popups.get(i)); }
        super.setContentView(frameLayout);
        cvCurrent = cv;
    }

    public ContentView getContentView() { return cvCurrent; }

    @Override
    public void onRequestPermissionsResult(int rqCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtil.onRequestPermissionsResult(rqCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ActivityUtil.onActivityResult(requestCode, resultCode, data);
    }

    private void handleOnBackPressed() {
        if(!popups.isEmpty()) {
            Popup popup = popups.get(popups.size()-1); // last element
            popup.onBackPressed();
            popup.performOutsideClick();
            removePopup(popup);
            return;
        }

        if(cvCurrent != null) {
            boolean consumed = cvCurrent.onBackPressed();
            if(consumed) { return; }
        }

        // else -> not consumed -> close app:
        finish();
    }

    @Override
    public void onBackPressed() { handleOnBackPressed(); }

    @Override
    public void finish() {
        cvCurrent = null; // to create new CV instance on next app start
        super.finish();
    }

    @SuppressWarnings("SameParameterValue")
    static protected void setInitialCvClass(Class<?> c) { SingleActivity.cvInitialClass = c; }

    public void showPopup(Popup popup) {
        if(popup == null) { return; }
        runOnUiThread(() -> {
            popups.remove(popup); // to be sure it will not exist twice
            popups.add(popup);

            // remove from its parent to prevent
            // java.lang.IllegalStateException: The specified child
            // already has a parent. You must call removeView()
            // on the child's parent first.
            ViewParent parent = popup.getParent();
            if(parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(popup);
            }

            FrameLayout frameLayout = (FrameLayout) cvCurrent.getParent();
            frameLayout.addView(popup);
        });
    }

    public void removePopup(Popup popup) {
        if(popup == null) { return; }
        runOnUiThread(() -> {
            SoftKeyboardUtil.hide(popup);
            FrameLayout frameLayout = (FrameLayout) cvCurrent.getParent();
            frameLayout.removeView(popup);
            popups.remove(popup);
        });
    }

    public boolean isShowingPopup(Popup popup) {
        FrameLayout frameLayout = (FrameLayout) cvCurrent.getParent();
        int n = frameLayout.getChildCount();
        for(int i=0; i<n; i++) {
            if(frameLayout.getChildAt(i) == popup) { return true; }
        }
        return false;
    }

    public void setWindowDisplayMode(WindowDisplayMode mode) {
        this.currentWindowDisplayMode = mode;

        Window window = getWindow();
        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(window, window.getDecorView());

        switch (mode) {
            case NORMAL_BARS_THEMED_FG:
                // not fullscreen
                // colorForeground around the camera hole,
                // colorForeground as notification bar
                window.getDecorView().setBackgroundColor(ThemeUtil.getColor(
                        this, android.R.attr.colorForeground));
                // legacy window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                // legacy window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                controller.setSystemBarsBehavior(
                        WindowInsetsControllerCompat.BEHAVIOR_DEFAULT);
                controller.show(WindowInsetsCompat.Type.statusBars());
                controller.show(WindowInsetsCompat.Type.navigationBars());
                break;

            case NORMAL_BARS_THEMED_BG:
                // not fullscreen
                // colorBackground around the camera hole,
                // colorBackground as notification bar
                window.getDecorView().setBackgroundColor(ThemeUtil.getColor(
                        this, android.R.attr.colorBackground));
                // legacy window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                // legacy window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                controller.setSystemBarsBehavior(
                        WindowInsetsControllerCompat.BEHAVIOR_DEFAULT);
                controller.show(WindowInsetsCompat.Type.statusBars());
                controller.show(WindowInsetsCompat.Type.navigationBars());
                break;

            case NORMAL_BARS_BLACK:
                // not fullscreen
                // BLACK color around the camera hole,
                // BLACK color as notification bar
                window.getDecorView().setBackgroundColor(Color.BLACK);
                // legacy window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                // legacy window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                controller.setSystemBarsBehavior(
                        WindowInsetsControllerCompat.BEHAVIOR_DEFAULT);
                controller.show(WindowInsetsCompat.Type.statusBars());
                controller.show(WindowInsetsCompat.Type.navigationBars());
                break;

            case NORMAL_BARS_BLACK_HIDE_NOTIFICATIONS:
                // not fullscreen
                // BLACK color around the camera hole,
                // BLACK color as notification bar
                window.getDecorView().setBackgroundColor(Color.BLACK);
                // legacy window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                // legacy window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                controller.setSystemBarsBehavior(
                        WindowInsetsControllerCompat.BEHAVIOR_DEFAULT);
                controller.hide(WindowInsetsCompat.Type.statusBars());
                controller.show(WindowInsetsCompat.Type.navigationBars());
                break;

            case IMMERSIVE_FULLSCREEN:
                // black bar around the camera hole
                window.getDecorView().setBackgroundColor(0xFF000000);
                // legacy window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                // legacy window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                controller.setSystemBarsBehavior(WindowInsetsControllerCompat
                        .BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
                controller.hide(WindowInsetsCompat.Type.statusBars());
                controller.hide(WindowInsetsCompat.Type.navigationBars());
                break;
        }

        if(android.os.Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams params = window.getAttributes();
            switch(mode) {
                case NORMAL_BARS_BLACK:
                case NORMAL_BARS_BLACK_HIDE_NOTIFICATIONS:
                case IMMERSIVE_FULLSCREEN:
                    params.layoutInDisplayCutoutMode = WindowManager.LayoutParams
                            .LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                    break;
                default:
                    params.layoutInDisplayCutoutMode = WindowManager.LayoutParams
                            .LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
                    break;
            }
            window.setAttributes(params);
        }

        ThemeUtil.adjustStatusAndNavBarColors(this);
        ViewCompat.requestApplyInsets(window.getDecorView());
    }

    public WindowDisplayMode getWindowDisplayMode() {
        return currentWindowDisplayMode;
    }
}