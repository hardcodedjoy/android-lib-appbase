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

package com.hardcodedjoy.appbase.popup;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.contentview.ContentView;

public class PopupProgress extends Popup {

    private final TextView tvMessage;
    private final ProgressBar progressBar;
    private boolean canceled;

    @Override
    public void onCancel() { canceled = true; }
    public boolean isCanceled() { return canceled; }

    public PopupProgress(String title, String message) {

        // first inflate:
        inflater.inflate(R.layout.appbase_popup_progress, this);

        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        tvMessage = findViewById(R.id.appbase_tv_message);
        progressBar = findViewById(R.id.appbase_pb_progress);

        if(title != null) tvTitle.setText(title);

        if(message == null) { tvMessage.setVisibility(GONE); }
        else { tvMessage.setVisibility(VISIBLE); tvMessage.setText(message); }

        progressBar.setMax(1000);
        progressBar.setProgress(0);

        ImageButton btnCancel = findViewById(R.id.appbase_btn_cancel);
        btnCancel.setOnClickListener(ocl);
    }

    @Override
    void oclOnClick(View view) { // only btnCancel will call this
        ContentView.removePopUp(this); // dismiss
        onCancel();
    }

    public PopupProgress(String title) { this(title, null); }

    public PopupProgress(int titleStringId) {
        this(ContentView.getString(titleStringId), null);
    }

    public void setMessage(String message) {
        ContentView.runOnUiThread(() -> {
            if(message == null) { tvMessage.setVisibility(GONE); return; }
            tvMessage.setText(message);
            tvMessage.setVisibility(VISIBLE);
        });
    }

    public void setMessage(int messageStringId) {
        ContentView.runOnUiThread(() -> {
            tvMessage.setText(messageStringId);
            tvMessage.setVisibility(VISIBLE);
        });
    }

    public ProgressBar getProgressBar() { return progressBar; }

    public void setProgress(final float progress) {
        ContentView.runOnUiThread(() -> {
            progressBar.setMax(1000);
            progressBar.setProgress((int)(progress * 1000));
        });
    }

    public void setProgress(final int progress, final int max) {
        ContentView.runOnUiThread(() -> {
            progressBar.setMax(max);
            progressBar.setProgress(progress);
        });
    }
}