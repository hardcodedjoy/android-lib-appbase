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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hardcodedjoy.appbase.ImageUtil;
import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.StringUtil;
import com.hardcodedjoy.appbase.contentview.ContentView;
import com.hardcodedjoy.appbase.popup.Option;
import com.hardcodedjoy.appbase.popup.PopupChoose;
import com.hardcodedjoy.appbase.popup.PopupColorPicker;
import com.hardcodedjoy.appbase.setgetters.IntSetGetter;
import com.hardcodedjoy.appbase.setgetters.SetGetter;

import java.util.Vector;

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

    static public void setTextColorToAllTextsAndButtons(ViewGroup vg, int color) {
        int n = vg.getChildCount();
        View v;
        for(int i=0; i<n; i++) {
            v = vg.getChildAt(i);
            if(v instanceof ViewGroup) { setTextColorToAllTextsAndButtons((ViewGroup)v, color); }
            else if(v instanceof TextView) { ((TextView) v).setTextColor(color); }
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
    static public void link(View layout, int resId, SetGetter setGetter) {
        View view = layout.findViewById(resId);
             if(view instanceof EditText)     { link((EditText)     view, setGetter); }
        else if(view instanceof CheckBox)     { link((CheckBox)     view, setGetter); }
        else if(view instanceof ToggleButton) { link((ToggleButton) view, setGetter); }
        else if(view instanceof SeekBar)      { link((SeekBar)      view, setGetter); }
        else if(view instanceof RadioButton)  { link((RadioButton)  view, setGetter); }
        else if(view instanceof RadioGroup)   { link((RadioGroup)   view, setGetter); }
    }

    static public void linkDropDownList(LinearLayout llDropDown, String title,
                String[] optionsKeys, String[] optionsValues, SetGetter setGetter) {

        EditText et = llDropDown.findViewById(R.id.appbase_et_drop_down);
        ImageButton btn = llDropDown.findViewById(R.id.appbase_btn_drop_down_expand);

        // first time (init)
        String key = setGetter.get();
        String value = StringUtil.findValueForKey(key, optionsKeys, optionsValues);
        if(value != null) { et.setText(value); }

        View.OnClickListener ocl = view -> {
            Vector<Option> op = new Vector<>();

            for(int i=0; i<optionsKeys.length; i++) {
                final int index = i;
                Option option = new Option(optionsValues[i], () -> {
                    et.setText(optionsValues[index]);
                    setGetter.set(optionsKeys[index]);
                });
                if(optionsKeys[i].equals(setGetter.get())) { option.setSelected(); }
                op.add(option);
            }

            showPopupChoose(title, op);
        };

        et.setFocusable(false);
        et.setFocusableInTouchMode(false);
        et.setOnClickListener(ocl);
        btn.setOnClickListener(ocl);
    }



    static public void linkColorField(LinearLayout llColor,
                                      String colorPickerTitle,
                                      IntSetGetter intSetGetter) {
        EditText et = null;
        ImageButton btn = null;
        for(int i=0; i<llColor.getChildCount(); i++) {
            View view = llColor.getChildAt(i);
            if(view instanceof EditText) { et = (EditText) view; }
            else if(view instanceof ImageButton) { btn = (ImageButton) view; }
        }
        final EditText editText = et;
        final ImageButton button = btn;

        if(editText != null) {
            GuiLinker.link(editText, new SetGetter() {
                @Override
                public void set(String colorHex) {
                    int color = 0;
                    try {
                        //noinspection PointlessBitwiseExpression
                        color = (int)(Long.parseLong(colorHex, 16) & 0xFFFFFFFF);
                    } catch (Exception e) { e.printStackTrace(System.err); }

                    if(button != null) { GuiUtil.setColorOnImageView(button, color); }
                    intSetGetter.set(color);
                }
                @Override
                public String get() {
                    int color = intSetGetter.get();
                    return String.format("%08X", color);
                }
            });
        }

        if(button != null) {
            PopupColorPicker colorPicker = new PopupColorPicker(
                    colorPickerTitle, null, intSetGetter.get()) {
                @Override
                public void onOK(int colorNew) {
                    intSetGetter.set(colorNew);
                    editText.setText(String.format("%08X", colorNew));
                    GuiUtil.setColorOnImageView(button, colorNew);
                }
            };
            button.setOnClickListener(view -> colorPicker.show());
        }
    }

    static public void linkColorField(LinearLayout llColor,
                                      int colorPickerTitleResId,
                                      IntSetGetter intSetGetter) {
        linkColorField(llColor, ContentView.getString(colorPickerTitleResId), intSetGetter);
    }


    @SuppressWarnings("unused")
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

    static private void showPopupChoose(String title, Vector<Option> op) {
        PopupChoose popupChoose = new PopupChoose(title, null, op);
        popupChoose.enableDismissByOutsideClick();

        // API 21 text color fix:
        int textColor = ThemeUtil.getColor(ContentView.getActivity(),
                android.R.attr.colorForeground);
        setTextColorToAllTextsAndButtons(popupChoose, textColor);

        popupChoose.show();
    }
}