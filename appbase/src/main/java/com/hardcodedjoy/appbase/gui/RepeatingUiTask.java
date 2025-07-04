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

package com.hardcodedjoy.appbase.gui;

import android.os.Handler;
import android.os.Looper;

@SuppressWarnings("unused")
public class RepeatingUiTask {

    private final Handler handler;
    private final Runnable taskRunnable;
    private final int intervalMillis;
    private boolean running;

    public RepeatingUiTask(final Runnable runnable, final int intervalMillis) {
        this.handler = new Handler(Looper.getMainLooper());
        this.intervalMillis = intervalMillis;

        taskRunnable = new Runnable() {
            @Override
            public void run() {
                if(!running) { return; } // if already stopped
                // else, if still running ( not stopped from runnable.run(); )
                runnable.run();
                handler.postDelayed(this, intervalMillis);
            }
        };
    }

    public synchronized void start() {
        handler.postDelayed(taskRunnable, intervalMillis);
        running = true;
    }
    public synchronized void stop() {
        handler.removeCallbacks(taskRunnable);
        running = false;
    }

    public synchronized boolean isRunning() { return running; }
}