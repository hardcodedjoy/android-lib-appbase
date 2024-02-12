/*

MIT License

Copyright © 2024 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.FrameLayout;

import com.hardcodedjoy.appbase.FileUtil;
import com.hardcodedjoy.appbase.IntentUtil;
import com.hardcodedjoy.appbase.SoftKeyboardUtil;
import com.hardcodedjoy.appbase.contentview.ContentView;
import com.hardcodedjoy.appbase.Settings;
import com.hardcodedjoy.appbase.gui.ThemeUtil;
import com.hardcodedjoy.appbase.popup.Popup;

import java.util.Vector;

public class SingleActivity extends Activity {

    private Object settings;

    // class of settings, to be set to app specific settings using setSettingsClass()
    static private Class<?> settingsClass = Settings.class;

    // class of cv to be displayed on app start
    static private Class<?> cvInitialClass = null;
    static private ContentView cvCurrent = null;
    static private final Vector<Popup> popups = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FileUtil.setActivity(this);
        IntentUtil.setActivity(this);
        PermissionUtil.setActivity(this);

        // we use our own title bar in "layout_main"
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        try {
            settings = settingsClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            settings = new Settings();
        }

        Settings settings = (Settings) this.settings;

        SharedPreferences sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        settings.setSharedPreferences(sp);
        settings.onLoad();

        if(settings.getLightTheme() == null) {
            settings.setLightTheme(ThemeUtil.getThemes(this, true)[0]);
        }
        if(settings.getDarkTheme() == null) {
            settings.setDarkTheme(ThemeUtil.getThemes(this, false)[0]);
        }

        // settings.getTheme needs activity to get the day / night mode of android settings
        ThemeUtil.setTheme(this, settings.getTheme(this));
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
                cv = null;
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
            popup = popups.elementAt(i);
            parent = popup.getParent();
            if(parent != null) { ((ViewGroup) parent).removeView(popup); }
        }

        // now put on screen:
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.addView(cv);
        for(int i=0; i<n; i++) { frameLayout.addView(popups.elementAt(i)); }
        super.setContentView(frameLayout);
        cvCurrent = cv;
    }

    public ContentView getContentView() { return cvCurrent; }

    public Object getSettings() { return settings; }

    @Override
    public void onRequestPermissionsResult(int rqCode, String[] permissions, int[] grantResults) {
        PermissionUtil.onRequestPermissionsResult(rqCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ActivityUtil.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if(popups.size() > 0) {
            Popup popup = popups.lastElement();
            removePopup(popup);
            runOnUiThread(popup::onCancel);
            return;
        }

        if(cvCurrent != null) {
            boolean consumed = cvCurrent.onBackPressed();
            if(consumed) { return; }
        }
        cvCurrent = null;
        super.onBackPressed();
        super.finish();
    }

    @SuppressWarnings("SameParameterValue")
    static protected void setSettingsClass(Class<?> c) { SingleActivity.settingsClass = c; }

    @SuppressWarnings("SameParameterValue")
    static protected void setInitialCvClass(Class<?> c) { SingleActivity.cvInitialClass = c; }

    public void showPopup(Popup popup) {
        if(popup == null) { return; }
        runOnUiThread(() -> {
            popups.add(popup);
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
}