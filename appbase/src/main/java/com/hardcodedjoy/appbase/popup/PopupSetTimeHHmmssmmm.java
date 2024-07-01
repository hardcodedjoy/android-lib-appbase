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

abstract public class PopupSetTimeHHmmssmmm extends Popup {

    static private final int[] digitLayoutIds = {
            R.id.appbase_ll_hh_10,
            R.id.appbase_ll_hh_1,
            R.id.appbase_ll_mm_10,
            R.id.appbase_ll_mm_1,
            R.id.appbase_ll_ss_10,
            R.id.appbase_ll_ss_1,
            R.id.appbase_ll_mmm_100,
            R.id.appbase_ll_mmm_10,
            R.id.appbase_ll_mmm_1};

    abstract public void onOK(long millis);

    @Override
    public void onCancel() { }

    public PopupSetTimeHHmmssmmm(String title, long millis) {

        // first inflate:
        inflater.inflate(R.layout.appbase_popup_set_time_hhmmssmmm, this);

        int mmm = (int) (millis % 1000);
        millis /= 1000;
        int ss = (int) (millis % 60);
        millis /= 60;
        int mm = (int) (millis % 60);
        millis /= 60;
        int hh = (int) (millis);

        for(int i = 0; i< digitLayoutIds.length; i++) {
            int index = i;
            int id = digitLayoutIds[index];
            LinearLayout ll = findViewById(id);
            Button btnPlus = (Button) ll.getChildAt(0);
            TextView tvDigit = (TextView) ll.getChildAt(1);
            Button btnMinus = (Button) ll.getChildAt(2);
            btnPlus.setOnClickListener(view -> increment(index));
            btnMinus.setOnClickListener(view -> decrement(index));

            if(id == R.id.appbase_ll_hh_10) { setDigit(tvDigit, hh/10); }
            if(id == R.id.appbase_ll_hh_1 ) { setDigit(tvDigit, hh%10); }

            if(id == R.id.appbase_ll_mm_10) { setDigit(tvDigit, mm/10); }
            if(id == R.id.appbase_ll_mm_1 ) { setDigit(tvDigit, mm%10); }

            if(id == R.id.appbase_ll_ss_10) { setDigit(tvDigit, ss/10); }
            if(id == R.id.appbase_ll_ss_1 ) { setDigit(tvDigit, ss%10); }

            if(id == R.id.appbase_ll_mmm_100) { setDigit(tvDigit, mmm/100); }
            if(id == R.id.appbase_ll_mmm_10) { setDigit(tvDigit, (mmm/10)%10); }
            if(id == R.id.appbase_ll_mmm_1 ) { setDigit(tvDigit, mmm%10); }
        }

        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        if(title != null) { tvTitle.setText(title); }

        ImageButton btnOk = findViewById(R.id.appbase_btn_ok);
        ImageButton btnCancel = findViewById(R.id.appbase_btn_cancel);
        btnOk.setOnClickListener(ocl);
        btnCancel.setOnClickListener(ocl);
    }

    @Override
    void oclOnClick(View view) {
        ContentView.removePopUp(this); // dismiss
        int id = view.getId();
        if(id == R.id.appbase_btn_ok || id == R.id.appbase_btn_ok_text) {
            long millis = getDigit(R.id.appbase_ll_hh_10);
            millis = millis * 10 + getDigit(R.id.appbase_ll_hh_1);

            millis = millis * 6 + getDigit(R.id.appbase_ll_mm_10);
            millis = millis * 10 + getDigit(R.id.appbase_ll_mm_1);

            millis = millis * 6 + getDigit(R.id.appbase_ll_ss_10);
            millis = millis * 10 + getDigit(R.id.appbase_ll_ss_1);

            millis = millis * 10 + getDigit(R.id.appbase_ll_mmm_100);
            millis = millis * 10 + getDigit(R.id.appbase_ll_mmm_10);
            millis = millis * 10 + getDigit(R.id.appbase_ll_mmm_1);

            onOK(millis);
        } else if(id == R.id.appbase_btn_cancel || id == R.id.appbase_btn_cancel_text) {
            onCancel();
        } else if(id == R.id.appbase_ll_outside_popup) {
            onCancel();
        }
    }

    private void increment(int digitLayoutIdIndex) {
        try {
            int id = digitLayoutIds[digitLayoutIdIndex];
            LinearLayout ll = findViewById(id);
            TextView tvDigit = (TextView) ll.getChildAt(1);
            String s = tvDigit.getText().toString();
            int a = Integer.parseInt(s);
            a++;
            boolean overflow = (a == 10);
            if(a == 6) {
                if(id == R.id.appbase_ll_mm_10) { overflow = true; }
                if(id == R.id.appbase_ll_ss_10) { overflow = true; }
            }
            if(overflow) {
                a = 0;
                // increment next:
                digitLayoutIdIndex--; // next digit has previous index
                if(digitLayoutIdIndex >= 0) {
                    increment(digitLayoutIdIndex);
                }
            }
            s = "" + a;
            tvDigit.setText(s);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private void decrement(int digitLayoutIdIndex) {
        try {
            int id = digitLayoutIds[digitLayoutIdIndex];
            LinearLayout ll = findViewById(id);
            TextView tvDigit = (TextView) ll.getChildAt(1);
            String s = tvDigit.getText().toString();
            int a = Integer.parseInt(s);
            a--;
            boolean underflow = (a == -1);

            if(underflow) {
                a = 9;
                if(id == R.id.appbase_ll_mm_10) { a = 5; }
                if(id == R.id.appbase_ll_ss_10) { a = 5; }

                // decrement next:
                digitLayoutIdIndex--; // next digit has previous index
                if(digitLayoutIdIndex >= 0) {
                    decrement(digitLayoutIdIndex);
                }
            }
            s = "" + a;
            tvDigit.setText(s);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private void setDigit(TextView tvDigit, int digit) {
        String s = "" + digit;
        tvDigit.setText(s);
    }

    private int getDigit(int digitLayoutId) {
        try {
            LinearLayout ll = findViewById(digitLayoutId);
            TextView tvDigit = (TextView) ll.getChildAt(1);
            return Integer.parseInt(tvDigit.getText().toString());
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return 0;
    }
}