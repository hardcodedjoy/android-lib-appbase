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

package com.hardcodedjoy.appbase.gui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
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
import android.widget.ToggleButton;

import com.hardcodedjoy.appbase.ImageUtil;

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

    static public void link(ToggleButton tb, final SetGetter setGetter) {
        try {
            boolean b = Boolean.parseBoolean(setGetter.get());
            tb.setChecked(b);
            setGetter.set("" + b);
            tb.setOnCheckedChangeListener((compoundButton, b1) -> setGetter.set("" + b1));
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
             if(view instanceof EditText)     { link((EditText)     view, setGetter); }
        else if(view instanceof CheckBox)     { link((CheckBox)     view, setGetter); }
        else if(view instanceof ToggleButton) { link((ToggleButton) view, setGetter); }
        else if(view instanceof SeekBar)      { link((SeekBar)      view, setGetter); }
        else if(view instanceof RadioButton)  { link((RadioButton)  view, setGetter); }
        else if(view instanceof RadioGroup)   { link((RadioGroup)   view, setGetter); }
    }

    static public void setHandleImage(ToggleButton tb, Drawable drawable) {
        setHandleImage(tb, ImageUtil.drawableToBitmap(drawable));
    }

    @SuppressLint("ClickableViewAccessibility")
    static public void setHandleImage(ToggleButton tb, Bitmap bitmap) {

        tb.post(() -> {
            float tbWidth = tb.getWidth();
            float tbHeight = tb.getHeight();

            // handle:
            float handleWidth = tbWidth - DisplayUnit.dpToPx(30.0f); // 27 + 3
            float handleHeight = tbHeight - DisplayUnit.dpToPx(6.0f); // 3 + 3

            float bmpWidth = bitmap.getWidth();
            float bmpHeight = bitmap.getHeight();

            float scaleFactor = Math.min(handleWidth / bmpWidth, handleHeight / bmpHeight);

            int scaledW = (int)(bmpWidth * scaleFactor + 0.5f);
            int scaledH = (int)(bmpHeight * scaleFactor + 0.5f);

            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledW, scaledH, true);
            Drawable scaledDrawable = ImageUtil.bitmapToDrawable(scaledBitmap);

            {
                int inset;
                if(tb.isChecked()) {
                    inset = DisplayUnit.dpToPx(27.0f) + (int)((handleWidth - scaledW) / 2 + 0.5f);
                } else {
                    inset = DisplayUnit.dpToPx(3.0f) + (int)((handleWidth - scaledW) / 2 + 0.5f);
                }
                tb.setButtonDrawable(new InsetDrawable(scaledDrawable,
                        inset, 0, 0, 0));
            }

            tb.setOnTouchListener((v, event) -> {
                tb.postDelayed(() -> {
                    int inset;
                    if(tb.isPressed()) {
                        if (tb.isChecked()) {
                            inset = DisplayUnit.dpToPx(22.0f) + (int) ((handleWidth - scaledW) / 2 + 0.5f);
                        } else {
                            inset = DisplayUnit.dpToPx(8.0f) + (int) ((handleWidth - scaledW) / 2 + 0.5f);
                        }
                    } else {
                        if (tb.isChecked()) {
                            inset = DisplayUnit.dpToPx(27.0f) + (int) ((handleWidth - scaledW) / 2 + 0.5f);
                        } else {
                            inset = DisplayUnit.dpToPx(3.0f) + (int) ((handleWidth - scaledW) / 2 + 0.5f);
                        }
                    }

                    tb.setButtonDrawable(new InsetDrawable(scaledDrawable,
                            inset, 0, 0, 0));
                }, 100);

                return false;
            });
        });
    }
}