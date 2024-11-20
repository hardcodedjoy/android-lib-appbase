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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.gui.DisplayUnit;
import com.hardcodedjoy.appbase.gui.DropDownMenu;
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

    @Override
    public boolean onBackPressed() {
        if(menuVisible()) {
            hideMenu();
            return true; // consumed
        }
        return super.onBackPressed();
    }
}