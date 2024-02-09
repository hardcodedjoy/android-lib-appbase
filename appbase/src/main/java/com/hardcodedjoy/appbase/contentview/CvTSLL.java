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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;

@SuppressLint("ViewConstructor")
public class CvTSLL extends ContentView { // Content View with Title and LL in SV
    public CvTSLL() { inflate(R.layout.appbase_cv_tsll); }

    public void setTitle(String title) {
        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        tvTitle.setText(title);
    }

    public void setTitle(int titleResId) {
        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        tvTitle.setText(titleResId);
    }

    public void setTitleIcon(int iconResId) {
        // borrow ivIcon from cv_settings:
        LinearLayout llCvSettings = (LinearLayout) inflate(
                getActivity(), R.layout.appbase_cv_settings, null);
        LinearLayout llTitle = llCvSettings.findViewById(R.id.appbase_ll_title);
        ImageView ivIcon = (ImageView) llTitle.getChildAt(0);
        llTitle.removeView(ivIcon);

        // change icon res:
        ivIcon.setImageResource(iconResId);

        // add to this cv:
        llTitle = findViewById(R.id.appbase_ll_title);
        llTitle.addView(ivIcon, 0);
    }
}