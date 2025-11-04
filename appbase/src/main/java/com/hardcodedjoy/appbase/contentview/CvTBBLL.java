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

package com.hardcodedjoy.appbase.contentview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardcodedjoy.appbase.ImageUtil;
import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.gui.DisplayUnit;
import com.hardcodedjoy.appbase.gui.DropDownMenu;
import com.hardcodedjoy.appbase.gui.LinearLayoutHorizontal;
import com.hardcodedjoy.appbase.gui.ThemeUtil;
import com.hardcodedjoy.appbase.popup.Option;

import java.util.Vector;

@SuppressLint("ViewConstructor")
public class CvTBBLL extends ContentView { // Content View with Title, Button Bar and LL

    private final float titleTextSizeDefault;
    private final HorizontalScrollView hsvButtonBar;
    private final LinearLayout llButtonBar;
    private final FrameLayout flMenuOptions; // menu together with 1dp border and scrollview
    private final LinearLayout llMenuOptions; // container for the menu options
    private final LinearLayout llDropDownMenu; // for adjusting x position
    private Button btnPreviouslyClicked;

    @SuppressLint("ClickableViewAccessibility")
    public CvTBBLL() {
        inflate(R.layout.appbase_cv_tbbll);
        setTitleIcon(-1);
        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        titleTextSizeDefault = DisplayUnit.pxToSp((int)tvTitle.getTextSize());

        hsvButtonBar = findViewById(R.id.appbase_hsv_button_bar);
        llButtonBar = findViewById(R.id.appbase_ll_button_bar);
        llButtonBar.removeAllViews();

        flMenuOptions = findViewById(R.id.appbase_fl_menu_options);
        llMenuOptions = flMenuOptions.findViewById(R.id.appbase_ll_menu_options);
        llDropDownMenu = flMenuOptions.findViewById(R.id.appbase_ll_dropdown_menu);

        hideMenu();

        // hide menu on outside touch:
        flMenuOptions.setOnTouchListener((v, event) -> {
            if(menuVisible()) {
                hideMenu();
                return true;
            }
            return false;
        });


    }

    private boolean menuVisible() { return flMenuOptions.getVisibility() == View.VISIBLE; }

    private void hideMenu() { flMenuOptions.setVisibility(View.GONE); }

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

    public void addButton(int btnTextStringId, Vector<Option> options) {
        String btnText = getString(btnTextStringId);
        addButton(btnText, options);
    }

    public void addButton(String btnText, Vector<Option> options) {
        Button button = new Button(getActivity());
        button.setText(btnText);
        button.setOnClickListener(view -> {

            if(menuVisible()) {
                if(button == btnPreviouslyClicked) {
                    hideMenu();
                    return;
                }
            }
            btnPreviouslyClicked = button;

            // GONE = does not keep width, height
            // INVISIBLE = keeps width, height
            flMenuOptions.setVisibility(View.INVISIBLE);

            llMenuOptions.removeAllViews();
            new DropDownMenu(options) {
                @Override
                public void hideMenu() { CvTBBLL.this.hideMenu(); }
            }.inflate(llMenuOptions);

            hsvButtonBar.post(() -> {
                // menu x position same as button x position
                float xMax = hsvButtonBar.getWidth() - llDropDownMenu.getWidth();
                float x = view.getX() - hsvButtonBar.getScrollX();

                int v1dp = DisplayUnit.dpToPx(1);

                // fit to the left:
                if(x <= 0) {
                    x = 0;
                    llDropDownMenu.setPadding(0, 0, v1dp, v1dp);
                }

                // if menu too wide -> fit to the right
                else if(x > xMax) {
                    x = xMax;
                    llDropDownMenu.setPadding(v1dp, 0, 0, v1dp);
                }

                else {
                    llDropDownMenu.setPadding(v1dp, 0, v1dp, v1dp);
                }

                llDropDownMenu.setX(x);
                flMenuOptions.setVisibility(View.VISIBLE);
            });
        });
        llButtonBar.addView(button);
    }

    public void addSingleButton(Option option) {

        FrameLayout fl = new FrameLayout(getActivity());
        int lrPadding = DisplayUnit.dpToPx(10f);
        int ivPadding = DisplayUnit.dpToPx(5f);
        llButtonBar.addView(fl);

        fl.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

        Button button = new Button(getActivity());
        fl.addView(button, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        LinearLayout ll = new LinearLayoutHorizontal(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        ll.setGravity(Gravity.CENTER);
        ll.setPadding(lrPadding, 0, lrPadding, 0);
        fl.addView(ll);

        ImageView ivIcon = new ImageView(getActivity());
        ll.addView(ivIcon);

        TextView tvText = new TextView(getActivity());
        int colorBG = ThemeUtil.getColor(getActivity(), android.R.attr.colorBackground);
        tvText.setTextColor(colorBG);
        tvText.setAllCaps(true);
        ll.addView(tvText);

        button.setOnClickListener(view -> {
            hideMenu();
            Runnable runnable = option.getExecutor();
            if(runnable != null) { runnable.run(); }
        });

        Bitmap bitmap = option.getIconBitmap();
        int iconId = option.getIconId();
        if(bitmap != null) { ivIcon.setImageBitmap(bitmap); }
        else if(iconId != 0) { ivIcon.setImageResource(iconId); }
        ImageUtil.setTint(ivIcon, colorBG);
        ivIcon.setPadding(0, ivPadding, 0, ivPadding);

        String name = option.getName();
        int nameId = option.getNameId();
        if(name != null) { tvText.setText(name); }
        else if(nameId != 0) { tvText.setText(nameId); }

        if(option.isDrawAsDisabled()) {
            ImageView ivDisabledOverlay = new ImageView(ContentView.getActivity());
            ivDisabledOverlay.setImageResource(R.drawable.menu_op_disabled_overlay);
            fl.addView(ivDisabledOverlay);
        }
    }

    @Override
    public boolean onBackPressed() {
        if(menuVisible()) {
            hideMenu();
            return true; // consumed
        }
        return super.onBackPressed();
    }
}