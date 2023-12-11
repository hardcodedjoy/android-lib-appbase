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
import android.content.Intent;

import java.util.Vector;

public class ActivityUtil {

    static private int requestCode = 0;
    static private final Vector<Integer> requestCodes = new Vector<>();
    static private final Vector<ActivityResultTask> tasks = new Vector<>();

    static public void startActivity(Activity activity, Class<?> c) {
        activity.startActivity(new Intent(activity, c));
    }

    static public void startActivityForResult(Activity activity, Class<?> c, ActivityResultTask onResultOK) {
        // force runnable to UI Thread:
        Runnable r = onResultOK.getRunnable();
        onResultOK.setRunnable(() -> activity.runOnUiThread(r));

        addTask(requestCode, onResultOK);
        activity.startActivityForResult(new Intent(activity, c), requestCode++);
    }

    static public void startActivityForResult(Activity activity, Class<?> c, Runnable onResultOK) {
        ActivityResultTask task = new ActivityResultTask(() -> activity.runOnUiThread(onResultOK));
        addTask(requestCode, task);
        activity.startActivityForResult(new Intent(activity, c), requestCode++);
    }

    static public void startActivityForResult(Activity activity, Intent intent, ActivityResultTask onResultOK) {
        // force runnable to UI Thread:
        Runnable r = onResultOK.getRunnable();
        onResultOK.setRunnable(() -> activity.runOnUiThread(r));

        addTask(requestCode, onResultOK);
        activity.startActivityForResult(intent, requestCode++);
    }

    static private void addTask(int requestCode, ActivityResultTask task) {
        synchronized(tasks) {
            requestCodes.add(requestCode);
            tasks.add(task);
        }
    }

    static private void runTask(int requestCode, Intent data) {
        synchronized(tasks) {
            int n = tasks.size();
            for(int i=0; i<n; i++) {
                if(requestCodes.elementAt(i) == requestCode) {
                    tasks.elementAt(i).setData(data);
                    tasks.elementAt(i).getRunnable().run();
                    requestCodes.remove(i);
                    tasks.remove(i);
                    return;
                }
            }
        }
    }

    @SuppressWarnings("unused")
    static public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) { return; }
        runTask(requestCode, data);
    }
}