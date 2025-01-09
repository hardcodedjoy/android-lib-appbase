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

package com.hardcodedjoy.dev.appbase;

import android.annotation.SuppressLint;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hardcodedjoy.appbase.contentview.ContentView;
import com.hardcodedjoy.appbase.gui.GuiLinker;
import com.hardcodedjoy.appbase.setgetters.SetGetter;
import com.hardcodedjoy.appbase.gui.ThemeUtil;

@SuppressLint("ViewConstructor")
public class CvThemeView extends ContentView {

    private String theme;

    public CvThemeView() { init(); }

    private void init() {

        if(theme == null) {
            theme = ThemeUtil.getThemes(getActivity())[0];
            ThemeUtil.setTheme(getActivity(), theme);
        }

        removeAllViews();
        inflate(R.layout.cv_theme_view);

        RadioGroup rg;
        RadioButton rb;

        rg = findViewById(R.id.appbase_rg_theme);
        rg.removeAllViews();

        String[] themes = ThemeUtil.getThemes(getActivity());
        for(String themeName : themes) {
            rb = new RadioButton(getActivity());
            rb.setText(themeName);

            // getColorStateList(resId, theme) requires API 23
            // maybe this is why text color does not get updated on API 21

            // fast workaround:
            rb.setTextColor(ThemeUtil.getColor(getActivity(), android.R.attr.colorForeground));

            rg.addView(rb);
        }

        GuiLinker.link(rg, new SetGetter() {
            @Override
            public void set(String value) {
                theme = value;
                ThemeUtil.setTheme(getActivity(), theme);
                init(); // re-init to reflect new theme
            }

            @Override
            public String get() { return theme; }
        });


    }

    @Override
    public boolean onBackPressed() {
        Settings settings = (Settings) Settings.getInstance();
        ThemeUtil.setTheme(getActivity(), settings.getTheme(getActivity()));
        return super.onBackPressed();
    }
}