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

package com.hardcodedjoy.appbase.gui;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

public class GuiLinker {

    @SuppressWarnings("unused")
    static public void setOnClickListenerToAllButtons(ViewGroup vg, View.OnClickListener ocl) {
        int n = vg.getChildCount();
        View v;
        for(int i=0; i<n; i++) {
            v = vg.getChildAt(i);
            if(v instanceof ViewGroup) { setOnClickListenerToAllButtons((ViewGroup)v, ocl); }
            else if(v instanceof Button)      { v.setOnClickListener(ocl); }
            else if(v instanceof ImageButton) { v.setOnClickListener(ocl); }
        }
    }

    static public void link(EditText et, final SetGetter setGetter) {

        // first time (init)
        // get value from settings and set on et
        String value = setGetter.get();
        if(value != null) {
            et.setText(value);
        }
        setGetter.set(value);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable e) { setGetter.set(e.toString()); }
        });

    }

    static public void link(CheckBox cb, final SetGetter setGetter) {
        try {
            boolean b = Boolean.parseBoolean(setGetter.get());
            cb.setChecked(b);
            setGetter.set("" + b);
            cb.setOnCheckedChangeListener((compoundButton, b1) -> setGetter.set("" + b1));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    static public void link(SeekBar sb, final SetGetter setGetter) {
        try {
            int val = Integer.parseInt(setGetter.get());
            sb.setProgress(val);
            setGetter.set("" + val);
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    setGetter.set("" + progress);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    static public void link(RadioButton rb, final SetGetter setGetter) {
        try {
            boolean b = Boolean.parseBoolean(setGetter.get());
            rb.setChecked(b);
            setGetter.set("" + b);
            rb.setOnCheckedChangeListener((compoundButton, b1) -> setGetter.set("" + b1));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    static public void link(RadioGroup rg, final SetGetter setGetter) {

        String current = setGetter.get();

        int n = rg.getChildCount();
        RadioButton rb;
        for(int i=0; i<n; i++) {
            rb = (RadioButton) rg.getChildAt(i);
            if(rb.getText().toString().equals(current)) {
                rb.setChecked(true);
                break;
            }
        }

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb1 = group.findViewById(checkedId);
            setGetter.set(rb1.getText().toString());
        });
    }


    @SuppressWarnings("unused")
    static public void link(View layout, int resId, final SetGetter setGetter) {
        View view = layout.findViewById(resId);
             if(view instanceof EditText)    { link((EditText)    view, setGetter); }
        else if(view instanceof CheckBox)    { link((CheckBox)    view, setGetter); }
        else if(view instanceof SeekBar)     { link((SeekBar)     view, setGetter); }
        else if(view instanceof RadioButton) { link((RadioButton) view, setGetter); }
        else if(view instanceof RadioGroup)  { link((RadioGroup)  view, setGetter); }
    }
}