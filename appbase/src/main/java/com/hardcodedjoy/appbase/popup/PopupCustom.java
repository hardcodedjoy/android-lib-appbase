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

package com.hardcodedjoy.appbase.popup;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.contentview.ContentView;

abstract public class PopupCustom extends Popup {

    private final TextView tvMessage;
    private final LinearLayout llContent;

    abstract public void onOK();
    @Override
    public void onCancel() { }

    public PopupCustom(String title, String message) {

        // first inflate:
        inflater.inflate(R.layout.appbase_popup_custom, this);

        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        tvMessage = findViewById(R.id.appbase_tv_message);
        llContent = findViewById(R.id.appbase_ll_content);

        if(title != null) { tvTitle.setText(title); }

        if(message == null) { tvMessage.setVisibility(GONE); }
        else { tvMessage.setVisibility(VISIBLE); tvMessage.setText(message); }

        findViewById(R.id.appbase_btn_ok).setOnClickListener(ocl);
        findViewById(R.id.appbase_btn_cancel).setOnClickListener(ocl);
    }

    @Override
    void oclOnClick(View view) {
        ContentView.removePopUp(this); // dismiss

        int id = view.getId();
        if(id == R.id.appbase_btn_ok || id == R.id.appbase_btn_ok_text) { onOK(); }
        else if(id == R.id.appbase_btn_cancel || id == R.id.appbase_btn_cancel_text) { onCancel(); }
        else if(id == R.id.appbase_ll_outside_popup) { onCancel(); }
    }

    public PopupCustom(String title) { this(title, null); }

    @SuppressWarnings("unused")
    public PopupCustom(int titleStringId) {
        this(ContentView.getString(titleStringId), null);
    }

    @SuppressWarnings("unused")
    public PopupCustom(int titleStringId, int messageStringId) {
        this(ContentView.getString(titleStringId), ContentView.getString(messageStringId));
    }

    @SuppressWarnings("unused")
    public PopupCustom setContentView(int viewId) {
        inflater.inflate(viewId, llContent);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public PopupCustom setContentView(View view) {
        llContent.removeAllViews();
        llContent.addView(view);
        return this;
    }

    public void setMessage(final String message) {
        ContentView.runOnUiThread(() -> {
            if(message == null) { tvMessage.setVisibility(GONE); return; }
            // else:
            tvMessage.setText(message);
            tvMessage.setVisibility(VISIBLE);
        });
    }

    public void hideButtonsOkCancel() {
        findViewById(R.id.appbase_btn_ok).setVisibility(GONE);
        findViewById(R.id.appbase_btn_cancel).setVisibility(GONE);
    }
}