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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardcodedjoy.appbase.contentview.CvTBBLL;
import com.hardcodedjoy.appbase.popup.Option;

import java.util.Vector;

@SuppressLint("ViewConstructor")
public class CvTBBLLTest extends CvTBBLL {

    public CvTBBLLTest() {
        // add initialization code here (that must run only one time)
        setTitleIcon(com.hardcodedjoy.appbase.R.drawable.ic_app_1);

        setTitle("1234567890 1234567890 1234567890");

        Vector<Option> ops;

        {
            ops = new Vector<>();

            ops.add(new Option(
                    com.hardcodedjoy.appbase.R.drawable.ic_file_type_file_3,
                    com.hardcodedjoy.appbase.R.string.option_new,
                    null));

            ops.add(new Option(
                    com.hardcodedjoy.appbase.R.drawable.ic_file_type_folder_3,
                    com.hardcodedjoy.appbase.R.string.option_open,
                    null));

            addButton(com.hardcodedjoy.appbase.R.string.file, ops);
        }

        {
            ops = new Vector<>();

            ops.add(new Option(
                    com.hardcodedjoy.appbase.R.drawable.ic_edit_undo_1,
                    com.hardcodedjoy.appbase.R.string.option_undo,
                    null));

            ops.add(new Option(
                    com.hardcodedjoy.appbase.R.drawable.ic_edit_redo_1,
                    com.hardcodedjoy.appbase.R.string.option_redo,
                    null));

            ops.add(new Option(
                    com.hardcodedjoy.appbase.R.drawable.ic_content_cut_1,
                    com.hardcodedjoy.appbase.R.string.option_cut,
                    null));

            ops.add(new Option(
                    com.hardcodedjoy.appbase.R.drawable.ic_content_copy_3,
                    com.hardcodedjoy.appbase.R.string.option_copy,
                    null));

            ops.add(new Option(
                    com.hardcodedjoy.appbase.R.drawable.ic_content_paste_3,
                    com.hardcodedjoy.appbase.R.string.option_paste,
                    null));

            ops.add(new Option(
                    com.hardcodedjoy.appbase.R.drawable.ic_content_delete_3,
                    com.hardcodedjoy.appbase.R.string.option_delete,
                    null));

            addButton(com.hardcodedjoy.appbase.R.string.title_edit, ops);
        }

        {
            ops = new Vector<>();
            addButton("super very and ultra long string", ops);
        }

        {
            ops = new Vector<>();

            ops.add(new Option(
                    com.hardcodedjoy.appbase.R.drawable.ic_edit_undo_1,
                    com.hardcodedjoy.appbase.R.string.option_undo,
                    null));

            ops.add(new Option(
                    com.hardcodedjoy.appbase.R.drawable.ic_edit_redo_1,
                    com.hardcodedjoy.appbase.R.string.option_redo,
                    null));

            addButton("edit 2", ops);
        }

    }

    @Override
    public void show() {
        super.show();
        // add code to run every time this ContentView appears on screen

        LinearLayout ll = findViewById(R.id.appbase_ll_content);
        ll.removeAllViews();
        TextView tv = new TextView(getActivity());
        String s = "This is a window with button bar";
        tv.setText(s);
        ll.addView(tv);
    }
}