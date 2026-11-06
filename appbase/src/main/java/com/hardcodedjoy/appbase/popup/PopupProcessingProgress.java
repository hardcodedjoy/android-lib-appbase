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

package com.hardcodedjoy.appbase.popup;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.hardcodedjoy.appbase.contentview.ContentView;

import java.util.concurrent.atomic.AtomicBoolean;

@SuppressLint("ViewConstructor")
public class PopupProcessingProgress extends PopupProgress {

    private final TextView tvTitle;
    private final AtomicBoolean cancel;

    public PopupProcessingProgress() {
        super(com.hardcodedjoy.appbase.R.string.title_processing);
        tvTitle = findViewById(com.hardcodedjoy.appbase.R.id.appbase_tv_title);
        cancel = new AtomicBoolean(false);

        setMessage("0.0 %");
        setProgress(0);
    }

    public void setTitle(String s)  { ContentView.runOnUiThread(() -> tvTitle.setText(s)); }
    public void setTitle(int resId) { ContentView.runOnUiThread(() -> tvTitle.setText(resId)); }

    @Override
    public void onCancel() { cancel.set(true); }

    @Override
    public void setProgress(float progress) {
        super.setProgress(progress); // will run on UI thread
        int progress1000 = (int) (progress * 1000 + 0.5f);
        String message = progress1000/10 + "." + progress1000%10 + " %";
        setMessage(message); // will run on UI thread
    }

    public AtomicBoolean getCancelSignal() { return cancel; }
}