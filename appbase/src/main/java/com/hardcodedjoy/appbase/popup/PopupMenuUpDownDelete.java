/*

MIT License

Copyright © 2026 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

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

package com.hardcodedjoy.appbase.popup;

import android.annotation.SuppressLint;

import com.hardcodedjoy.appbase.contentview.ContentView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

@SuppressLint("ViewConstructor")
public class PopupMenuUpDownDelete<T> extends PopupChoose {

    private final ArrayList<T> list;
    private final T item;
    private final Runnable onAfterOpExecuted;

    public PopupMenuUpDownDelete(String title,
                                 ArrayList<T> list,
                                 T item,
                                 Runnable onAfterOpExecuted) {
        super(title, null, new Vector<>(Arrays.asList(
                new Option(com.hardcodedjoy.appbase.R.drawable.ic_arrow_v_up_1,
                        com.hardcodedjoy.appbase.R.string.option_move_up, null),
                new Option(com.hardcodedjoy.appbase.R.drawable.ic_arrow_v_down_1,
                        com.hardcodedjoy.appbase.R.string.option_move_down, null),
                new Option(com.hardcodedjoy.appbase.R.drawable.ic_content_delete_1,
                        com.hardcodedjoy.appbase.R.string.option_delete, null)
        )));

        this.list = list;
        this.item = item;
        this.onAfterOpExecuted = onAfterOpExecuted;

        options.get(0).setExecutor(this::onMoveUp);
        options.get(1).setExecutor(this::onMoveDown);
        options.get(2).setExecutor(this::askDelete);
    }

    private void askDelete() {
        String delete = ContentView.getString(
                com.hardcodedjoy.appbase.R.string.title_delete);
        String message = ContentView.getString(com.hardcodedjoy.appbase.R.string.delete_x);
        message = message.replace("[x]", item.toString());
        PopupAsk popupAsk = new PopupAsk(delete, message) {
            @Override
            public void onOK() { onDelete(); }
        };
        popupAsk.enableDismissByOutsideClick();
        popupAsk.show();
    }

    private void onMoveUp() {
        int index = list.indexOf(item);
        if(index == 0) { return; }
        list.remove(item);
        list.add(index-1, item);
        if(onAfterOpExecuted != null) { onAfterOpExecuted.run(); }
    }

    private void onMoveDown() {
        int index = list.indexOf(item);
        if(index == list.size()-1) { return; }
        list.remove(item);
        list.add(index+1, item);
        if(onAfterOpExecuted != null) { onAfterOpExecuted.run(); }
    }

    private void onDelete() {
        list.remove(item);
        if(onAfterOpExecuted != null) { onAfterOpExecuted.run(); }
    }
}