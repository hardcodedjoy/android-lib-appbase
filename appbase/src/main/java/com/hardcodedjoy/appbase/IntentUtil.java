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

package com.hardcodedjoy.appbase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class IntentUtil {

    @SuppressLint("StaticFieldLeak")
    static private Activity activity;

    static public void setActivity(Activity activity) { IntentUtil.activity = activity; }

    @SuppressLint("ObsoleteSdkInt")
    static public Uri getUri(Intent intent) {
        if(android.os.Build.VERSION.SDK_INT < 16) { return intent.getData(); }

        ClipData clipData = intent.getClipData();

        if(clipData != null) {
            if(clipData.getItemCount() > 0) {
                return clipData.getItemAt(0).getUri();
            } else {
                return null;
            }
        } else {
            return intent.getData();
        }
    }

    static public void shareFiles(ArrayList<Uri> uriList, String title, String mimeType) {
        shareFiles(uriList, title, mimeType, null);
    }

    static public void shareFiles(
            ArrayList<Uri> uriList, String title, String mimeType, String packageName) {

        // mimeType = "audio/*" for audio files

        if(uriList == null)   { return; }
        if(uriList.isEmpty()) { return; }

        Intent shareIntent;

        if(uriList.size() == 1) { shareIntent = new Intent(Intent.ACTION_SEND);          }
        else                    { shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE); }

        shareIntent.setType(mimeType);

        if(packageName != null) { shareIntent.setPackage(packageName); }

        if(uriList.size() == 1) { shareIntent.putExtra(Intent.EXTRA_STREAM, uriList.get(0)); }
        else { shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList); }

        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivity(Intent.createChooser(shareIntent, title));
    }

    @SuppressWarnings("unused")
    static public void shareFile(Uri uri, String title, String mimeType) {
        shareFile(uri, title, mimeType, null);
    }

    static public void shareFile(Uri uri, String title, String mimeType, String packageName) {
        if(uri == null) { return; }
        ArrayList<Uri> uriList = new ArrayList<>();
        uriList.add(uri);
        shareFiles(uriList, title, mimeType, packageName);
    }

    static public void shareText(String text, String title) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.setType("text/plain");
        activity.startActivity(Intent.createChooser(shareIntent, title));
    }

    static public void viewFile(Uri uri, String mimeType, int flags) {
        viewFile(uri, mimeType, null, flags);
    }

    static public void viewFile(Uri uri, String mimeType, String packageName, int flags) {
        if(uri == null) { return; }
        Intent intentView = new Intent();
        intentView.setAction(Intent.ACTION_VIEW);
        intentView.setDataAndType(uri, mimeType);
        if(packageName != null) { intentView.setPackage(packageName); }

        intentView.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | flags);
        activity.startActivity(intentView);
    }

    static public void editFile(Uri uri, String mimeType) {
        editFile(uri, mimeType, null);
    }


    static public void editFile(Uri uri, String mimeType, String packageName) {
        if(uri == null) { return; }
        Intent intentEdit = new Intent();
        intentEdit.setAction(Intent.ACTION_EDIT);
        intentEdit.setDataAndType(uri, mimeType);
        if(packageName != null) { intentEdit.setPackage(packageName); }

        intentEdit.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intentEdit.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        activity.startActivity(intentEdit);
    }


    // TODO: test (not tested)
    static public void editFileWith(Uri uri, String title, String mimeType) {
        if(uri == null) { return; }
        Intent intentEdit = new Intent();
        intentEdit.setAction(Intent.ACTION_EDIT);
        intentEdit.setDataAndType(uri, mimeType);
        intentEdit.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intentEdit.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        activity.startActivity(Intent.createChooser(intentEdit, title));
    }


    // TODO: test (not tested)
    static public void openFileWith(Uri uri, String title, String mimeType) {
        if(uri == null) { return; }
        Intent intentView = new Intent();
        intentView.setAction(Intent.ACTION_VIEW);
        intentView.setDataAndType(uri, mimeType);
        intentView.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivity(Intent.createChooser(intentView, title));
    }

    static public void startForegroundService(Intent intent) {
        if(android.os.Build.VERSION.SDK_INT >= 26) {
            activity.getApplicationContext().startForegroundService(intent);
        } else {
            activity.getApplicationContext().startService(intent);
        }
    }

    static public void startForegroundService(Class<?> serviceClass) {
        Intent intent = new Intent(activity.getApplicationContext(), serviceClass);
        startForegroundService(intent);
    }

    static public void stopService(Intent intent) {
        activity.getApplicationContext().stopService(intent);
    }

    static public void stopService(Class<?> serviceClass) {
        Intent intent = new Intent(activity.getApplicationContext(), serviceClass);
        stopService(intent);
    }

    static public PendingIntent getPendingBroadcast(int rqCode, Class<?> receiver, String action) {
        Intent intent = new Intent(activity, receiver).setAction(action);
        int flags = 0;
        if(android.os.Build.VERSION.SDK_INT >= 23) { flags = PendingIntent.FLAG_IMMUTABLE; }
        return PendingIntent.getBroadcast(activity, rqCode, intent, flags);
    }

    static public PendingIntent getPendingActivity(Context ct, int rqCode, Activity activity) {
        Intent notificationIntent = new Intent(ct, activity.getClass());
        int flags = 0;
        if(android.os.Build.VERSION.SDK_INT >= 23) { flags = PendingIntent.FLAG_IMMUTABLE; }
        return PendingIntent.getActivity(ct, rqCode, notificationIntent, flags);
    }
}