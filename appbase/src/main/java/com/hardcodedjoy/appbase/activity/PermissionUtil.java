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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import java.util.Vector;

public class PermissionUtil {

    @SuppressLint("StaticFieldLeak")
    static private Activity activity;

    static public void setActivity(Activity activity) { PermissionUtil.activity = activity; }

    static private int requestCode = 0;
    static private final Vector<Integer> requestCodes = new Vector<>();
    static private final Vector<Runnable> tasksOnGranted = new Vector<>();
    static private final Vector<Runnable> tasksOnDenied = new Vector<>();

    @SuppressWarnings("unused")
    static public void runWithPermission(String permission, Runnable onGranted) {
        runWithPermissions(new String[]{permission}, onGranted);
    }

    @SuppressWarnings("unused")
    static public void runWithPermission(String permission, Runnable onGranted, Runnable onDenied) {
        runWithPermissions(new String[]{permission}, onGranted, onDenied);
    }

    static public void runWithPermissions(String[] permissions, Runnable onGranted) {
        runWithPermissions(permissions, onGranted, () -> {});
    }

    static public void runWithPermissions(String[] permissions,
                                          Runnable onGranted, Runnable onDenied) {

        // all granted -> onGranted
        // one denied -> onDenied, stop checking others

        if(Build.VERSION.SDK_INT < 23) {
            activity.runOnUiThread(onGranted);
            return;
        }
        permissions = onlyNonGranted(permissions);
        if(permissions.length == 0) {
            activity.runOnUiThread(onGranted);
            return;
        }

        addTask(requestCode,
                () -> activity.runOnUiThread(onGranted),
                () -> activity.runOnUiThread(onDenied));

        activity.requestPermissions(permissions, requestCode++);
        if(requestCode < 0) { requestCode = 0; }
    }

    static private String[] onlyNonGranted(String[] permissions) {
        Vector<String> nonGranted = new Vector<>();
        for(String p : permissions) { if(!permissionAlreadyGranted(p)) { nonGranted.add(p); } }
        return nonGranted.toArray(new String[0]);
    }

    static private void addTask(int requestCode, Runnable taskOnGranted, Runnable taskOnDenied) {
        synchronized(requestCodes) {
            requestCodes.add(requestCode);
            tasksOnGranted.add(taskOnGranted);
            tasksOnDenied.add(taskOnDenied);
        }
    }

    static private void runTask(int requestCode, int grantResult) {
        synchronized(requestCodes) {
            int n = requestCodes.size();
            for(int i=0; i<n; i++) {
                if(requestCodes.elementAt(i) != requestCode) { continue; }

                if(grantResult == PackageManager.PERMISSION_GRANTED) {
                    tasksOnGranted.elementAt(i).run();
                } else if(grantResult == PackageManager.PERMISSION_DENIED) {
                    tasksOnDenied.elementAt(i).run();
                }

                requestCodes.remove(i);
                tasksOnGranted.remove(i);
                tasksOnDenied.remove(i);
                return;
            }
        }
    }

    static public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        int n = permissions.length;
        for(int i=0; i<n; i++) {
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                runTask(requestCode, PackageManager.PERMISSION_DENIED);
                return;
            }
        }
        runTask(requestCode, PackageManager.PERMISSION_GRANTED);
    }

    static public boolean permissionAlreadyGranted(String permission) {
        if (android.os.Build.VERSION.SDK_INT < 23) { return true; }
        return (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
    }

    static public void takePersistableUriPermissionRead(Uri uri) {
        activity.getContentResolver().takePersistableUriPermission(
                uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }

    static public void takePersistableUriPermissionReadWrite(Uri uri) {
        activity.getContentResolver().takePersistableUriPermission(
                uri, Intent.FLAG_GRANT_READ_URI_PERMISSION |
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }

    static public void releasePersistableUriPermissionRead(Uri uri) {
        activity.getContentResolver().releasePersistableUriPermission(
                uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }

    static public void releasePersistableUriPermissionReadWrite(Uri uri) {
        activity.getContentResolver().releasePersistableUriPermission(
                uri, Intent.FLAG_GRANT_READ_URI_PERMISSION |
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }
}