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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.contentview.ContentView;
import com.hardcodedjoy.appbase.gui.ThemeUtil;

import java.util.Vector;

@SuppressLint("ViewConstructor")
public class PopupChoose extends Popup {

    private final LinearLayout llOptions;
    private final ImageButton btnAdd;
    private final ImageButton btnRefresh;

    private final Vector<Option> options;

    private final int colorSelected;

    @Override
    public void onCancel() {}

    public void onRefresh() {}
    public void onOther() {}
    public void onAfterOptionExecuted() {}

    public PopupChoose(String title, String message, Vector<Option> options) {

        this.options = options;

        // first inflate:
        inflater.inflate(R.layout.appbase_popup_chooser_input, this);

        final TextView tvTitle = findViewById(R.id.appbase_tv_title);
        final TextView tvMessage = findViewById(R.id.appbase_tv_message);
        llOptions = findViewById(R.id.appbase_ll_options);
        final LinearLayout llOptionsOverlays = findViewById(R.id.appbase_ll_options_overlays);
        btnAdd = findViewById(R.id.appbase_btn_add);
        btnRefresh = findViewById(R.id.appbase_btn_refresh);

        Activity a = ContentView.getActivity();
        colorSelected = ThemeUtil.getColor(a, android.R.attr.textColorHighlight);

        tvTitle.setText(title);
        if(message == null) { tvMessage.setVisibility(GONE); }
        else { tvMessage.setVisibility(VISIBLE); tvMessage.setText(message); }

        llOptions.removeAllViews();
        llOptionsOverlays.removeAllViews();

        addOptions(llOptions, llOptionsOverlays, options);

        btnAdd.setOnClickListener(ocl);
        btnAdd.setVisibility(GONE);
        btnRefresh.setOnClickListener(ocl);
        btnRefresh.setVisibility(GONE);
        setOnClickListener(ocl);
    }

    private RadioButton getNewRadioButton(Option option) {
        Context context = llOptions.getContext();
        RadioButton rb = new RadioButton(context);
        rb.setText(option.getName());

        if(option.isSelected()) { rb.setChecked(true); }

        rb.setOnClickListener(view -> {
            if(!rb.isChecked()) { return; }
            rb.postDelayed(() -> {
                ContentView.removePopUp(PopupChoose.this); // dismiss
                option.run();
                onAfterOptionExecuted();
            }, 250);
        });

        return rb;
    }

    private void addOptions(LinearLayout llOptions,
                            LinearLayout llOptionsOverlays,
                            Vector<Option> options) {

        if(options.isEmpty()) { return; }

        Option firstOption = options.firstElement();
        boolean withIcon = (firstOption.getIconId() != 0)
                | (firstOption.getIconDrawable() != null)
                | (firstOption.getIconBitmap() != null);

        if(!withIcon) {
            Context context = llOptions.getContext();
            RadioGroup rg = new RadioGroup(context);
            for(Option option : options) {
                rg.addView(getNewRadioButton(option));

                // overlay
                // option disabled -> semi-transparent colorBG
                // option enabled -> transparent (invisible) overlay, but still hold the space
                {
                    ImageView ivOverlay = new ImageView(context);
                    if(option.isDrawAsDisabled()) {
                        ivOverlay.setImageResource(R.drawable.chooser_op_disabled_overlay);
                    }
                    LinearLayout.LayoutParams params;
                    params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    params.weight = 1.0f;
                    llOptionsOverlays.addView(ivOverlay, params);
                }
            }
            llOptions.addView(rg);
            return;
        }

        // else -> with icon:

        View vOption;
        Button button;
        Option option;

        int n = options.size();

        for(int i=0; i<n; i++) {
            option = options.elementAt(i);

            inflater.inflate(R.layout.appbase_popup_ci_opt_ic, llOptions);

            vOption = llOptions.getChildAt(i);
            button = vOption.findViewById(R.id.appbase_btn_option);
            button.setId(i);
            TextView tvText = vOption.findViewById(R.id.appbase_tv_text);
            if(option.getName() != null) {
                tvText.setText(option.getName());
            } else if(option.getNameId() != 0) {
                tvText.setText(option.getNameId());
            }
            smallerLongLines(vOption);
            ImageView ivIcon = vOption.findViewById(R.id.appbase_iv_icon);
            option.setIconTintColor(tvText.getTextColors().getDefaultColor());
            option.applyIconTo(ivIcon);
            button.setOnClickListener(ocl);

            if(option.isSelected()) { button.setBackgroundColor(colorSelected); }

            if(option.isDrawAsDisabled()) {
                ImageView ivDisabledOverlay = new ImageView(vOption.getContext());
                ivDisabledOverlay.setImageResource(R.drawable.chooser_op_disabled_overlay);
                ((FrameLayout)vOption).addView(ivDisabledOverlay);
            }
        }
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
        if(id == R.id.appbase_btn_add) {
            ContentView.removePopUp(PopupChoose.this); // dismiss
            onOther();
        } else if(id == R.id.appbase_btn_refresh) {
            onRefresh();
        } else if(id == R.id.appbase_ll_outside_popup) {
            ContentView.removePopUp(PopupChoose.this); // dismiss
            onCancel();
        }
    }

    @SuppressWarnings("unused")
    public PopupChoose showBtnAdd() {
        btnAdd.setVisibility(VISIBLE);
        return this;
    }

    @SuppressWarnings("unused")
    public PopupChoose showBtnRefresh() {
        btnRefresh.setVisibility(VISIBLE);
        return this;
    }

    static private void smallerLongLines(View vOption) {

        TextView tvText = vOption.findViewById(R.id.appbase_tv_text);

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