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

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.contentview.ContentView;

@SuppressLint("ViewConstructor")
public class PopupInfo extends Popup {

    @Override
    public void onCancel() {}

    public PopupInfo(String title, String message) {

        // first inflate:
        inflater.inflate(R.layout.appbase_popup_info, this);

        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        TextView tvMessage = findViewById(R.id.appbase_tv_message);
        ImageButton btnCancel = findViewById(R.id.appbase_btn_cancel);

        if(title != null) { tvTitle.setText(title); }

        if(message == null) { tvMessage.setVisibility(GONE); }
        else { tvMessage.setVisibility(VISIBLE); tvMessage.setText(message); }

        btnCancel.setOnClickListener(ocl);
    }

    @Override
    void oclOnClick(View view) {
        ContentView.removePopUp(this); // dismiss
        onCancel();
    }

    public PopupInfo(String message) { this(null, message); }

    public PopupInfo(int titleStringId, int messageStringId) {
        this(getString(titleStringId), getString(messageStringId));
    }

    public PopupInfo(int messageStringId) {
        this(null, getString(messageStringId));
    }
}