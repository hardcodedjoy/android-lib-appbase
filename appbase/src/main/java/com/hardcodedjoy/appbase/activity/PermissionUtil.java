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

package com.hardcodedjoy.appbase.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.Vector;

public class PermissionUtil {

    static private int requestCode = 0;
    static private final Vector<Integer> requestCodes = new Vector<>();
    static private final Vector<Runnable> tasks = new Vector<>();

    static public void runWithPermission(
            Activity activity, String permission, Runnable onPermissionsGranted) {
        runWithPermissions(activity, new String[]{permission}, onPermissionsGranted);
    }

    static public void runWithPermissions(
            Activity activity, String[] permissions, Runnable onPermissionsGranted) {

        if(Build.VERSION.SDK_INT < 23) {
            activity.runOnUiThread(onPermissionsGranted);
            return;
        }

        for(String permission : permissions) {
            if(activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                addTask(requestCode, () -> activity.runOnUiThread(onPermissionsGranted));
                activity.requestPermissions(permissions, requestCode++);
                if(requestCode < 0) { requestCode = 0; }
                return;
            }
        }

        activity.runOnUiThread(onPermissionsGranted);
    }

    static private void addTask(int requestCode, Runnable task) {
        synchronized(tasks) {
            requestCodes.add(requestCode);
            tasks.add(task);
        }
    }

    static private void runTask(int requestCode) {
        synchronized(tasks) {
            int n = tasks.size();
            for(int i=0; i<n; i++) {
                if(requestCodes.elementAt(i) == requestCode) {
                    tasks.elementAt(i).run();
                    requestCodes.remove(i);
                    tasks.remove(i);
                    return;
                }
            }
        }
    }

    static public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        int n = permissions.length;
        for(int i=0; i<n; i++) {
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED) { return; }
        }
        runTask(requestCode);
    }
}