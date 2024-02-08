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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.SoftKeyboardUtil;
import com.hardcodedjoy.appbase.contentview.ContentView;

abstract public class PopupEnterPassword extends Popup {

    private final EditText etInput;

    abstract public void onOK(String s);
    @Override
    public void onCancel() { }

    public PopupEnterPassword(String title, String message, String def) {

        // first inflate:

        inflater.inflate(R.layout.appbase_popup_enter_password, this);

        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        TextView tvMessage = findViewById(R.id.appbase_tv_message);
        etInput = findViewById(R.id.appbase_et_input);

        if(title != null) { tvTitle.setText(title); }

        if(message == null) { tvMessage.setVisibility(GONE); }
        else { tvMessage.setVisibility(VISIBLE); tvMessage.setText(message); }

        if(def != null) { etInput.setText(def); }

        ImageButton btnOK = findViewById(R.id.appbase_btn_ok);
        ImageButton btnCancel = findViewById(R.id.appbase_btn_cancel);

        btnOK.setOnClickListener(ocl);
        btnCancel.setOnClickListener(ocl);
        setOnClickListener(ocl);
    }

    @Override
    void oclOnClick(View view) {
        ContentView.removePopUp(this); // dismiss
        int id = view.getId();
        if(id == R.id.appbase_btn_ok || id == R.id.appbase_btn_ok_text) { onOK(etInput.getText().toString()); }
        else if(id == R.id.appbase_btn_cancel || id == R.id.appbase_btn_cancel_text) { onCancel(); }
        else if(id == R.id.appbase_ll_outside_popup) { onCancel(); }
    }

    public PopupEnterPassword(int titleStringId, int messageStringId, String def) {
        this(getString(titleStringId), getString(messageStringId), def);
    }

    public PopupEnterPassword(String title, String message) { this(title, message, null); }
    public PopupEnterPassword(String title) { this(title, null, null); }
    public PopupEnterPassword(int titleStringId, int messageStringId) {
        this(titleStringId, messageStringId, null);
    }
    public PopupEnterPassword(int titleStringId) { this(getString(titleStringId), null, null); }
    public PopupEnterPassword(int titleStringId, String def) {
        this(getString(titleStringId), null, def);
    }

    public PopupEnterPassword() { this(null, null, null); }

    public EditText getEtInput() { return etInput; }

    public void show() {
        super.show();
        SoftKeyboardUtil.showAndSelectAll(etInput);
    }
}