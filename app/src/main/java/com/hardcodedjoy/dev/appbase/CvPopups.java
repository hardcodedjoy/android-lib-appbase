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

package com.hardcodedjoy.dev.appbase;

import android.annotation.SuppressLint;

import com.hardcodedjoy.appbase.contentview.ContentView;
import com.hardcodedjoy.appbase.gui.GuiLinker;
import com.hardcodedjoy.appbase.popup.PopupAsk;
import com.hardcodedjoy.appbase.popup.PopupError;
import com.hardcodedjoy.appbase.popup.PopupInfo;
import com.hardcodedjoy.appbase.popup.PopupInput;

@SuppressLint("ViewConstructor")
public class CvPopups extends ContentView {

    public CvPopups() {
        // add initialization code here (that must run only one time)
        inflate(R.layout.appbase_cv_popups);

        GuiLinker.setOnClickListenerToAllButtons(this, view -> {
            int id = view.getId();

            if(id == R.id.btn_popup_ask) {
                new PopupAsk(R.string.delete, R.string.are_you_sure) {
                    @Override
                    public void onOK() {}
                }.show();
            }

            if(id == R.id.btn_popup_info) { showInfo(R.string.android_is_cool); }
            if(id == R.id.btn_popup_error) { showError(R.string.err_file_not_found); }

            if(id == R.id.btn_popup_input) {
                new PopupInput(R.string.type_something) {
                    @Override
                    public void onOK(String s) { showInfo(s); }
                }.show();
            }
        });
    }
}