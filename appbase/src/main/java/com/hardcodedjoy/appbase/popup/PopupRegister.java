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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.SoftKeyboardUtil;
import com.hardcodedjoy.appbase.contentview.ContentView;

abstract public class PopupRegister extends Popup {

    private final EditText etUsername;
    private final EditText etPassword;
    private final EditText etConfirmPassword;

    abstract public void onOK(String username, String password, String repeatPassword);
    @Override
    public void onCancel() { }

    public PopupRegister(String message) {

        // first inflate:

        inflater.inflate(R.layout.appbase_popup_register, this);

        TextView tvMessage = findViewById(R.id.appbase_tv_message);
        etUsername = findViewById(R.id.appbase_et_username);
        etPassword = findViewById(R.id.appbase_et_password);
        etConfirmPassword = findViewById(R.id.appbase_et_repeat_password);

        if(message == null) { tvMessage.setVisibility(GONE); }
        else { tvMessage.setVisibility(VISIBLE); tvMessage.setText(message); }

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
        if(id == R.id.appbase_btn_ok || id == R.id.appbase_btn_ok_text) {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String repeatPassword = etConfirmPassword.getText().toString();
            onOK(username, password, repeatPassword);
        }
        else if(id == R.id.appbase_btn_cancel || id == R.id.appbase_btn_cancel_text) { onCancel(); }
    }

    public PopupRegister(int messageStringId) { this(getString(messageStringId)); }
    public PopupRegister() { this(null); }

    public EditText getEtUsername() { return etUsername; }
    public EditText getEtPassword() { return etPassword; }
    public EditText getEtConfirmPassword() { return etConfirmPassword; }

    public void show() {
        super.show();
        SoftKeyboardUtil.showAndSelectAll(etUsername);
    }
}