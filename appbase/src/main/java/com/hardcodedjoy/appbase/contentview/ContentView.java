/*

MIT License

Copyright © 2023 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hardcodedjoy.appbase.activity.ActivityResultTask;
import com.hardcodedjoy.appbase.activity.ActivityUtil;
import com.hardcodedjoy.appbase.activity.PermissionUtil;
import com.hardcodedjoy.appbase.activity.SingleActivity;
import com.hardcodedjoy.appbase.gui.ThemeUtil;
import com.hardcodedjoy.appbase.popup.Popup;
import com.hardcodedjoy.appbase.popup.PopupError;
import com.hardcodedjoy.appbase.popup.PopupInfo;

@SuppressLint("ViewConstructor")
public class ContentView extends LinearLayout {

    static private SingleActivity activity;
    static private LayoutInflater inflater;

    static protected Object settings;

    protected ContentView cvPrevious;

    private boolean showAlreadyCalled;

    static public void setActivity(SingleActivity activity) {
        ContentView.activity = activity;
        ContentView.inflater = activity.getLayoutInflater();
        ContentView.settings = activity.getSettings();
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

    // to be overridden by apps that can be started by intents other than Intent.ACTION_MAIN
    // @NonNull intent, @NonNull intent.getAction()
    public void processIntent(Intent intent) {}

    public boolean onBackPressed() {
        // no previous -> backPressed not consumed by this ContentView
        if(cvPrevious == null) { return false; }

        // else -> go to previous if any:
        cvPrevious.show();
        return true; // consumed
    }






    static public String getString(int resId) { return getActivity().getString(resId); }

    static public void startActivityForResult(Class<?> c, ActivityResultTask onResultOK) {
        ActivityUtil.startActivityForResult(getActivity(), c, onResultOK);
    }

    static public void startActivityForResult(Class<?> c, Runnable onResultOK) {
        ActivityUtil.startActivityForResult(getActivity(), c, onResultOK);
    }

    static public void startActivityForResult(Intent intent, ActivityResultTask onResultOK) {
        ActivityUtil.startActivityForResult(getActivity(), intent, onResultOK);
    }

    static public void runWithPermissions(String[] permissions, Runnable onPermissionsGranted) {
        PermissionUtil.runWithPermissions(getActivity(), permissions, onPermissionsGranted);
    }

    static public void runWithPermission(String permission, Runnable onPermissionsGranted) {
        PermissionUtil.runWithPermission(getActivity(), permission, onPermissionsGranted);
    }

    static public void runOnUiThread(Runnable r) { activity.runOnUiThread(r); }
    static public void showPopUp(Popup popup) { activity.showPopup(popup); }
    static public void removePopUp(Popup popup) { activity.removePopup(popup); }
    static public boolean isShowingPopUp(Popup popup) { return activity.isShowingPopup(popup); }


    static public void showError(String error, Runnable onCancel) {
        runOnUiThread(() -> new PopupError(error) {
            @Override
            public void onCancel() { if(onCancel != null) { onCancel.run(); } }
        }.show());
    }

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

    static public int getColor(int id) {
        if(android.os.Build.VERSION.SDK_INT >= 23) {
            return activity.getResources().getColor(id, null);
        }
        //noinspection deprecation
        return activity.getResources().getColor(id);
    }

    static public int getThemeColor(int androidRAttrColorId) {
        return ThemeUtil.getColor(activity, androidRAttrColorId);
    }

    // set background to color selected for a few ms, then unset, then run runnable
    static public void colorThen(View view, int colorCode, Runnable next) {
        colorThen(view, colorCode, 100, next);
    }

    static public void colorThen(View view, int colorCode, int delayMillis, Runnable next) {
        view.setBackgroundColor(colorCode);
        new Thread() {
            @Override
            public void run() {
                try { Thread.sleep(delayMillis); } catch (Exception e) { /**/ }
                runOnUiThread(next);
            }
        }.start();
    }
}