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

package com.hardcodedjoy.dev.appbase;

import android.annotation.SuppressLint;

import com.hardcodedjoy.appbase.contentview.ContentView;
import com.hardcodedjoy.appbase.gui.GuiLinker;
import com.hardcodedjoy.appbase.popup.Option;
import com.hardcodedjoy.appbase.popup.PopupAsk;
import com.hardcodedjoy.appbase.popup.PopupAvailableOnlyInFullVersion;
import com.hardcodedjoy.appbase.popup.PopupChoose;
import com.hardcodedjoy.appbase.popup.PopupColorPicker;
import com.hardcodedjoy.appbase.popup.PopupCustom;
import com.hardcodedjoy.appbase.popup.PopupEnterPassword;
import com.hardcodedjoy.appbase.popup.PopupImage;
import com.hardcodedjoy.appbase.popup.PopupInput;
import com.hardcodedjoy.appbase.popup.PopupLogin;
import com.hardcodedjoy.appbase.popup.PopupPleaseWait;
import com.hardcodedjoy.appbase.popup.PopupProgress;
import com.hardcodedjoy.appbase.popup.PopupRegister;
import com.hardcodedjoy.appbase.popup.PopupSelectTimeInterval;
import com.hardcodedjoy.appbase.popup.PopupSetPassword;
import com.hardcodedjoy.appbase.popup.PopupSetTimeHHmmssmmm;

import java.util.Vector;

@SuppressLint("ViewConstructor")
public class CvPopups extends ContentView {

    private int colorPickerColor;
    private long timeMillis;

    public CvPopups() {
        // add initialization code here (that must run only one time)
        inflate(R.layout.cv_popups);

        GuiLinker.setOnClickListenerToAllButtons(this, view -> {
            int id = view.getId();

            if(id == R.id.btn_popup_ask) { showPopupAsk(); }
            if(id == R.id.btn_popup_ask_2) { showPopupAsk2(); }
            if(id == R.id.btn_popup_ask_3) { showPopupAsk3(); }
            if(id == R.id.btn_popup_choose) { showPopupChoose(); }
            if(id == R.id.btn_popup_color_picker) { showPopupColorPicker(); }
            if(id == R.id.btn_popup_custom) { showPopupCustom(); }
            if(id == R.id.btn_popup_error) { showError(R.string.err_file_not_found); }
            if(id == R.id.btn_popup_image) { showPopupImage(); }
            if(id == R.id.btn_popup_info) { showInfo(R.string.android_is_cool); }
            if(id == R.id.btn_popup_input) { showPopupInput(); }
            if(id == R.id.btn_popup_please_wait) { showPopupPleaseWait(); }
            if(id == R.id.btn_popup_progress) { showPopupProgress(); }
            if(id == R.id.btn_popup_sel_time_interval) { showPopupSelectTimeInterval(); }
            if(id == R.id.btn_popup_set_password) { showPopupSetPassword(); }
            if(id == R.id.btn_popup_enter_password) { showPopupEnterPassword(); }
            if(id == R.id.btn_popup_register) { showPopupRegister(); }
            if(id == R.id.btn_popup_login) { showPopupLogin(); }
            if(id == R.id.btn_popup_available_only_in_full_version) {
                showPopupAvailableOnlyInFullVersion();
            }
            if(id == R.id.btn_popup_set_time) { showPopupSetTime(); }
        });

        colorPickerColor = 0xFF008000;
    }

    private void showPopupAsk() {
        new PopupAsk(R.string.title_delete, R.string.are_you_sure) {
            @Override
            public void onOK() {}
        }.show();
    }

    private void showPopupAsk2() {
        new PopupAsk(
                R.string.popup_ask_with_text_buttons,
                R.string.are_you_sure,
                R.string.yes_that_would_be_great,
                R.string.btn_no) {
            @Override
            public void onOK() {}
        }.show();
    }

    private void showPopupAsk3() {
        new PopupAsk(
                R.string.popup_ask_with_text_buttons,
                R.string.are_you_sure,
                R.string.btn_yes,
                R.string.no_i_dont_want_that) {
            @Override
            public void onOK() {}
        }.show();
    }

    private void showPopupChoose() {
        Vector<Option> op = new Vector<>();

        op.add(new Option(R.drawable.ic_settings_1, "option A", () -> {}));
        op.add(new Option(R.drawable.ic_info_1, "option B", () -> {}));

        Option optionDisabled = new Option(R.drawable.ic_clear_1, "disabled option", () -> {});
        optionDisabled.setDrawAsDisabled(true);
        op.add(optionDisabled);

        for(int i=0; i<10; i++) {
            op.add(new Option(R.drawable.ic_settings_1, "option " + (i*2 + 1), () -> {}));
            op.add(new Option(R.drawable.ic_info_1, "option " + (i*2 + 2), () -> {}));
        }

        new PopupChoose("Choose", "choose an option", op).show();
    }

    private void showPopupColorPicker() {
        new PopupColorPicker(colorPickerColor) {
            @Override
            public void onOK(int colorNew) { colorPickerColor = colorNew; }
        }.show();
    }

    private void showPopupCustom() {
        new PopupCustom("Custom Popup", "message") {
            @Override
            public void onOK() {}
        }.show();
    }

    private void showPopupImage() {
        new PopupImage("Image title", null, "fig.1") {
            @Override
            public void onCancel() {}
        }.show();
    }

    private void showPopupInput() {
        new PopupInput(R.string.type_something) {
            @Override
            public void onOK(String s) { showInfo(s); }
        }.show();
    }

    private void showPopupPleaseWait() {
        new PopupPleaseWait().show();
    }

    private void showPopupProgress() {
        PopupProgress popupProgress = new PopupProgress("Progress", "45.0 %");
        popupProgress.setProgress(0.45f);
        popupProgress.show();
    }

    private void showPopupSelectTimeInterval() {
        long durationMicros = 262000000L;
        new PopupSelectTimeInterval("Select interval", durationMicros) {
            @Override
            public void onOK(long startMicros, long endMicros) {
                String s = "interval selected:\n";
                s += millisToHHMMSSmmm(startMicros/1000) + " to ";
                s += millisToHHMMSSmmm(endMicros/1000);
                showInfo(s);
            }
        }.show();
    }

    private void showPopupSetPassword() {
        new PopupSetPassword() {
            @Override
            public void onOK(String s) { showInfo(getString(R.string.password) + " = " + s); }
        }.show();
    }

    private void showPopupEnterPassword() {
        new PopupEnterPassword() {
            @Override
            public void onOK(String s) { showInfo(getString(R.string.password) + " = " + s); }
        }.show();
    }

    private void showPopupRegister() {
        new PopupRegister() {
            @Override
            public void onOK(String username, String password, String repeatPassword) {

            }
        }.show();
    }

    private void showPopupLogin() {
        new PopupLogin() {
            @Override
            public void onOK(String username, String password) {

            }
        }.show();
    }

    private void showPopupAvailableOnlyInFullVersion() {
        new PopupAvailableOnlyInFullVersion(
                "Some premium option",
                "Super App Pro",
                "com.hardcodedjoy.superapppro",
                "appbase_demo").show();
    }

    private void showPopupSetTime() {
        new PopupSetTimeHHmmssmmm("Set time", timeMillis) {
            @Override
            public void onOK(long millis) { timeMillis = millis; }
        }.show();
    }
}