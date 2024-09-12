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

package com.hardcodedjoy.dev.appbase;

import android.annotation.SuppressLint;
import android.widget.LinearLayout;

import com.hardcodedjoy.appbase.contentview.CvTSLL;
import com.hardcodedjoy.appbase.gui.GuiLinker;
import com.hardcodedjoy.appbase.setgetters.IntSetGetter;

@SuppressLint("ViewConstructor")
public class CvColorTest extends CvTSLL {

    private int colorForeground;
    private int colorBackground;

    public CvColorTest() {
        setTitle(com.hardcodedjoy.appbase.R.string.title_color);
        setTitleIcon(com.hardcodedjoy.appbase.R.drawable.ic_circle_2);
        LinearLayout ll = findViewById(R.id.appbase_ll_content);
        inflate(getActivity(), R.layout.cv_color_test, ll);

        GuiLinker.linkColorField(findViewById(R.id.ll_fg_color),
                com.hardcodedjoy.appbase.R.string.title_foreground_color, new IntSetGetter() {
            @Override
            public void set(int value) { colorForeground = value; }
            @Override
            public int get() { return colorForeground; }
        });

        GuiLinker.linkColorField(findViewById(R.id.ll_bg_color),
                com.hardcodedjoy.appbase.R.string.title_background_color, new IntSetGetter() {
            @Override
            public void set(int value) { colorBackground = value; }
            @Override
            public int get() { return colorBackground; }
        });
    }
}