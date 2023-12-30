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

package com.hardcodedjoy.appbase.popup;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.contentview.ContentView;

import java.util.Vector;

@SuppressLint("ViewConstructor")
public class PopupChoose extends Popup {

    private final LinearLayout llOptions;
    private final ImageButton btnAdd;
    private final ImageButton btnRefresh;

    private final Vector<Option> options;


    @Override
    public void onCancel() {}

    public void onRefresh() {}
    public void onOther() {}
    public void onAfterOptionExecuted() {}

    public PopupChoose(String title, String message, Vector<Option> options) {

        this.options = options;

        // first inflate:
        inflater.inflate(R.layout.appbase_popup_chooser_input, this);

        final TextView tvTitle = findViewById(R.id.tv_title);
        final TextView tvMessage = findViewById(R.id.tv_message);
        llOptions = findViewById(R.id.ll_options);
        btnAdd = findViewById(R.id.btn_add);
        btnRefresh = findViewById(R.id.btn_refresh);

        tvTitle.setText(title);
        if(message == null) { tvMessage.setVisibility(GONE); }
        else { tvMessage.setVisibility(VISIBLE); tvMessage.setText(message); }

        llOptions.removeAllViews();

        View vOption;
        Button button;
        int n = options.size();
        Option option;
        int iconSize;
        int iconTint;
        LinearLayout.LayoutParams params;

        for(int i=0; i<n; i++) {
            option = options.elementAt(i);
            inflater.inflate(R.layout.appbase_popup_ci_opt_ic, llOptions);
            vOption = llOptions.getChildAt(i);
            button = vOption.findViewById(R.id.btn_option);
            button.setId(i);
            TextView tvText = vOption.findViewById(R.id.tv_text);
            if(option.getName() != null) {
                tvText.setText(option.getName());
            } else if(option.getNameId() != 0) {
                tvText.setText(option.getNameId());
            }
            smallerLongLines(vOption);
            ImageView ivIcon = vOption.findViewById(R.id.iv_icon);

            if(option.getIconBitmap() != null) {
                ivIcon.setImageBitmap(option.getIconBitmap());
            } else if(option.getIconId() != 0) {
                if(option.getIconTintColor() == 0) { // not set
                    // set it to text color:
                    int color = ContentView.getThemeColor(android.R.attr.textColor);
                    option.setIconTintColor(color);
                }
                ivIcon.setImageResource(option.getIconId());
            } else if(option.getIconDrawable() != null) {
                ivIcon.setImageDrawable(option.getIconDrawable());
            }

            iconSize = option.getIconSize();
            if(iconSize != 0) { // is set
                params = new LinearLayout.LayoutParams(iconSize, iconSize);
                ivIcon.setLayoutParams(params);
            }

            if(android.os.Build.VERSION.SDK_INT > 21) {
                iconTint = option.getIconTintColor();
                if(iconTint != 0) { // is set
                    ivIcon.setImageTintList(ColorStateList.valueOf(iconTint));
                    ivIcon.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
                }
            }

            button.setOnClickListener(ocl);
        }

        btnAdd.setOnClickListener(ocl);
        btnAdd.setVisibility(GONE);
        btnRefresh.setOnClickListener(ocl);
        btnRefresh.setVisibility(GONE);
        setOnClickListener(ocl);
    }

    @Override
    void oclOnClick(View view) {

        if(view.getParent().getParent() == llOptions) { // option clicked
            int i = view.getId();
            ContentView.removePopUp(PopupChoose.this); // dismiss
            options.elementAt(i).run();
            onAfterOptionExecuted();
            return;
        }

        int id = view.getId();
        if(id == R.id.btn_add) {
            ContentView.removePopUp(PopupChoose.this); // dismiss
            onOther();
        } else if(id == R.id.btn_refresh) {
            onRefresh();
        } else if(id == R.id.ll_outside_popup) {
            onClickOutside();
        }
    }

    public PopupChoose showBtnAdd() {
        btnAdd.setVisibility(VISIBLE);
        return this;
    }

    public PopupChoose showBtnRefresh() {
        btnRefresh.setVisibility(VISIBLE);
        return this;
    }

    static private void smallerLongLines(View vOption) {

        TextView tvText = vOption.findViewById(R.id.tv_text);

        String text = tvText.getText().toString();

        int a = text.indexOf('\n');
        if(a == -1) { return; }

        String text1 = text.substring(0, a);
        String text2 = text.substring(a+1);
        tvText.setText(text1);
        TextView tvMessage2 = new TextView(vOption.getContext());
        tvMessage2.setText(text2);
        //if(text2.length() > 20) {
            tvMessage2.setTextSize(15);
        //}
        ViewGroup vg = (ViewGroup)tvText.getParent();
        vg.addView(tvMessage2);
        vg.postInvalidate();
    }
}