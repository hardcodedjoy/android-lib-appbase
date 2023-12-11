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

package com.hardcodedjoy.appbase.contentview;

import android.annotation.SuppressLint;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.Settings;
import com.hardcodedjoy.appbase.gui.GuiLinker;
import com.hardcodedjoy.appbase.gui.SetGetter;
import com.hardcodedjoy.appbase.gui.ThemeUtil;

@SuppressLint("ViewConstructor")
public class CvSettingsBase extends ContentView {

    public CvSettingsBase() { init(); }

    public void init() {
        removeAllViews();
        inflate(R.layout.appbase_cv_settings);

        RadioGroup rgTheme = findViewById(R.id.rg_theme);
        RadioButton rb;

        rb = findViewById(R.id.rb_theme_light);
        rb.setText(ThemeUtil.LIGHT);
        rb = findViewById(R.id.rb_theme_dark);
        rb.setText(ThemeUtil.DARK);
        rb = findViewById(R.id.rb_theme_system);
        rb.setText(ThemeUtil.SYSTEM);

        GuiLinker.link(rgTheme, new SetGetter() {
            @Override
            public void set(String value) {
                ((Settings) settings).setTheme(value);
                ((Settings) settings).save();
                ThemeUtil.set(getActivity(), ((Settings) settings).getTheme());
                init(); // re-init to reflect new theme
            }
            @Override
            public String get() { return ((Settings) settings).getTheme(); }
        });
    }

    @Override
    public boolean onBackPressed() {
        ((Settings) settings).save();
        return super.onBackPressed();
    }
}