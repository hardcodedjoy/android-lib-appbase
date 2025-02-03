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

package com.hardcodedjoy.dev.appbase.cv;

import android.annotation.SuppressLint;
import android.widget.LinearLayout;

import com.hardcodedjoy.appbase.contentview.ContentView;
import com.hardcodedjoy.appbase.gui.GuiLinker;
import com.hardcodedjoy.appbase.setgetters.SetGetter;
import com.hardcodedjoy.dev.appbase.R;

@SuppressLint("ViewConstructor")
public class CvDDLTest extends ContentView {

    private String currentValue;

    public CvDDLTest() {
        // add initialization code here (that must run only one time)
        inflate(R.layout.cv_ddl_test);

        LinearLayout ddlTest = findViewById(R.id.appbase_dd_test);

        String title = "Title";
        String[] keys = { "key1", "key2", "key3" };
        String[] keysDisabled = { "key3" };
        String[] values = { "val1", "val2", "val3" };

        GuiLinker.linkDropDownList(ddlTest, title, keys, keysDisabled, values, new SetGetter() {
            @Override
            public void set(String value) {
                // value = what was chosen by the user
                currentValue = value;
            }

            @Override
            public String get() {
                // get current value (ie: from settings)
                return currentValue;
            }
        });
    }
}