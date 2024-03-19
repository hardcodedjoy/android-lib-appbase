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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.contentview.ContentView;

abstract public class PopupAsk extends Popup {

    private final Button btnPositive;
    private final Button btnNegative;
    private final ImageButton btnOK;
    private final ImageButton btnCancel;

    abstract public void onOK();

    @Override
    public void onCancel() { }

    public PopupAsk(String title, String message) {

        // first inflate:
        inflater.inflate(R.layout.appbase_popup_ask, this);

        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        TextView tvMessage = findViewById(R.id.appbase_tv_message);

        if(title != null) tvTitle.setText(title);

        if(message == null) { tvMessage.setVisibility(GONE); }
        else { tvMessage.setVisibility(VISIBLE); tvMessage.setText(message); }

        btnOK = findViewById(R.id.appbase_btn_ok);
        btnCancel = findViewById(R.id.appbase_btn_cancel);
        btnPositive = findViewById(R.id.appbase_btn_ok_text);
        btnNegative = findViewById(R.id.appbase_btn_cancel_text);

        btnOK.setOnClickListener(ocl);
        btnCancel.setOnClickListener(ocl);
        btnPositive.setOnClickListener(ocl);
        btnNegative.setOnClickListener(ocl);
        setOnClickListener(ocl);
    }

    @Override
    void oclOnClick(View view) {
        ContentView.removePopUp(this);  // dismiss
        int id = view.getId();
             if(id == R.id.appbase_btn_ok || id == R.id.appbase_btn_ok_text) { onOK(); }
        else if(id == R.id.appbase_btn_cancel || id == R.id.appbase_btn_cancel_text) { onCancel(); }
        else if(id == R.id.appbase_ll_outside_popup) { onCancel(); }
    }

    public PopupAsk(String title, String message, String positive, String negative) {
        this(title, message);
        btnPositive.setText(positive);
        btnNegative.setText(negative);

        btnOK.setVisibility(GONE);
        btnCancel.setVisibility(GONE);
        btnPositive.setVisibility(VISIBLE);
        btnNegative.setVisibility(VISIBLE);

        if(positive.length() > 10 && negative.length() < 5) {
            ((LinearLayout.LayoutParams)btnPositive.getLayoutParams()).weight = 0.7f;
            ((LinearLayout.LayoutParams)btnNegative.getLayoutParams()).weight = 1.3f;
        } else if(negative.length() > 10 && positive.length() < 5) {
            ((LinearLayout.LayoutParams)btnNegative.getLayoutParams()).weight = 0.7f;
            ((LinearLayout.LayoutParams)btnPositive.getLayoutParams()).weight = 1.3f;
        }
    }

    public PopupAsk(int titleStringId, int messageStringId,
                    int positiveStringId, int negativeStringId) {
        this(getString(titleStringId),
             getString(messageStringId),
             getString(positiveStringId),
             getString(negativeStringId));
    }

    public PopupAsk(int titleStringId, int messageStringId) {
        this(getString(titleStringId), getString(messageStringId));
    }

    public PopupAsk(String title, int messageStringId) {
        this(title, getString(messageStringId));
    }

    public PopupAsk(int titleStringId, String message) {
        this(getString(titleStringId), message);
    }

    public PopupAsk(String title, String message,
                    int positiveStringId, int negativeStringId) {
        this(title, message, getString(positiveStringId), getString(negativeStringId));
    }

    public PopupAsk(int titleStringId, String message,
                    int positiveStringId, int negativeStringId) {
        this(getString(titleStringId), message,
                getString(positiveStringId), getString(negativeStringId));
    }

}