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

package com.hardcodedjoy.appbase.contentview;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.popup.Option;

import java.util.Vector;

@SuppressLint("ViewConstructor")
public class CvTM extends ContentView { // Content View with Title and Menu

    // class of cv to be displayed for settings,
    // to be set to app specific CvSettings using setSettingsCvClass()
    static protected Class<?> settingsCvClass;

    static protected Class<?> aboutCvClass;

    protected FrameLayout flMenuOptions; // menu together with 1dp border and scrollview
    protected LinearLayout llMenuOptions; // container for the menu options
    protected Vector<Option> menuOptions;

    protected float titleTextSizeDefault;

    protected CvTM() {
        menuOptions = new Vector<>();
        menuOptions.add(new Option(R.drawable.ic_info_1, R.string.title_about, () -> {
            try { ((ContentView)aboutCvClass.newInstance()).show();
            } catch (Exception e) { e.printStackTrace(System.err); }
        }));
        menuOptions.add(new Option(R.drawable.ic_settings_1, R.string.title_settings, () -> {
            try { ((ContentView)settingsCvClass.newInstance()).show();
            } catch (Exception e) { e.printStackTrace(System.err); }
        }));
    }

    protected boolean menuVisible() {
        return flMenuOptions.getVisibility() == View.VISIBLE;
    }

    protected void hideMenu() { flMenuOptions.setVisibility(View.GONE); }

    @SuppressLint("RtlHardcoded")
    protected void showMenu() {
        llMenuOptions.removeAllViews();

        FrameLayout flMenuOptionWithIcon;
        Button btnOption;
        ImageView ivOptionIcon;
        TextView tvOptionText;

        LinearLayout.LayoutParams params;

        for(Option option : menuOptions) {
            flMenuOptionWithIcon = (FrameLayout) inflate(getActivity(),
                    R.layout.appbase_menu_opt_ic, null);
            btnOption = flMenuOptionWithIcon.findViewById(R.id.appbase_btn_option);
            ivOptionIcon = flMenuOptionWithIcon.findViewById(R.id.appbase_iv_icon);
            tvOptionText = flMenuOptionWithIcon.findViewById(R.id.appbase_tv_text);

            btnOption.setOnClickListener(v -> {
                hideMenu();
                option.getExecutor().run();
            });

            if(option.getName() != null) {
                tvOptionText.setText(option.getName());
            } else if(option.getNameId() != 0) {
                tvOptionText.setText(option.getNameId());
            }

            // if option has icon -> set ivOptionIcon
            // else -> hide ivOptionIcon and remove text padding

            boolean withIcon = (option.getIconId() != 0)
                    | (option.getIconDrawable() != null)
                    | (option.getIconBitmap() != null);

            if(withIcon) {
                option.applyIconTo(ivOptionIcon);
            } else {
                ivOptionIcon.setVisibility(GONE);
                int paddingTop = tvOptionText.getPaddingTop();
                int paddingBottom = tvOptionText.getPaddingBottom();
                int paddingRight = tvOptionText.getPaddingRight();

                // remove additional left padding:
                tvOptionText.setPadding(paddingRight, paddingTop, paddingRight, paddingBottom);
            }

            if(option.isDrawAsDisabled()) {
                ImageView ivDisabledOverlay = new ImageView(getActivity());
                ivDisabledOverlay.setImageResource(R.drawable.menu_op_disabled_overlay);
                flMenuOptionWithIcon.addView(ivDisabledOverlay);
            }

            params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            llMenuOptions.addView(flMenuOptionWithIcon, params);
        }

        flMenuOptions.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onBackPressed() {
        if(menuVisible()) {
            hideMenu();
            return true; // consumed
        }
        return super.onBackPressed();
    }

    @SuppressWarnings("unused")
    public Vector<Option> getMenuOptions() { return menuOptions; }

    @SuppressWarnings("unused")
    public void setMenuOptions(Vector<Option> ops) { this.menuOptions = ops; }

    public void addMenuOptions(Vector<Option> ops, int index) { menuOptions.addAll(index, ops); }

    public void setTitle(String title) {
        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        tvTitle.setText(title);
    }

    public void setTitle(int titleResId) {
        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        tvTitle.setText(titleResId);
    }

    @SuppressWarnings("unused")
    public void setTitleTextSize(float sp) {
        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        tvTitle.setTextSize(sp);
    }

    @SuppressWarnings("unused")
    public void restoreTitleTextSize() {
        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        tvTitle.setTextSize(titleTextSizeDefault);
    }

    @SuppressWarnings("unused")
    public void setTitleIcon(int iconResId) {
        ImageView ivTitleIcon = findViewById(R.id.appbase_iv_title_icon);
        if(iconResId == -1) {
            ivTitleIcon.setVisibility(GONE);
            return;
        }

        // else:
        ivTitleIcon.setImageResource(iconResId);
        ivTitleIcon.setVisibility(VISIBLE);
    }

    static public void setSettingsCvClass(Class<?> c) { settingsCvClass = c; }
    static public void setAboutCvClass(Class<?> c) { aboutCvClass = c; }
}