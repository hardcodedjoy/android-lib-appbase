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
import android.widget.TextView;

import com.hardcodedjoy.appbase.contentview.CvTMSLL;
import com.hardcodedjoy.appbase.gui.MenuOption;

import java.util.Vector;

@SuppressLint("ViewConstructor")
public class CvMain extends CvTMSLL {

    static private int i = 0;

    public CvMain() {
        // add initialization code here (that must run only one time)
        Vector<MenuOption> ops = new Vector<>();
        ops.add(new MenuOption("popups", () -> new CvPopups().show()));
        ops.add(new MenuOption("theme view", () -> new CvThemeView().show()));
        addMenuOptions(ops, 0);
    }

    @Override
    public void show() {
        super.show();

        // add code to run every time this ContentView appears on screen

        // "Hello World" demo:
        LinearLayout llContent = findViewById(R.id.appbase_ll_content);
        llContent.removeAllViews();
        inflate(getActivity(), R.layout.layout_main, llContent);
        TextView tv = findViewById(R.id.appbase_tv_text);
        String s = "Hello, World! " + (i++);
        tv.setText(s);
    }
}