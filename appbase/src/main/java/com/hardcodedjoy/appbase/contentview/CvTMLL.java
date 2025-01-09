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
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.gui.DisplayUnit;

@SuppressWarnings("unused")
@SuppressLint("ViewConstructor")
public class CvTMLL extends CvTM { // Title / Menu / LinearLayout

    @SuppressLint({"ClickableViewAccessibility", "RtlHardcoded"})
    public CvTMLL() {
        inflate(R.layout.appbase_cv_tmll);

        findViewById(R.id.appbase_iv_title_icon).setVisibility(GONE);
        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        titleTextSizeDefault = DisplayUnit.pxToSp((int)tvTitle.getTextSize());

        flMenuOptions = findViewById(R.id.appbase_fl_menu_options);
        llMenuOptions = flMenuOptions.findViewById(R.id.appbase_ll_menu_options);

        findViewById(R.id.appbase_btn_menu).setOnClickListener(view -> {
            if(menuVisible()) { hideMenu(); }
            else { showMenu(); }
        });
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
}