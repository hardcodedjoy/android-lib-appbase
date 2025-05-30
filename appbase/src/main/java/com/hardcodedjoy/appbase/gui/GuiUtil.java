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

package com.hardcodedjoy.appbase.gui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hardcodedjoy.appbase.ImageUtil;
import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.contentview.ContentView;

public class GuiUtil {

    static public RectF getRect(View view) {
        float x = view.getX();
        float y = view.getY();
        int w = view.getWidth();
        int h = view.getHeight();
        return new RectF(x, y, x+w, y+h);
    }

    static public void setXYWH(View view, float x, float y, float w, float h) {
        ViewGroup.LayoutParams params;
        ViewGroup parent = (ViewGroup) view.getParent();

        if (parent == null) {
            params = new ViewGroup.LayoutParams((int) w, (int) h);
        } else if (parent instanceof LinearLayout) {
            params = new LinearLayout.LayoutParams((int) w, (int) h);
        } else if (parent instanceof RelativeLayout) {
            params = new RelativeLayout.LayoutParams((int) w, (int) h);
        } else if (parent instanceof FrameLayout) {
            params = new FrameLayout.LayoutParams((int) w, (int) h);
        } else {
            params = new ViewGroup.LayoutParams((int) w, (int) h);
        }

        view.setLayoutParams(params);
        view.setX(x);
        view.setY(y);
    }

    static public void setRect(View view, RectF rect) {
        ViewGroup.LayoutParams params;
        params = new ViewGroup.LayoutParams((int)(rect.right - rect.left), (int)(rect.bottom - rect.top));
        view.setLayoutParams(params);
        view.setX(rect.left);
        view.setY(rect.top);
    }

    static public void setColorOnImageView(View view, int color) {
        view.post(() -> {
            int w = view.getWidth() - (view.getPaddingLeft() + view.getPaddingRight());
            int h = view.getHeight() - (view.getPaddingTop() + view.getPaddingBottom());
            if(w < 1 || h < 1) { return; }
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(color);
            if(view instanceof ImageView) { // ImageButton is also an ImageView
                ((ImageView)view).setImageBitmap(bitmap);
            }
        });
    }

    static public void setGlowInside(ViewGroup vg, boolean glow, boolean locked) {

        Activity a = ContentView.getActivity();
        int color = ThemeUtil.getColor(a, android.R.attr.colorBackground);
        if(glow) { color = ThemeUtil.getColor(a, android.R.attr.colorFocusedHighlight); }
        if(locked) { color = ThemeUtil.getColor(a, R.attr.appBaseColorBackgroundA60); }

        int n = vg.getChildCount();
        for(int i = 0; i < n; ++i) {
            View v = vg.getChildAt(i);
            if (v instanceof ViewGroup) {
                setGlowInside((ViewGroup)v, glow, locked);
            } else if (v instanceof TextView) {
                ((TextView) v).setTextColor(color);
                String text = ((TextView) v).getText().toString();
                SpannableString spannableString = new SpannableString(text);
                StyleSpan styleSpan = new StyleSpan(Typeface.NORMAL);
                if(glow) { styleSpan = new StyleSpan(Typeface.BOLD); }
                spannableString.setSpan(styleSpan, 0, text.length(), 0);
                ((TextView) v).setText(spannableString);
            } else if (v instanceof ImageView) {
                ImageUtil.setTint((ImageView) v, color);
                ((ImageView) v).setImageTintMode(PorterDuff.Mode.SRC_IN);
            }
        }
    }

    static public void disable(Activity activity, EditText et, View.OnClickListener ocl) {
        et.setFocusable(false);
        et.setFocusableInTouchMode(false);
        et.setBackgroundResource(com.hardcodedjoy.appbase.R.drawable.et_disabled);
        int colorText = ThemeUtil.getColor(activity, R.attr.appBaseColorTextDisabled);
        et.setTextColor(colorText);
        et.setOnClickListener(ocl);
    }

    static public void disable(Activity activity, CheckBox cb, View.OnClickListener ocl) {
        cb.setFocusable(false);
        cb.setFocusableInTouchMode(false);
        // cb.setBackgroundResource(com.hardcodedjoy.appbase.R.drawable.cb_disabled);
        int colorText = ThemeUtil.getColor(activity, R.attr.appBaseColorTextDisabled);
        cb.setTextColor(colorText);
        cb.setOnClickListener(view -> {
            cb.setChecked(false);
            ocl.onClick(view);
        });
    }

    static public void disableDDL(Activity activity, LinearLayout llDropDown, View.OnClickListener ocl) {
        EditText et = llDropDown.findViewById(R.id.appbase_et_drop_down);
        ImageButton btn = llDropDown.findViewById(R.id.appbase_btn_drop_down_expand);
        int colorText = ThemeUtil.getColor(activity, R.attr.appBaseColorTextDisabled);

        et.setFocusable(false);
        et.setFocusableInTouchMode(false);
        et.setBackgroundResource(R.drawable.et_disabled);
        et.setTextColor(colorText);
        et.setOnClickListener(ocl);

        btn.setFocusable(false);
        btn.setFocusableInTouchMode(false);

        int leftPadding   = btn.getPaddingLeft();
        int topPadding    = btn.getPaddingTop();
        int rightPadding  = btn.getPaddingRight();
        int bottomPadding = btn.getPaddingBottom();

        btn.setBackgroundResource(R.drawable.btn_1_disabled);
        btn.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);

        ImageUtil.setTint(btn, colorText);
        btn.setOnClickListener(ocl);
    }

    static public void replaceView(View what, View with) {
        if(what == null) { return; }
        if(with == null) { return; }
        ViewParent whatParent = what.getParent();
        if(whatParent == null) { return; }
        if(!(whatParent instanceof ViewGroup)) { return; }

        ViewParent withParent = with.getParent();
        if(withParent != null) {
            if(!(withParent instanceof ViewGroup)) { return; }
            ((ViewGroup) withParent).removeView(with);
        }

        ViewGroup.LayoutParams params = what.getLayoutParams();
        if(params != null) { with.setLayoutParams(params); }

        int index = ((ViewGroup) whatParent).indexOfChild(what);
        ((ViewGroup) whatParent).removeView(what);
        ((ViewGroup) whatParent).addView(with, index);
    }

    static public void setMargins(View view, int left, int top, int right, int bottom) {
        if(view == null) { return; }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) { return; }
        if(params instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) params).setMargins(left, top, right, bottom);
        } else if(params instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) params).setMargins(left, top, right, bottom);
        } else if(params instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) params).setMargins(left, top, right, bottom);
        }
        view.setLayoutParams(params);
    }
}