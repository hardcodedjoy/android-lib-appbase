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

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.contentview.ContentView;
import com.hardcodedjoy.appbase.popup.Option;

import java.util.Vector;

abstract public class DropDownMenu {

    private final Vector<Option> options;

    abstract public void hideMenu();

    public DropDownMenu(Vector<Option> options) {
        this.options = options;
    }

    public void inflate(LinearLayout llDestination) {
        FrameLayout flMenuOptionWithIcon;
        Button btnOption;
        ImageView ivOptionIcon;
        TextView tvOptionText;

        LinearLayout.LayoutParams params;

        for(Option option : options) {
            flMenuOptionWithIcon = (FrameLayout) ContentView.inflate(ContentView.getActivity(),
                    R.layout.appbase_menu_opt_ic, null);
            btnOption = flMenuOptionWithIcon.findViewById(R.id.appbase_btn_option);
            ivOptionIcon = flMenuOptionWithIcon.findViewById(R.id.appbase_iv_icon);
            tvOptionText = flMenuOptionWithIcon.findViewById(R.id.appbase_tv_text);

            btnOption.setOnClickListener(v -> {
                hideMenu();
                Runnable executor = option.getExecutor();
                if(executor == null) {
                    ContentView.showError(R.string.err_unimplemented);
                } else {
                    executor.run();
                }
            });

            if(option.getName() != null) {
                tvOptionText.setText(option.getName());
                btnOption.setContentDescription(tvOptionText.getText());
            } else if(option.getNameId() != 0) {
                tvOptionText.setText(option.getNameId());
                btnOption.setContentDescription(tvOptionText.getText());
            }

            // if option has icon -> set ivOptionIcon
            // else -> hide ivOptionIcon and remove text padding

            boolean withIcon = (option.getIconId() != 0)
                    | (option.getIconDrawable() != null)
                    | (option.getIconBitmap() != null);

            if(withIcon) {
                option.applyIconTo(ivOptionIcon);
            } else {
                ivOptionIcon.setVisibility(View.GONE);
                int paddingTop = tvOptionText.getPaddingTop();
                int paddingBottom = tvOptionText.getPaddingBottom();
                int paddingRight = tvOptionText.getPaddingRight();

                // remove additional left padding:
                tvOptionText.setPadding(paddingRight, paddingTop, paddingRight, paddingBottom);
            }

            if(option.isDrawAsDisabled()) {
                ImageView ivDisabledOverlay = new ImageView(ContentView.getActivity());
                ivDisabledOverlay.setImageResource(R.drawable.menu_op_disabled_overlay);
                flMenuOptionWithIcon.addView(ivDisabledOverlay);
            }

            params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            llDestination.addView(flMenuOptionWithIcon, params);
        }
    }
}